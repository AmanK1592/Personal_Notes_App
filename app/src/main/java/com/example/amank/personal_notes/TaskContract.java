package com.example.amank.personal_notes.db;

import android.provider.BaseColumns;

/**
 * Created by Aman K on 11/13/2016.
 */

public class TaskContract {

    public static final String DB_NAME = "com.example.amank.personal_notes.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "notes";

        public static final String COL_TASK_TITLE = "title";
    }

}
