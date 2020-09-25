package com.example.zerowaste.message;

import android.provider.BaseColumns;

public class Message_List {
    private Message_List() {
    }

    public static final class MessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "messagge_List";
        public static final String NAME = "name";
        public static final String SUBJECT = "subject";
        public static final String MSG = "message";
        public static final String TIMESTAMP = "timestamp";
    }
}
