package com.x_c0re.a0rganize;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrentTasksAdapter extends BaseAdapter
{
    Context context;
    LayoutInflater inflater;
    ArrayList<CurrentTask> currentTasks;

    CurrentTasksAdapter(Context context, ArrayList<CurrentTask> currentTasks)
    {
        this.context = context;
        this.currentTasks = currentTasks;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return currentTasks.size();
    }

    @Override
    public Object getItem(int position)
    {
        return currentTasks.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if (view == null)
        {
            view = inflater.inflate(R.layout.new_task_element, parent, false);
        }

        CurrentTask t = getCurrentTask(position);

        ((TextView)view.findViewById(R.id.textAuthorLogin)).setText(t.author_login);
        ((TextView)view.findViewById(R.id.text_element)).setText(t.text);

        return view;
    }

    CurrentTask getCurrentTask(int position)
    {
        return ((CurrentTask)getItem(position));
    }
}
