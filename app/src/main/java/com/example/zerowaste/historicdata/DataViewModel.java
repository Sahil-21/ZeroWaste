package com.example.zerowaste.historicdata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.zerowaste.note.Note;
import com.example.zerowaste.note.NoteRepository;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<List<Data>> allDatas;

    public DataViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
        allDatas = repository.getAllDatas();

    }

    public void insert(Data data) {
        repository.insert(data);
    }

    public LiveData<List<Data>> getAllDatas() {
        return allDatas;
    }
}
