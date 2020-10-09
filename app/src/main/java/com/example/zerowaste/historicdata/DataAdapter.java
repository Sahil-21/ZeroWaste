package com.example.zerowaste.historicdata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerowaste.R;


import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {
    private List<Data> datas = new ArrayList<>();
    private OnItemClickListener listener;
    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_item, parent, false);
        return new DataHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {
        Data currentNote = datas.get(position);
        holder.textViewTitle.setText(String.valueOf(currentNote.getPk()));
        holder.textTime.setText(String.valueOf(currentNote.getTIMESTAMP()));
        holder.textViewDescription.setText(String.valueOf(currentNote.getBID()));

    }
    @Override
    public int getItemCount() {
        return datas.size();
    }
    public void setNotes(List<Data> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public Data getDataAt(int position) {
        return datas.get(position);
    }

    class DataHolder extends RecyclerView.ViewHolder {

        private TextView textTime;
        private TextView textViewTitle;
        private TextView textViewDescription;

        public DataHolder(View itemView) {
            super(itemView);
            textTime = itemView.findViewById(R.id.times);
            textViewTitle = itemView.findViewById(R.id.eddtsl);
            textViewDescription = itemView.findViewById(R.id.eddbin);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(datas.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Data data);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}