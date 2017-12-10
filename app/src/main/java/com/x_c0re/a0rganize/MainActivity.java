package com.x_c0re.a0rganize;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private Fragment fragment;
    private FragmentManager manager;

    private TextView login_view;

    private CircleImageView mProfileButton;

    public final static String SAVED_TEXT = "saved_text"; // имя файла настроек

    public final static String SAVED_LOGIN = "login";   // сохраненный логин

    SharedPreferences sharedPreferencesLogin;
    SharedPreferences.Editor editorLogin;

    public static String login_bridge;

    public static String check_for_login = "";

    public static String current_id;

    FindJSONContactByLogin find;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferencesLogin = getSharedPreferences(SAVED_TEXT, MODE_PRIVATE);

        if (check_for_login.equals("moved"))
        {
            saveLogin(login_bridge);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NewTaskActivity.current_login = login_view.getText().toString();

                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mProfileButton = navigationView.getHeaderView(0).findViewById(R.id.avatarImage);

        try
        {
            Glide.with(this).load(findAvatarUrl(loadLogin())).into(mProfileButton);
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }


        mProfileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        login_view = navigationView.getHeaderView(0).findViewById(R.id.textViewLogin);

        String a = loadLogin();
        login_view.setText(a);

        fragment = new CurrentTasksFragment();
        manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.constraintLayoutMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        CurrentTasksFragment.current_login = login_view.getText().toString();
        FailedTasksFragment.current_login = login_view.getText().toString();
        CancelTheTaskDialog.current_login = login_view.getText().toString();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_about_app:
                AboutMeFragment about = new AboutMeFragment();
                about.show(manager, "drawer");

                return true;

            case R.id.action_logout:
                CheckActivity.activity = "fromMainActivitytoAuthActivity";
                CurrentTasksFragment.data.clear();
                FailedTasksFragment.failedTasksData.clear();

                eraseLogin();
                login_view.setText(""); // наверное, это лишнее, но пусть будет)

                Intent intent = new Intent(this, CheckActivity.class);
                startActivity(intent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        FragmentTransaction transaction = manager.beginTransaction();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        switch (item.getItemId())
        {
            case (R.id.nav_wall_lounge):
                fragment = new CourtLounge();
                transaction.replace(R.id.constraintLayoutMain, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                drawer.closeDrawers();
                return true;
            case (R.id.nav_stats):
                fragment = new StatsFragment();
                transaction.replace(R.id.constraintLayoutMain, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                drawer.closeDrawers();
                return true;
            case (R.id.nav_settings):
                fragment = new SettingsFragment();
                transaction.replace(R.id.constraintLayoutMain, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                drawer.closeDrawers();
                return true;
            case (R.id.nav_current_tasks):
                fragment = new CurrentTasksFragment();
                transaction.replace(R.id.constraintLayoutMain, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                drawer.closeDrawers();
                return true;
            case (R.id.nav_in_a_forum):
                fragment = new InAForumTasksFragment();
                transaction.replace(R.id.constraintLayoutMain, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                drawer.closeDrawers();
                return true;
            case (R.id.nav_done):
                fragment = new CompletedTasksFragment();
                transaction.replace(R.id.constraintLayoutMain, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                drawer.closeDrawers();
                return true;
            case (R.id.nav_failed):
                fragment = new FailedTasksFragment();
                transaction.replace(R.id.constraintLayoutMain, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                drawer.closeDrawers();
                return true;
        }

        return true;
    }

    public void saveLogin(String login)
    {
        editorLogin = sharedPreferencesLogin.edit();
        editorLogin.putString(SAVED_LOGIN, login);
        editorLogin.apply();
    }

    public String loadLogin()
    {
        return sharedPreferencesLogin.getString(SAVED_LOGIN, "");
    }

    public void eraseLogin()
    {
        editorLogin = sharedPreferencesLogin.edit();
        editorLogin.clear();
        editorLogin.apply();
    }

    private Uri findAvatarUrl(String login) throws InterruptedException, ExecutionException
    {
        find = new FindJSONContactByLogin();
        find.execute(login);
        String avatar_json = find.get();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        ContactJSON json = gson.fromJson(avatar_json, ContactJSON.class);
        String full_adress_to_avatar = "https://overcome-api.herokuapp.com" + json.avatar;
        Uri uri = Uri.parse(full_adress_to_avatar);
        return uri;
    }

    private static class FindJSONContactByLogin extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            HttpRequest request = HttpRequest.get("http://overcome-api.herokuapp.com/contacts/find_by_login/" + strings[0]);
            return request.body();
        }
    }
}
