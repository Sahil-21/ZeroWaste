package com.example.zerowaste.historicdata;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.zerowaste.note.Note;

@Database(entities = {Data.class}, version = 1, exportSchema = false)
public abstract class DataDatabase extends RoomDatabase {
    private static DataDatabase instance;
    public abstract DataDao dataDao();
    public static synchronized DataDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DataDatabase.class, "data_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DataDao dataDao;
        private PopulateDbAsyncTask(DataDatabase db) {
            dataDao = db.dataDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}