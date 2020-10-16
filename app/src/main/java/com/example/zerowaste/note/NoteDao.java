package com.example.zerowaste.note;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("Update note_table SET ITERATOR=0,AF_URL= null,BF_URL=null,BF_TIMESTAMP='1',AF_TIMESTAMP ='2'")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY BID ASC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM note_table WHERE ITERATOR =0")
    LiveData<List<Note>> getAllNotes_0();

    @Query("SELECT * FROM note_table WHERE ITERATOR =1")
    LiveData<List<Note>> getAllNotes_1();
}