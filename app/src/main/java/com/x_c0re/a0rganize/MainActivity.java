package com.x_c0re.a0rganize;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private Fragment fragment;
    private FragmentManager manager;

    private TextView login_view;

    private ImageButton mProfileButton;

    SharedPreferences sharedPreferencesLogin;
    SharedPreferences.Editor editorLogin;

    // SharedPreferences sharedPreferencesLogin;
    // SharedPreferences.Editor editorLogin;

    public static String login_bridge;

    public static String check_for_login = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (check_for_login.equals("moved"))
        {
            saveLogin(login_bridge);
            // saveLogin(login_bridge);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mProfileButton = navigationView.getHeaderView(0).findViewById(R.id.avatarImage);
        mProfileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        login_view = navigationView.getHeaderView(0).findViewById(R.id.textViewLogin); // <======== IS HERE

        String a = loadLogin();
        login_view.setText(a);

        fragment = new CurrentTasksFragment();
        manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.constraintLayoutMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        CurrentTasksFragment.current_login = login_view.getText().toString();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

                eraseLogin();
                // eraseLogin();
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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
        sharedPreferencesLogin = getPreferences(MODE_PRIVATE);
        editorLogin = sharedPreferencesLogin.edit();
        editorLogin.putString("saved_text", login);
        editorLogin.apply();
    }

    public String loadLogin()
    {
        sharedPreferencesLogin = getPreferences(MODE_PRIVATE);
        return sharedPreferencesLogin.getString("saved_text", "");
    }

    public void eraseLogin()
    {
        sharedPreferencesLogin = getPreferences(MODE_PRIVATE);
        editorLogin = sharedPreferencesLogin.edit();
        editorLogin.clear();
        editorLogin.apply();
    }
}
