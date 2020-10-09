package com.example.zerowaste.historicdata;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {
    private DataDao dataDao;
    private LiveData<List<Data>> allDatas;

    public DataRepository(Application application) {
        DataDatabase database = DataDatabase.getInstance(application);
        dataDao = database.dataDao();
        allDatas = dataDao.getAllDatas();

    }
    public void insert(Data data) {
        new InsertNoteAsyncTask(dataDao).execute(data);
    }

    public LiveData<List<Data>> getAllDatas() {
        return allDatas;
    }


    private static class InsertNoteAsyncTask extends AsyncTask<Data, Void, Void> {
        private DataDao dataDao;
        private InsertNoteAsyncTask(DataDao noteDao) {
            this.dataDao = noteDao;
        }
        @Override
        protected Void doInBackground(Data... datas) {
            dataDao.insert(datas[0]);
            return null;
        }
    }


}