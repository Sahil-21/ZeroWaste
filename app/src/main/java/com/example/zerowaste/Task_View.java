package com.example.zerowaste;
import com.example.zerowaste.task_add.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

public class Task_View extends AppCompatActivity {

    private SQLiteDatabase rDatabase;
    private TaskAdapter rAdapter;
    public String latitude, longitude, bin_id, iterator;
    RecyclerView task_recyclerView_0,task_recyclerView_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_task);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Task");

        task_recyclerView_0 = findViewById(R.id.tsk_recycler_0);
        task_recyclerView_0.setLayoutManager(new LinearLayoutManager(this));

        task_recyclerView_1 = findViewById(R.id.tsk_recycler_1);
        task_recyclerView_1.setLayoutManager(new LinearLayoutManager(this));

        TaskDBHelper tsk_dbHelper0 = new TaskDBHelper(this);
        rDatabase = tsk_dbHelper0.getReadableDatabase();

        rAdapter = new TaskAdapter(this, getAllItems_0());
        task_recyclerView_0.setAdapter(rAdapter);

        rAdapter = new TaskAdapter(this, getAllItems_1());
        task_recyclerView_1.setAdapter(rAdapter);

    }

    private Cursor getAllItems_0() {
        String qry = "SELECT * FROM task_List WHERE ITERATOR = '0' ";
        Cursor cur =  rDatabase.rawQuery(qry,null);
        return cur;
    }

    private Cursor getAllItems_1() {
        String qry = "SELECT * FROM task_List WHERE ITERATOR = '1' ";
        Cursor cur =  rDatabase.rawQuery(qry,null);
        return cur;
    }

    public void onclick_visiblility_rec0(View view) {
        if (task_recyclerView_0.getVisibility() == View.GONE) {
            task_recyclerView_0.setVisibility(View.VISIBLE);
        }else{
            task_recyclerView_0.setVisibility(View.GONE);
        }
    }

    public void onclick_visiblility_rec1(View view) {
        if (task_recyclerView_1.getVisibility() == View.GONE) {
            task_recyclerView_1.setVisibility(View.VISIBLE);
        }else{
            task_recyclerView_1.setVisibility(View.GONE);
        }
    }



}