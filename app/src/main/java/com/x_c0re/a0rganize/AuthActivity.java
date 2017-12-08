package com.x_c0re.a0rganize;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.core.android.Auth;
import com.github.kevinsawicki.http.HttpRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;

import static java.security.AccessController.getContext;

public class AuthActivity extends AppCompatActivity
{
    private TextView mTextSignIn;
    private EditText mLoginField, mPasswordField;
    private Button mLogInButton;

    LoginSeeker seeker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mTextSignIn = findViewById(R.id.textViewSignIn);
        mTextSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AuthActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

        mLoginField = findViewById(R.id.editTextLogin);
        mPasswordField = findViewById(R.id.editTextPassword);

        mLogInButton = findViewById(R.id.buttonLogin);
        mLogInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String login = mLoginField.getText().toString();
                String password = mPasswordField.getText().toString();
                seeker = new LoginSeeker();
                seeker.execute(login);

                try
                {
                    Log.w("execution_result", seeker.get());
                    if (!seeker.get().equals("null"))
                    {
                        if (seeker.get().contains("\"password\":\"" + password + "\""))
                        {
                            CheckActivity.activity = "fromAuthActivitytoMainActivity";
                            CheckActivity.loginS = mLoginField.getText().toString();

                            MainActivity.check_for_login = "moved";
                            MainActivity.login_bridge = login;

                            Intent intent = new Intent(AuthActivity.this, CheckActivity.class);
                            startActivity(intent);

                            Toast toast = Toast.makeText(AuthActivity.this, "Logged as " + login, Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else
                        {
                            Toast toast = Toast.makeText(AuthActivity.this, "Incorrect login or password", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                    else
                    {
                        Toast toast = Toast.makeText(AuthActivity.this, "Incorrect login or password", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                catch (InterruptedException | ExecutionException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    public void onBackPressed()
    {
        // blocked
    }

    static class LoginSeeker extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            HttpRequest request = HttpRequest.get("http://overcome-api.herokuapp.com/contacts/find_by_login/" + strings[0]);
            return request.body();
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
        }
    }

    // на вход подаются логин и пароль;
    // выполняется поиск элемента по логину, выдается его тело
    // в теле находится "password":"[введенный пароль]"
    // если все верно, возвращается значение true, иначе - false
}


