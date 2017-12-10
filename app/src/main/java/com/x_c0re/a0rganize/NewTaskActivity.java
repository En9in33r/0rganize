package com.x_c0re.a0rganize;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class NewTaskActivity extends AppCompatActivity
{
    private EditText mEnterTask;

    public static String current_login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        mEnterTask = findViewById(R.id.editTaskText);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.new_task_form_label);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.top_right_start_mission_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.start_mission_button)
        {
            CurrentTasksFragment.data.clear();

            // отправка текста и имени автора на сервер
            RequestParams params = new RequestParams();
            params.put("text", mEnterTask.getText().toString());
            AsyncHttpClient client = new AsyncHttpClient();
            client.post("https://overcome-api.herokuapp.com/contacts/" + MainActivity.current_id + "/runnings",
                    params, new AsyncHttpResponseHandler()
                    {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
                        {
                            Log.w("async", "Success!");
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                        {
                            Log.w("async", "Failure!");
                        }
                    });

            Toast toast = Toast.makeText(this, "Successfully!", Toast.LENGTH_LONG);
            toast.show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == android.R.id.home)
        {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
