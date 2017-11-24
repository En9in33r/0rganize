package com.x_c0re.a0rganize;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class CancelTheTaskDialog extends DialogFragment
{
    public static int current_post_id;
    public static String current_login;

    DBHelper helper;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure?")
                .setMessage("Warning: It causes your rating will be decreased!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        helper = new DBHelper(getActivity());
                        SQLiteDatabase db = helper.getWritableDatabase();
                        Cursor cursor;

                        String selection = "author_login = ?";
                        String selection_args[] = new String[] { current_login };

                        cursor = db.query(DBHelper.TABLE_RUNNING_TASKS,
                                new String[] { DBHelper.KEY_ID, DBHelper.KEY_AUTHOR_LOGIN, DBHelper.KEY_TEXT },
                                selection, selection_args, null, null, null);

                        if (cursor.moveToFirst())
                        {
                            for (int cunter = 0; cunter < current_post_id; cunter++)
                            {
                                cursor.moveToNext();
                            }

                            String author_login_this = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_AUTHOR_LOGIN));
                            String text_this = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TEXT));
                            String id_this = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ID));

                            ContentValues values_failed = new ContentValues();
                            values_failed.put(DBHelper.KEY_AUTHOR_LOGIN, author_login_this);
                            values_failed.put(DBHelper.KEY_TEXT, text_this);
                            db.insert(DBHelper.TABLE_FAILED_TASKS, null, values_failed);

                            db.delete(DBHelper.TABLE_RUNNING_TASKS, "_id = ?", new String[] { id_this });
                        }
                        cursor.close();

                        Fragment fragment = new CurrentTasksFragment();
                        FragmentManager manager = getFragmentManager();

                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.constraintLayoutMain, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        // не нужен код, все равно окно закроется :3
                    }
                });

        return builder.create();
    }
}
