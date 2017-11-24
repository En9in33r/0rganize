package com.x_c0re.a0rganize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class FailedTasksAdapter extends BaseAdapter
{
    Context context;
    LayoutInflater inflater;
    ArrayList<FailedTask> failedTasks;

    FailedTasksAdapter(Context context, ArrayList<FailedTask> failedTasks)
    {
        this.context = context;
        this.failedTasks = failedTasks;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return failedTasks.size();
    }

    @Override
    public Object getItem(int position)
    {
        return failedTasks.get(position);
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
            view = inflater.inflate(R.layout.failed_task_element, parent, false);
        }

        FailedTask f = getFailedTask(position);

        ((TextView)view.findViewById(R.id.textAuthorLogin_failed)).setText(f.author_login);
        ((TextView)view.findViewById(R.id.text_element_failed)).setText(f.text);

        return view;
    }

    FailedTask getFailedTask(int position)
    {
        return (FailedTask)getItem(position);
    }
}
