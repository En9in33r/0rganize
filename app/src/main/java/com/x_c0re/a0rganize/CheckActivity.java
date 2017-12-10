package com.x_c0re.a0rganize;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.kevinsawicki.http.HttpRequest;

import java.util.concurrent.ExecutionException;

public class CheckActivity extends AppCompatActivity
{
    // любимый костылик и первое запускающееся активити.

    public final static String SAVED_TEXT = "saved_text"; // имя файла настроек

    public final static String SAVED_LOGIN = "login";   // сохраненный логин
    public final static String SAVED_ID = "id";         // сохраненный ID
    
    public static SharedPreferences shLogin;
    public static SharedPreferences.Editor edLogin;

    public static String activity = "";

    public static String loginS;
    public static String idS;

    IDFinder finder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        shLogin = getSharedPreferences(SAVED_TEXT, MODE_PRIVATE);

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
            else
            {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
        // if you are logging in your login saves to a SharedPreferences key and 'activity' variable is erasing
        else if (activity.equals("fromAuthActivitytoMainActivity"))
        {
            saveLogin(loginS);
            saveID(idS);
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

        // передача в MainActivity полученного ID

        try
        {
            finder = new IDFinder();
            finder.execute(loadLogin());
            MainActivity.current_id = finder.get();
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    // saves login to special variable
    public void saveLogin(String login)
    {
        edLogin = shLogin.edit();
        edLogin.putString(SAVED_LOGIN, login);
        edLogin.apply();
    }

    // returns login
    public String loadLogin()
    {
        return shLogin.getString(SAVED_LOGIN, "");
    }

    public void saveID(String id) // saves ID
    {
        edLogin = shLogin.edit();
        edLogin.putString(SAVED_ID, id);
        edLogin.apply();
    }

    public String loadID() // returns ID
    {
        return shLogin.getString(SAVED_ID, "");
    }

    // deletes login and ID from file
    public void eraseLogin()
    {
        edLogin = shLogin.edit();
        edLogin.clear();
        edLogin.apply();
    }

    private static class IDFinder extends AsyncTask<String, Void, String> // по логину находит id
    {
        @Override
        protected String doInBackground(String... strings)
        {
            HttpRequest request = HttpRequest.get("http://overcome-api.herokuapp.com/contacts/find_out_id/" + strings[0]);

            return request.body();
        }
    }
}
