package com.x_c0re.a0rganize;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class NewTaskActivity extends AppCompatActivity
{
    private EditText mEnterTask;

    DBHelper helper;

    public static String current_login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        mEnterTask = (EditText)findViewById(R.id.editTaskText);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.new_task_form_label);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.top_right_start_mission_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.start_mission_button)
        {
            CurrentTasksFragment.data.clear();

            helper = new DBHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DBHelper.KEY_AUTHOR_LOGIN, current_login);
            values.put(DBHelper.KEY_TEXT, mEnterTask.getText().toString());

            db.insert(DBHelper.TABLE_RUNNING_TASKS, null, values);
            db.close();

            Toast toast = Toast.makeText(this, "Successfully!", Toast.LENGTH_LONG);
            toast.show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == android.R.id.home)
        {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
