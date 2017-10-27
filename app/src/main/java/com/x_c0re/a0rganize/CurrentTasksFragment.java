package com.x_c0re.a0rganize;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrentTasksFragment extends ListFragment
{
    public static ArrayList<HashMap<String, String>> data = new ArrayList<>();

    DBHelper helper;

    public static String current_login;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.new_task_element,
                new String[] {"AuthorLogin", "TaskText"}, new int[] {R.id.textTaskID, R.id.text_element} );

        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        helper = new DBHelper(getActivity());
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor;

        String selection = "author_login = ?";

        String[] selectionArgs = new String[] { current_login }; // заглушка

        cursor = db.query(DBHelper.TABLE_RUNNING_TASKS,
                new String[] {DBHelper.KEY_ID, DBHelper.KEY_AUTHOR_LOGIN, DBHelper.KEY_TEXT },
                selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst())
        {
            while (!cursor.isAfterLast())
            {
                HashMap<String, String> map = new HashMap<>();
                map.put("AuthorLogin", cursor.getString(cursor.getColumnIndex(DBHelper.KEY_AUTHOR_LOGIN)));
                map.put("TaskText", cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEXT)));
                data.add(map);

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
