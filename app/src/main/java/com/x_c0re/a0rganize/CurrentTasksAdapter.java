package com.x_c0re.a0rganize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CurrentTasksAdapter extends BaseAdapter
{
    Context context;
    LayoutInflater inflater;
    ArrayList<CurrentTask> currentTasks;
    ImageButton mFailTask;
    ImageButton mCompleteTask;

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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if (view == null)
        {
            view = inflater.inflate(R.layout.current_task_element, parent, false);
        }

        CurrentTask t = getCurrentTask(position);

        ((TextView)view.findViewById(R.id.textAuthorLogin)).setText(t.author_login);
        ((TextView)view.findViewById(R.id.text_element)).setText(t.text);

        mFailTask = view.findViewById(R.id.canselTaskButton);
        mCompleteTask = view.findViewById(R.id.completeTaskButton);

        View.OnClickListener mClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switch (view.getId())
                {
                    case R.id.canselTaskButton:
                        CancelTheTaskDialog.current_post_id = Integer.parseInt(view.getTag().toString());
                        CancelTheTaskDialog dialog = new CancelTheTaskDialog();
                        dialog.show(((MainActivity)context).getSupportFragmentManager() , "dialog");

                        break;
                    case R.id.completeTaskButton:

                        break;
                }
            }
        };

        mFailTask.setOnClickListener(mClickListener);
        mCompleteTask.setOnClickListener(mClickListener);

        mFailTask.setTag(position);
        mCompleteTask.setTag(position);

        return view;
    }

    CurrentTask getCurrentTask(int position)
    {
        return ((CurrentTask)getItem(position));
    }
}
