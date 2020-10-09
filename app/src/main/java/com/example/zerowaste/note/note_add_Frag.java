package com.example.zerowaste.note;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerowaste.R;


import com.example.zerowaste.note.*;
import java.util.List;

public class note_add_Frag extends AppCompatActivity implements Note_ExampleDialog.ExampleDialogListener{
    private NoteViewModel noteViewModel;
    private RecyclerView task_recyclerView;
    public String  BF_TIMESTAMP, BF_URL,AF_TIMESTAMP,AF_URL,TIMESTAMP;
    double latitude=0, longitude=0;
    public int  bin_id=0, iterator,person_id;
    public static final int ADD_NOTE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);


        task_recyclerView = findViewById(R.id.tsk_recycler);
        task_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final NoteAdapter adapter = new NoteAdapter();
        task_recyclerView.setAdapter(adapter);


        //    taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNotes(notes);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView task_recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(note_add_Frag.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(task_recyclerView);


    }


    public void openDialog_note(View view) {
        Note_ExampleDialog exampleDialog = new Note_ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(double lat1, double lon1, int bin, int itr) {
        latitude = lat1;
        longitude = lon1;
        bin_id = bin;
        iterator = itr;
        person_id=10;
        additem();
    }

    private void additem () {
        Toast.makeText(note_add_Frag.this, "Sucessful", Toast.LENGTH_LONG).show();
        if (bin_id==0 && latitude==0 && longitude==0  ) {
            Toast.makeText(this, "Task not saved", Toast.LENGTH_SHORT).show();
            return;
        }
        BF_TIMESTAMP= BF_URL=AF_TIMESTAMP=AF_URL=TIMESTAMP=null;
        Note note = new Note(bin_id, person_id,latitude,longitude,iterator, BF_TIMESTAMP, BF_URL,AF_TIMESTAMP,AF_URL,TIMESTAMP );
        noteViewModel.insert(note);
        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();

    }


}