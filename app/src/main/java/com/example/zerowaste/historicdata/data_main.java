package com.example.zerowaste.historicdata;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerowaste.R;
import com.example.zerowaste.note.NoteViewModel;

import java.util.List;


public class data_main extends AppCompatActivity {
    private DataViewModel dataViewModel;
    RecyclerView task_recyclerView_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);

        task_recyclerView_data = findViewById(R.id.recylerview_data);
        task_recyclerView_data.setLayoutManager(new LinearLayoutManager(this));
        task_recyclerView_data.setHasFixedSize(true);
        final DataAdapter adapter = new DataAdapter();
        task_recyclerView_data.setAdapter(adapter);
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        dataViewModel.getAllDatas().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(@Nullable List<Data> datas) {
                adapter.setNotes(datas);
            }
        });
    }
}
