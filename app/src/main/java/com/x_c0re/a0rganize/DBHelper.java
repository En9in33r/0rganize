package com.x_c0re.a0rganize;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "contactDB";

    public static final String TABLE_CONTACTS = "contacts";
    public static final String TABLE_RUNNING_TASKS = "running_tasks";
    public static final String TABLE_PUBLISHED_TASKS = "published_tasks";
    public static final String TABLE_FAILED_TASKS = "failed_tasks";
    public static final String TABLE_COMPLETED_TASKS = "completed_tasks";

    public static final String KEY_ID = "_id";

    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONE = "phone";

    public static final String KEY_AUTHOR_LOGIN = "author_login";
    public static final String KEY_TEXT = "text";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID
        + " integer primary key," + KEY_NAME + " text," + KEY_SURNAME + " text,"
                + KEY_LOGIN + " text," + KEY_PASSWORD + " text," + KEY_PHONE + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_RUNNING_TASKS + "(" + KEY_ID
                + " integer primary key," + KEY_AUTHOR_LOGIN + " text," + KEY_TEXT + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_PUBLISHED_TASKS + "(" + KEY_ID
                + " integer primary key," + KEY_AUTHOR_LOGIN + " text," + KEY_TEXT + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_FAILED_TASKS + "(" + KEY_ID
                + " integer primary key," + KEY_AUTHOR_LOGIN + " text," + KEY_TEXT + " text" + ")");

        sqLiteDatabase.execSQL("create table " + TABLE_COMPLETED_TASKS + "(" + KEY_ID
                + " integer primary key," + KEY_AUTHOR_LOGIN + " text," + KEY_TEXT + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_CONTACTS);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_RUNNING_TASKS);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_PUBLISHED_TASKS);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_FAILED_TASKS);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_COMPLETED_TASKS);
        onCreate(sqLiteDatabase);
    }
}
