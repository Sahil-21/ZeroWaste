package com.example.zerowaste.note;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Note>> allNotes0;
    private LiveData<List<Note>> allNotes1;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
        allNotes0 = repository.getAllNotes_0();
        allNotes1 = repository.getAllNotes_1();
    }
    public void insert(Note note) {
        repository.insert(note);
    }
    public void update(Note note) {
        repository.update(note);
    }
    public void delete(Note note) {
        repository.delete(note);
    }
    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
    public LiveData<List<Note>> getAllNotes_1() {
        return allNotes1;
    }
    public LiveData<List<Note>> getAllNotes_0() {
        return allNotes0;
    }
}