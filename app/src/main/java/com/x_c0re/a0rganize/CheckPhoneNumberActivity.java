package com.x_c0re.a0rganize;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class CheckPhoneNumberActivity extends AppCompatActivity
{
    private EditText mInputNumber;

    final Random random = new Random();

    PostRequestSender ps;

    DBHelper helper;

    public static int code;
    public static String number;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_phone_number);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Number verification");

        mInputNumber = (EditText)findViewById(R.id.inputPhoneNumberField);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.top_right_start_mission_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.start_mission_button:
                code = 1000 + random.nextInt(9000);
                number = mInputNumber.getText().toString();

                VerificationCodeActivity.codeVerifying = String.valueOf(code);

                // ЕСЛИ НОМЕР УЖЕ ИСПОЛЬЗУЕТСЯ
                helper = new DBHelper(this);
                SQLiteDatabase database = helper.getWritableDatabase();

                String selection = "phone = ?";
                String[] selectionArgs = new String[] { mInputNumber.getText().toString() };
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, new String[] { DBHelper.KEY_ID },
                        selection, selectionArgs, null, null, null);
                if (cursor.moveToFirst())
                {
                    Toast toast = Toast.makeText(this, "This phone number is already taken", Toast.LENGTH_LONG);
                    toast.show();

                    cursor.close();
                    database.close();
                }
                else
                {
                    VerificationCodeActivity.entered_phone = mInputNumber.getText().toString();

                    // TODO: в случае отсутствия интернет-соединения должен выводиться Toast с последующей отменой регистрации

                    ps = new PostRequestSender();
                    ps.execute();

                    Intent intent = new Intent(this, VerificationCodeActivity.class);
                    startActivity(intent);
                }

                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class PostRequestSender extends AsyncTask<String, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings)
        {

            HttpRequest request = HttpRequest.post("https://sms.ru/sms/send");
            request.part("api_id", "73F8F961-A9EE-1F6E-DB2B-B6E3FCAED43C");
            request.part("to", number);
            request.part("msg", code);
            request.part("json", "1");
            int status = request.code();
            if (status == 200)
            {
                System.out.println(request.body());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
        }
    }
}
