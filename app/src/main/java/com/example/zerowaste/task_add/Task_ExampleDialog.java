package com.example.zerowaste.task_add;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.zerowaste.R;

public class Task_ExampleDialog extends AppCompatDialogFragment {
    private EditText lat;
    private EditText lon;
    private EditText bid;
    private EditText itr;
    private ExampleDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_task_dialog, null);

        lat = view.findViewById(R.id.edtlat);
        lon = view.findViewById(R.id.edtlon);
        bid = view.findViewById(R.id.bin_id);
        itr = view.findViewById(R.id.edit_iterator);

        builder.setView(view)
                .setTitle("Add Bin")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String lat1 = lat.getText().toString();
                        String lon1 = lon.getText().toString();
                        String bid1 = bid.getText().toString();
                        String itr1 = itr.getText().toString();
                        listener.applyTexts(lat1, lon1, bid1, itr1);
                    }
                });
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface ExampleDialogListener {
        void applyTexts(String lat1, String lon1,String bid1, String itr1);

    }
}