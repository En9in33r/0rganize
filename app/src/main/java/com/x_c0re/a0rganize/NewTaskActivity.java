package com.x_c0re.a0rganize;

import android.content.Intent;
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

    HashMap<String, String> map;

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
            // there will be BLOOD x_0 <:8~~ <:8~~

            map = new HashMap<>();
            CurrentTasksFragment.data.add(map);
            map.put("ID", "Task #" + (CurrentTasksFragment.data.indexOf(map) + 1));
            map.put("TaskText", mEnterTask.getText().toString());

            Toast toast = Toast.makeText(this, "Succesfully!", Toast.LENGTH_LONG);
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
