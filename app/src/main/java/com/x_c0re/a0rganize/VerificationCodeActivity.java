package com.x_c0re.a0rganize;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;

public class VerificationCodeActivity extends AppCompatActivity
{
    public static String codeVerifying;

    private EditText mCodeField;

    public static String entered_login;
    public static String entered_password;
    public static String entered_name;
    public static String entered_surname;
    public static String entered_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Number Verification");

        mCodeField = findViewById(R.id.codeField);
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
                if (codeVerifying.equals(mCodeField.getText().toString()))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Phone number confirmed!", Toast.LENGTH_LONG);
                    toast.show();

                    Intent intent = new Intent(this, CropOrSkipActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this, "Internet connection required", Toast.LENGTH_LONG).show();
                }

                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
