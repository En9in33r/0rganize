package com.x_c0re.a0rganize;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class CompletedTasksFragment extends ListFragment
{
    public static ArrayList<HashMap<String, String>> dataCompleted;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        /* SimpleAdapter adapter = new SimpleAdapter(getActivity(), dataCompleted, R.id.listViewCompleted,
                new String[] { "ID", "TaskText" }, new int[] { 1, 2 } ); */

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_completed, container, false);
    }
}
