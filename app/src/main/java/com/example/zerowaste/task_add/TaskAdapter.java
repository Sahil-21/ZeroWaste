package com.example.zerowaste.task_add;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerowaste.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{
    private Context mContext;
    private Cursor mCursor;

    public TaskAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        public TextView t_id;
        public TextView bin5_id;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            t_id = itemView.findViewById(R.id.edttskid);
            bin5_id = itemView.findViewById(R.id.edt_bin_id);

        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }


        String id1 = mCursor.getString(mCursor.getColumnIndex(Task_List.TaskEntry._ID));
        String bid2 = mCursor.getString(mCursor.getColumnIndex(Task_List.TaskEntry.BID));
        long id = mCursor.getLong(mCursor.getColumnIndex(Task_List.TaskEntry._ID));
        holder.itemView.setTag(id);

        holder.t_id.setText(id1);
        holder.bin5_id.setText(bid2);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
