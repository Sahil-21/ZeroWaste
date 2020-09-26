package com.example.zerowaste.task_add;

import android.provider.BaseColumns;

public class Task_List {
    private Task_List() {
    }

    public static final class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "task_List";
        public static final String BID= "bin_id";
        public static final String PID= "person_id";
        public static final String LAT = "latitude";
        public static final String LON = "longitude";
        public static final String ITERATOR = "iterator";
        public static final String BF_TIMESTAMP = "before_timestamp";
        public static final String BF_URL = "before_url";
        public static final String AF_TIMESTAMP = "after_timestamp";
        public static final String AF_URL = "after_url";
        public static final String TIMESTAMP = "timestamp";
    }
}
