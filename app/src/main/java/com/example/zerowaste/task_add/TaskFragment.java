package com.example.zerowaste.task_add;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.zerowaste.R;

public class TaskFragment extends AppCompatActivity implements Task_ExampleDialog.ExampleDialogListener{

    private SQLiteDatabase nDatabase;
    private TaskAdapter nAdapter;
    public String latitude, longitude, bin_id, iterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);

        TaskDBHelper tsk_dbHelper = new TaskDBHelper(this);
        nDatabase = tsk_dbHelper.getWritableDatabase();

        RecyclerView task_recyclerView = findViewById(R.id.tsk_recycler);
        task_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nAdapter = new TaskAdapter(this, getAllItems());
        task_recyclerView.setAdapter(nAdapter);

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
        }).attachToRecyclerView(task_recyclerView);

    }

    public void onfbbuttonclick_task(View view) {
        openDialog();
    }

    public void openDialog() {
        Task_ExampleDialog exampleDialog = new Task_ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String lat1, String lon1, String bin, String itr) {
        latitude = lat1;
        longitude = lon1;
        bin_id = bin;
        iterator = itr;
        additem();
    }

    private void additem () {
        Toast.makeText(TaskFragment.this, "Sucessful", Toast.LENGTH_LONG).show();
        if (latitude.trim().length() == 0 || longitude.trim().length() == 0) {
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(Task_List.TaskEntry.LAT, latitude);
        cv.put(Task_List.TaskEntry.LON, longitude);
        cv.put(Task_List.TaskEntry.BID, bin_id);
        cv.put(Task_List.TaskEntry.ITERATOR, iterator);
        nDatabase.insert(Task_List.TaskEntry.TABLE_NAME, null, cv);
        nAdapter.swapCursor(getAllItems());

    }

    private void removeItem(long id) {
        nDatabase.delete(Task_List.TaskEntry.TABLE_NAME,
                Task_List.TaskEntry._ID + "=" + id, null);
        nAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems() {
        return nDatabase.query(
                Task_List.TaskEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Task_List.TaskEntry.TIMESTAMP + " ASC"
        );
    }


}