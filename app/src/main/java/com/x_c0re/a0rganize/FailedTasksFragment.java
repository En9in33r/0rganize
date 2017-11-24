package com.x_c0re.a0rganize;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FailedTasksFragment extends ListFragment
{
    public static ArrayList<FailedTask> failedTasksData = new ArrayList<>();
    FailedTasksAdapter failed_adapter;
    public static String current_login;

    DBHelper helper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        failed_adapter = new FailedTasksAdapter(getActivity(), failedTasksData);
        setListAdapter(failed_adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        helper = new DBHelper(getActivity());
        Cursor cursor;
        SQLiteDatabase db = helper.getWritableDatabase();

        String selection = "author_login = ?";
        String[] selection_args = new String[] { current_login };

        cursor = db.query(DBHelper.TABLE_FAILED_TASKS,
                new String[] { DBHelper.KEY_ID, DBHelper.KEY_AUTHOR_LOGIN, DBHelper.KEY_TEXT },
                selection, selection_args, null, null, null);

        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                failedTasksData.add(new FailedTask(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_AUTHOR_LOGIN)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEXT))));
                cursor.moveToNext();
            }
        }

        cursor.close();

        return inflater.inflate(R.layout.failed_tasks_fragment, container, false);
    }

    @Override
    public void onDestroyView()
    {
        failedTasksData.clear();
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        failedTasksData.clear();
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        failedTasksData.clear();
        super.onDetach();
    }
}
