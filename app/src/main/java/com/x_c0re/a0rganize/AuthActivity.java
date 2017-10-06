package com.x_c0re.a0rganize;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    DBHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        helper = new DBHelper(this);

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
                String login = mLoginField.getText().toString();
                String password = mPasswordField.getText().toString();

                db = helper.getWritableDatabase();

                Cursor cursor;

                String selection = "login = ?";
                String[] selectionArgs = new String[] { login };
                cursor = db.query(DBHelper.TABLE_CONTACTS, new String[] { DBHelper.KEY_ID, DBHelper.KEY_PASSWORD},
                        selection, selectionArgs, null, null, null);

                if (cursor != null)
                {
                    cursor.moveToFirst();
                    String password_from_cursor = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PASSWORD));
                    String id_from_cursor = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ID));

                    if (password_from_cursor.equals(password)) // это заглушка
                    {
                        CheckActivity.activity = "fromAuthActivitytoMainActivity";
                        CheckActivity.loginS = mLoginField.getText().toString();

                        helper.close();

                        Intent intent = new Intent(AuthActivity.this, CheckActivity.class);
                        startActivity(intent);


                        Toast toast = Toast.makeText(AuthActivity.this, "Logged as " + login, Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else
                    {
                        Toast toast = Toast.makeText(AuthActivity.this, "Incorrect login or password", Toast.LENGTH_LONG);
                        toast.show();

                        helper.close();
                    }
                }
                else
                {
                    Toast toast = Toast.makeText(AuthActivity.this, "Incorrect login or password", Toast.LENGTH_LONG);
                    toast.show();

                    helper.close();
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


