package com.example.zerowaste;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zerowaste.historicdata.DataViewModel;
import com.example.zerowaste.note.Note;
import com.example.zerowaste.note.NoteAdapter;
import com.example.zerowaste.note.NoteViewModel;

import java.util.List;

public class HomeFragment extends Fragment {
    TextView tr, tc, tt;
    final NoteAdapter adapter = new NoteAdapter();
    final NoteAdapter adapter1 = new NoteAdapter();
    private NoteViewModel noteViewModel;

    private static final String REMAINING = "argrem";
    private static final String COMPLETED = "argcom";
    private static final String TOTAL = "argtot";
    private int r,c,t;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        tr = v.findViewById(R.id.rem_task);
        tc = v.findViewById(R.id.com_task);
        tt = v.findViewById(R.id.total_task);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes_0().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNotes(notes);
                r = adapter.getItemCount();
                tr.setText(String.valueOf(r));

            }
        });
        noteViewModel.getAllNotes_1().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter1.setNotes(notes);
                c = adapter1.getItemCount();
                tc.setText(String.valueOf(c));
            }
        });
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter1.setNotes(notes);
                t = adapter1.getItemCount();
                tt.setText(String.valueOf(t));
            }
        });
        
        return v;

    }
}