package com.x_c0re.a0rganize;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrentTasksFragment extends ListFragment
{
    public static ArrayList<CurrentTask> data = new ArrayList<>();
    CurrentTasksAdapter adapter;

    public static String current_login;
    DBHelper helper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        adapter = new CurrentTasksAdapter(getActivity(), data);

        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        helper = new DBHelper(getActivity());
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor;

        String selection = "author_login = ?";

        String[] selectionArgs = new String[] { current_login };

        cursor = db.query(DBHelper.TABLE_RUNNING_TASKS,
                new String[] {DBHelper.KEY_ID, DBHelper.KEY_AUTHOR_LOGIN, DBHelper.KEY_TEXT },
                selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                data.add(new CurrentTask(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_AUTHOR_LOGIN)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEXT))));

                cursor.moveToNext();
            }
        }

        cursor.close();

        return inflater.inflate(R.layout.current_tasks_fragment, container, false);
    }

    @Override
    public void onDestroyView()
    {
        data.clear();
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        data.clear();
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        data.clear();
        super.onDetach();
    }
}
