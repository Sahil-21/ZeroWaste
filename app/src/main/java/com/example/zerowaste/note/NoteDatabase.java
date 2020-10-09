package com.example.zerowaste.note;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;
    public abstract NoteDao noteDao();
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
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
        private NoteDao noteDao;
        private PopulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note(24,10,55.55,45.66,0,"fgd",null,"1",null, "ngdfull"));
            noteDao.insert(new Note(23,10,55.55,45.66,0,"nufgdll",null,"1",null, "nugdfll"));
            noteDao.insert(new Note(25,10,55.55,45.66,0,"gd",null,"1",null, "nugfdll"));
            noteDao.insert(new Note(26,10,55.55,45.66,0,"gd",null,"1",null, "nugfdll"));
            return null;
        }
    }
}