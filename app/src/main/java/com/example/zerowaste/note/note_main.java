package com.example.zerowaste.note;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.zerowaste.R;
import com.example.zerowaste.Task_Individual;
import com.example.zerowaste.historicdata.DataViewModel;
import java.util.List;

public class note_main extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    private DataViewModel dataViewModel;
    public static final int EDIT_NOTE_REQUEST = 2;
    public String BF_TIMESTAMP, BF_URL, AF_TIMESTAMP, AF_URL, TIMESTAMP;
    double latitude, longitude;
    public int bin_id, iterator, person_id;
    RecyclerView task_recyclerView_0;
    RecyclerView task_recyclerView_1;
    public static final int CAMERA_PER_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    public static final int RESTORAGE = 105;
    public static final int WESTORAGE = 106;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_task);
        task_recyclerView_0 = findViewById(R.id.tsk_recycler_0);
        task_recyclerView_0.setLayoutManager(new LinearLayoutManager(this));
        task_recyclerView_1 = findViewById(R.id.tsk_recycler_1);
        task_recyclerView_1.setLayoutManager(new LinearLayoutManager(this));

        final NoteAdapter adapter = new NoteAdapter();
        final NoteAdapter adapter1 = new NoteAdapter();
        task_recyclerView_0.setAdapter(adapter);
        task_recyclerView_1.setAdapter(adapter1);
        int a = adapter.getItemCount();
        Log.d("Fucked", "aDta " + a);


        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes_0().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNotes(notes);
                int a1 = adapter.getItemCount();
                Log.d("Fucked Twice", "aDta " + a1);

            }
        });
        noteViewModel.getAllNotes_1().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter1.setNotes(notes);
            }
        });

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(note_main.this, Task_Individual.class);
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
        adapter1.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(note_main.this, Task_Individual.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(Task_Individual.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }


            bin_id =data.getIntExtra(Task_Individual.EXTRA_BIN_ID,0);
            iterator =data.getIntExtra(Task_Individual.EXTRA_ITERATOR,0);
            person_id =data.getIntExtra(Task_Individual.EXTRA_PID,0);
            latitude = data.getDoubleExtra(Task_Individual.EXTRA_LAT,0);
            longitude = data.getDoubleExtra(Task_Individual.EXTRA_LON,0);
            BF_TIMESTAMP = data.getStringExtra(Task_Individual.EXTRA_BF_TIMESTAMP);
            BF_URL = data.getStringExtra(Task_Individual.EXTRA_BF_URL);
            AF_TIMESTAMP = data.getStringExtra(Task_Individual.EXTRA_AF_TIMESTAMP);
            AF_URL = data.getStringExtra(Task_Individual.EXTRA_AF_URL);
            TIMESTAMP = data.getStringExtra(Task_Individual.EXTRA_TIMESTAMP);

            Note note = new Note(bin_id, person_id, latitude, longitude, iterator, BF_TIMESTAMP, BF_URL, AF_TIMESTAMP, AF_URL, TIMESTAMP);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }


    public void onclick_visiblility_rec0(View view) {
        if (task_recyclerView_0.getVisibility() == View.GONE) {
            task_recyclerView_0.setVisibility(View.VISIBLE);
        } else {
            task_recyclerView_0.setVisibility(View.GONE);
        }
    }

    public void onclick_visiblility_rec1(View view) {
        if (task_recyclerView_1.getVisibility() == View.GONE) {
            task_recyclerView_1.setVisibility(View.VISIBLE);
        } else {
            task_recyclerView_1.setVisibility(View.GONE);
        }
    }


}