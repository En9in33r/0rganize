package com.x_c0re.a0rganize;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

public class CurrentTasksFragment extends ListFragment
{
    public static ArrayList<HashMap<String, String>> data = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.new_task_element,
                new String[] {"ID", "TaskText"}, new int[] {R.id.textTaskID, R.id.text_element} );

        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.current_tasks_fragment, container, false);
    }
}
