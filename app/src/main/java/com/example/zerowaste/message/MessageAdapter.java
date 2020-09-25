package com.example.zerowaste.message;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerowaste.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{
    private Context mContext;
    private Cursor mCursor;

    public MessageAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        public TextView tname;
        public TextView tsub;
        public TextView tmsg;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            tname = itemView.findViewById(R.id.cd_name);
            tsub = itemView.findViewById(R.id.cd_sub);
            tmsg = itemView.findViewById(R.id.cd_msg);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_message_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String name5 = mCursor.getString(mCursor.getColumnIndex(Message_List.MessageEntry.NAME));
        String sub5 = mCursor.getString(mCursor.getColumnIndex(Message_List.MessageEntry.SUBJECT));
        String msg5 = mCursor.getString(mCursor.getColumnIndex(Message_List.MessageEntry.MSG));
        long id = mCursor.getLong(mCursor.getColumnIndex(Message_List.MessageEntry._ID));
        holder.itemView.setTag(id);

        holder.tname.setText(name5);
        holder.tsub.setText(sub5);
        holder.tmsg.setText(msg5);
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
