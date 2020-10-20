package com.example.zerowaste.historicdata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.zerowaste.historicdata.Data;

import java.util.List;

@Dao
public interface DataDao {
    @Insert
    void insert(Data data);

    @Query("SELECT * FROM data_table ORDER BY pk DESC")
    LiveData<List<Data>> getAllDatas();

}

