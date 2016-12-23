package com.example.amank.personal_notes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aman K on 11/13/2016.
 */

public class TaskDbHelper extends SQLiteOpenHelper {

    public TaskDbHelper(Context context) {
        super(context, com.example.amank.personal_notes.db.TaskContract.DB_NAME, null, com.example.amank.personal_notes.db.TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + com.example.amank.personal_notes.db.TaskContract.TaskEntry.TABLE + " ( " +
                com.example.amank.personal_notes.db.TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                com.example.amank.personal_notes.db.TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + com.example.amank.personal_notes.db.TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }
}
