package com.x_c0re.a0rganize;

import android.content.ContentValues;
import android.content.Context;
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

import static java.security.AccessController.getContext;

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
                cursor = db.query(DBHelper.TABLE_CONTACTS,
                        new String[] { DBHelper.KEY_ID, DBHelper.KEY_PASSWORD, DBHelper.KEY_NAME, DBHelper.KEY_SURNAME},
                        selection, selectionArgs, null, null, null);

                if (cursor.moveToFirst())
                {
                    String password_from_cursor = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PASSWORD));
                    String id_from_cursor = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ID));
                    String name_from_cursor = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME));
                    String surname_from_cursor = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_SURNAME));

                    // достать логин из поля ввода было проще, знаю. upd: придется все-таки из поля
                    String login_from_cursor = mLoginField.getText().toString();

                    /* Ищет по введенному логину соответствующую строку в таблицу БД, если
                    * находит что-то - достает из строки пароль, имя и фамилию пользователя.
                    * Если введенный в другом поле пароль равен тому паролю, что был выведен
                    * из БД, производится вход в аккаунт. Затем переходная активити, о
                    * которой никто не должен знать, получает информацию о том, что производится
                    * переход из активити auth в активити main; переменная loginS получает значение
                    * в виде введенного логина; для того, чтобы после перехода в mainactivity
                    * корректно отображались имя и фамилия юзера на шторке, статические переменные в ней
                    * получают значения сигнала и имени и фамилии; */

                    /* TODO: при регистрации нового пользователя необходимо реализовать то же самое! */
                    /* TODO: а также при входе и регистрации должен записываться в SharedPreferences логин! <- это выполни первым */

                    if (password_from_cursor.equals(password))
                    {
                        CheckActivity.activity = "fromAuthActivitytoMainActivity";
                        CheckActivity.loginS = mLoginField.getText().toString();

                        MainActivity.check_for_login = "moved";
                        MainActivity.login_bridge = (name_from_cursor + " " + surname_from_cursor);
                        MainActivity.login_bridge = login_from_cursor;

                        helper.close();
                        cursor.close();

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
                        cursor.close();
                    }
                }
                else
                {
                    Toast toast = Toast.makeText(AuthActivity.this, "Incorrect login or password", Toast.LENGTH_LONG);
                    toast.show();

                    helper.close();
                    cursor.close();
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


