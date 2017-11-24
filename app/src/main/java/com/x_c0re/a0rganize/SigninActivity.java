package com.x_c0re.a0rganize;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.github.kevinsawicki.http.HttpRequest;

import java.util.concurrent.ExecutionException;

public class SigninActivity extends AppCompatActivity
{
    private EditText mNameField;
    private EditText mSurnameField;
    private EditText mLoginField;
    private EditText mPasswordField;
    private EditText mRepeatPasswordField;

    ConnectionDetector cd;
    LoginSeeker seeker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign In");

        mNameField = (EditText)findViewById(R.id.editTextName);
        mSurnameField = (EditText)findViewById(R.id.editTextSurname);
        mLoginField = (EditText)findViewById(R.id.editTextLogin);
        mPasswordField = (EditText)findViewById(R.id.editTextPassword);
        mRepeatPasswordField = (EditText)findViewById(R.id.editTextRepeatPassword);
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
                if (!mNameField.getText().toString().equals("") && !mSurnameField.getText().toString().equals("")
                        && !mLoginField.getText().toString().equals("") && !mPasswordField.getText().toString().equals("")
                        && !mRepeatPasswordField.getText().toString().equals(""))
                {
                    if (mPasswordField.getText().toString().equals(mRepeatPasswordField.getText().toString()))
                    {
                        // поиск в БД логина. если он уже занят - регистрация отменяется (все это выполняется при условии
                        // того, что смартфон подключен к Интернету)

                        cd = new ConnectionDetector(this);
                        if (cd.isConnected())
                        {
                            seeker = new LoginSeeker();
                            seeker.execute(mLoginField.getText().toString());
                            try
                            {
                                if (seeker.get()) // если ответ сервера - null (если по запросу ничего не найдено и логин свободен)
                                {
                                    VerificationCodeActivity.entered_name = mNameField.getText().toString();
                                    VerificationCodeActivity.entered_surname = mSurnameField.getText().toString();
                                    VerificationCodeActivity.entered_login = mLoginField.getText().toString();
                                    VerificationCodeActivity.entered_password = mPasswordField.getText().toString();

                                    Intent intent = new Intent(this, CheckPhoneNumberActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(this, "This login is already taken", Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (InterruptedException | ExecutionException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            Toast.makeText(this, "Internet connection required", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast toast = Toast.makeText(this, "Passwords are not matches", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else
                {
                    Toast toast = Toast.makeText(this, "All fields must be full", Toast.LENGTH_LONG);
                    toast.show();
                }

                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class LoginSeeker extends AsyncTask<String, Void, Boolean>
    {
        @Override
        protected Boolean doInBackground(String... strings)
        {
            HttpRequest request = HttpRequest.get("http://95.85.19.194/contacts/find_by_login/" + strings[0]);
            return request.body().equals("null");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            super.onPostExecute(aBoolean);
        }
    }
}
