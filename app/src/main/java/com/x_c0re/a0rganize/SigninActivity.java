package com.x_c0re.a0rganize;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class SigninActivity extends AppCompatActivity
{
    private EditText mNameField;
    private EditText mSurnameField;
    private EditText mLoginField;
    private EditText mPasswordField;
    private EditText mRepeatPasswordField;

    DBHelper helper;

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

        helper = new DBHelper(this);
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
            // registration button
            case R.id.start_mission_button:
                if (!mNameField.getText().toString().equals("") && !mSurnameField.getText().toString().equals("")
                        && !mLoginField.getText().toString().equals("") && !mPasswordField.getText().toString().equals("")
                        && !mRepeatPasswordField.getText().toString().equals(""))
                {
                    if (mPasswordField.getText().toString().equals( mRepeatPasswordField.getText().toString() ) )
                    {
                        SQLiteDatabase database = helper.getWritableDatabase();

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DBHelper.KEY_NAME, mNameField.getText().toString());
                        contentValues.put(DBHelper.KEY_SURNAME, mSurnameField.getText().toString());
                        contentValues.put(DBHelper.KEY_LOGIN, mLoginField.getText().toString());
                        contentValues.put(DBHelper.KEY_PASSWORD, mPasswordField.getText().toString());

                        database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
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
}
