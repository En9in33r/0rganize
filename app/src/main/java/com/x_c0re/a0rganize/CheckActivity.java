package com.x_c0re.a0rganize;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CheckActivity extends AppCompatActivity
{
    // КОСТЫЫЫЫЫЫЛЬ!

    public final static String SAVED_TEXT = "saved_text";
    public static SharedPreferences shLogin;
    public static SharedPreferences.Editor edLogin;

    public static String activity = "";
    public static String loginS;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);


        // standart equal of variable 'activity'
        if (activity.equals(""))
        {
            // loads login from key
            String login = loadLogin();

            // if nothing keeps in it you are moving to AuthActivity
            if (login.equals(""))
            {
                Intent intent = new Intent(this, AuthActivity.class);
                startActivity(intent);
            }
            // if something keeps in it you checking the registration automatically
            else if (login.equals("admin"))
            {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
        // if you are logging in your login saves to a SharedPreferences key and 'activity' variable is erasing
        else if (activity.equals("fromAuthActivitytoMainActivity"))
        {
            saveLogin(loginS);
            activity = "";
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else if (activity.equals("fromMainActivitytoAuthActivity"))
        {
            eraseLogin();
            activity = "";
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }
    }

    // saves login to special variable
    public void saveLogin(String login)
    {
        shLogin = getPreferences(MODE_PRIVATE);
        edLogin = shLogin.edit();
        edLogin.putString(SAVED_TEXT, login);
        edLogin.apply();
    }

    // returns login
    public String loadLogin()
    {
        shLogin = getPreferences(MODE_PRIVATE);
        return shLogin.getString(SAVED_TEXT, "");
    }

    // deletes login from variable
    public void eraseLogin()
    {
        shLogin = getPreferences(MODE_PRIVATE);
        edLogin = shLogin.edit();
        edLogin.clear();
        edLogin.apply();
    }

    @Override
    public void onBackPressed()
    {

    }
}
