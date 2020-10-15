package com.example.zerowaste;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.SharedPreferences;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import com.example.zerowaste.map.Map_item_view;
import com.example.zerowaste.model.User;
import com.example.zerowaste.model.databaseHelper;
import com.example.zerowaste.note.Note;
import com.example.zerowaste.note.NoteAdapter;
import com.example.zerowaste.note.NoteViewModel;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zerowaste.Login.EMAIL;
import static com.example.zerowaste.Login.SHARED_PREFS;

public class HomeFragment extends Fragment {
    TextView tr, tc, tt,tv_name,tv_tida;
    final NoteAdapter adapter = new NoteAdapter();
    final NoteAdapter adapter1 = new NoteAdapter();
    private NoteViewModel noteViewModel;
    ImageView trac;
    User curr_user;
    String curr_email,curr_name;
    databaseHelper db;
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
        trac= v.findViewById(R.id.tackkk);
        tv_name = v.findViewById(R.id.tv_name);
  //      tv_tida = v.findViewById(R.id.tida);
    //    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
   //     tv_tida.setText(currentDateTimeString);

        TextClock textClock = (TextClock) v.findViewById(R.id.textClock);
        textClock.setFormat12Hour(null);
        //textClock.setFormat24Hour("dd/MM/yyyy hh:mm:ss a");
        textClock.setFormat24Hour(" EEE MMM d hh:mm:ss a ");

        db = new databaseHelper(getContext());
        loadData();
        curr_user = new User();
        curr_user= db.getUser(curr_email);
        curr_name = curr_user.getName();

        tv_name.setText(curr_name);
        trac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opentrack();
            }
        });

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

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        curr_email = sharedPreferences.getString(EMAIL,"null");
        Toast.makeText(getContext(), curr_email, Toast.LENGTH_SHORT).show();
    }

    private void opentrack() {
        Intent intent = new Intent(getContext(), Map_item_view.class);
        startActivity(intent);
    }
}