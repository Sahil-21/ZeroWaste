package com.example.zerowaste.task_add;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zerowaste.task_add.Task_List.TaskEntry;

public class TaskDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tasklist.db";
    public static final int DATABASE_VERSION = 1;
    public TaskDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TASKLIST_TABLE = "CREATE TABLE " +
                TaskEntry.TABLE_NAME + " (" +
                TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskEntry.BID + " INTEGER , " +
                TaskEntry.PID + " INTEGER , " +
                TaskEntry.LAT + " INTEGER NOT NULL, " +
                TaskEntry.LON + " INTEGER NOT NULL, " +
                TaskEntry.ITERATOR + " INTEGER DEFAULT 0, " +
                TaskEntry.BF_TIMESTAMP + " TIMESTAMP, " +
                TaskEntry.BF_URL + " TEXT , " +
                TaskEntry.AF_TIMESTAMP + " TIMESTAMP, " +
                TaskEntry.AF_URL + " TEXT , " +
                TaskEntry.TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_TASKLIST_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}