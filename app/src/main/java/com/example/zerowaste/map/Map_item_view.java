package com.example.zerowaste.map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerowaste.MapsActivity;
import com.example.zerowaste.R;
import com.example.zerowaste.Task_Individual;
import com.example.zerowaste.historicdata.DataViewModel;
import com.example.zerowaste.note.Note;
import com.example.zerowaste.note.NoteAdapter;
import com.example.zerowaste.note.NoteViewModel;
import com.example.zerowaste.note.note_main;

import java.util.List;

public class Map_item_view extends AppCompatActivity {
    private NoteViewModel noteViewModel;

    public static final int EDIT_NOTE_REQUEST = 2;
    public String BF_TIMESTAMP, BF_URL, AF_TIMESTAMP, AF_URL, TIMESTAMP;
    double latitude, longitude;
    public int bin_id, iterator, person_id;
    RecyclerView task_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_data);

        Toolbar toolbar = findViewById(R.id.toolbar_maps);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bin Locations");

        task_recyclerView = findViewById(R.id.map_recycler);
        task_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MapAdapter adapter = new MapAdapter();
        task_recyclerView.setAdapter(adapter);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNotes(notes);
                int a1 = adapter.getItemCount();
                Log.d("Checking", "aDta " + a1);

            }
        });

        adapter.setOnItemClickListener(new MapAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(Map_item_view.this, MapsActivity.class);
                intent.putExtra(Task_Individual.EXTRA_ID, note.getId());
                intent.putExtra(Task_Individual.EXTRA_BIN_ID, note.getBID());
                intent.putExtra(Task_Individual.EXTRA_PID, note.getPID());
                intent.putExtra(Task_Individual.EXTRA_LAT, note.getLAT());
                intent.putExtra(Task_Individual.EXTRA_LON, note.getLON());
                intent.putExtra(Task_Individual.EXTRA_ITERATOR, note.getITERATOR());
                intent.putExtra(Task_Individual.EXTRA_BF_TIMESTAMP, note.getBF_TIMESTAMP());
                intent.putExtra(Task_Individual.EXTRA_BF_URL, note.getBF_URL());
                intent.putExtra(Task_Individual.EXTRA_AF_TIMESTAMP, note.getAF_TIMESTAMP());
                intent.putExtra(Task_Individual.EXTRA_AF_URL, note.getAF_URL());
                intent.putExtra(Task_Individual.EXTRA_TIMESTAMP, note.getTIMESTAMP());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);

            }
        });

    }

}