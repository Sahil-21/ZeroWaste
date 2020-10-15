package com.example.zerowaste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zerowaste.message.MessageAdapter;
import com.example.zerowaste.message.MessageDBHelper;
import com.example.zerowaste.message.Message_ExampleDialog;
import com.example.zerowaste.message.Message_List;
import com.example.zerowaste.model.User;
import com.example.zerowaste.model.databaseHelper;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zerowaste.Login.EMAIL;
import static com.example.zerowaste.Login.SHARED_PREFS;

public class MessageFragment extends AppCompatActivity implements Message_ExampleDialog.ExampleDialogListener {

    private SQLiteDatabase mDatabase;
    private MessageAdapter mAdapter;
    private TextView sub;
    private TextView msg;
    public String subject,message;
    databaseHelper db;
    User curr_user;
    String curr_email,curr_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_message);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Messages");

        db = new databaseHelper(this);
        loadData();
        curr_user = new User();
        curr_user= db.getUser(curr_email);
        curr_name = curr_user.getName();

        MessageDBHelper dbHelper = new MessageDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.msg_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MessageAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        curr_email = sharedPreferences.getString(EMAIL,"null");
        Toast.makeText(this, curr_email, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    public void onfbbuttonclick(View view) {
        openDialog();
    }

    public void openDialog() {
        Message_ExampleDialog messageExampleDialog = new Message_ExampleDialog();
        messageExampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String sub1, String msg1) {
        subject = sub1;
        message =msg1;
        additem();
    }

    private void additem () {
        Toast.makeText(MessageFragment.this, subject, Toast.LENGTH_LONG).show();
        if (subject.trim().length() == 0 || message.trim().length() == 0) {
            return;
        }
        String name = curr_name;
        ContentValues cv = new ContentValues();
        cv.put(Message_List.MessageEntry.NAME, name);
        cv.put(Message_List.MessageEntry.SUBJECT, subject);
        cv.put(Message_List.MessageEntry.MSG, message);
        mDatabase.insert(Message_List.MessageEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());

    }

    private void removeItem(long id) {
        mDatabase.delete(Message_List.MessageEntry.TABLE_NAME,
                Message_List.MessageEntry._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems() {
        return mDatabase.query(
                Message_List.MessageEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Message_List.MessageEntry.TIMESTAMP + " DESC"
        );
    }
}