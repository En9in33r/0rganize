package com.x_c0re.a0rganize;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AuthActivity extends AppCompatActivity
{
    private TextView mTextSignIn;
    private EditText mLoginField, mPasswordField;
    private Button mLogInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mTextSignIn = (TextView)findViewById(R.id.textViewSignIn);
        mTextSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AuthActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });

        mLoginField = (EditText)findViewById(R.id.editTextLogin);
        mPasswordField = (EditText)findViewById(R.id.editTextPassword);

        mLogInButton = (Button)findViewById(R.id.buttonLogin);
        mLogInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mLoginField.getText().toString().equals("admin") && mPasswordField.getText().toString().equals("qwerty"))
                {
                    CheckActivity.activity = "fromAuthActivitytoMainActivity";
                    CheckActivity.loginS = mLoginField.getText().toString();

                    Intent intent = new Intent(AuthActivity.this, CheckActivity.class);
                    startActivity(intent);


                    Toast toast = Toast.makeText(AuthActivity.this, "Logged as admin", Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(AuthActivity.this, "Incorrect login or password", Toast.LENGTH_LONG);
                    toast.show();
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
}


