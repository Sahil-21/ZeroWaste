package com.example.zerowaste;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import com.example.zerowaste.historicdata.Data;
import com.example.zerowaste.historicdata.DataViewModel;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.zerowaste.note.note_main.CAMERA_PER_CODE;
import static com.example.zerowaste.note.note_main.CAMERA_REQUEST_CODE;


public class Task_Individual extends AppCompatActivity implements View.OnClickListener {
    private DataViewModel dataViewModel;
    TextView textViewbin_id;
    ImageView before, after;
    public String BF_TIMESTAMP, BF_URL, AF_TIMESTAMP, AF_URL, TIMESTAMP;
    double latitude, longitude;
    public int bin_id, iterator, person_id, ids;
    int flag;
    public String main_url;


    Intent data = new Intent();
    public static final String EXTRA_ID =
            "com.example.zerowaste.EXTRA_ID";
    public static final String EXTRA_BIN_ID =
            "com.example.zerowaste.EXTRA_BIN_ID";

    public static final String EXTRA_PID =
            "com.example.zerowaste.EXTRA_PID";

    public static final String EXTRA_LAT = "com.example.zerowaste.EXTRA_LAT";
    public static final String EXTRA_LON = "com.example.zerowaste.EXTRA_LON";
    public static final String EXTRA_ITERATOR = "com.example.zerowaste.EXTRA_ITERATOR";
    public static final String EXTRA_BF_TIMESTAMP = "com.example.zerowaste.EXTRA_BF_TIMESTAMP";
    public static final String EXTRA_BF_URL = "com.example.zerowaste.EXTRA_BF_URL";
    public static final String EXTRA_AF_TIMESTAMP = "com.example.zerowaste.EXTRA_AF_TIMESTAMP";
    public static final String EXTRA_AF_URL = "com.example.zerowaste.AF_URL";
    public static final String EXTRA_TIMESTAMP = "com.example.zerowaste.EXTRA_TIMESTAMP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ind_task);

        Toolbar toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Individual Task");

        Intent intent = getIntent();
        bin_id = intent.getIntExtra(EXTRA_BIN_ID, 0);
        iterator = intent.getIntExtra(EXTRA_ITERATOR, 0);
        person_id = intent.getIntExtra(EXTRA_PID, 0);
        latitude = intent.getDoubleExtra(EXTRA_LAT, 0);
        longitude = intent.getDoubleExtra(EXTRA_LON, 0);
        BF_TIMESTAMP = intent.getStringExtra(EXTRA_BF_TIMESTAMP);
        BF_URL = intent.getStringExtra(EXTRA_BF_URL);
        AF_TIMESTAMP = intent.getStringExtra(EXTRA_AF_TIMESTAMP);
        AF_URL = intent.getStringExtra(EXTRA_AF_URL);
        TIMESTAMP = intent.getStringExtra(EXTRA_TIMESTAMP);

        textViewbin_id = findViewById(R.id.t_edtdid);
        //     bin_id.setText(String.valueOf(intent.getIntExtra(EXTRA_BIN_ID,0)));
        textViewbin_id.setText(String.valueOf(intent.getIntExtra(EXTRA_BIN_ID, 0)));

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);

        before = findViewById(R.id.t_iv_beforecleaning);
        after = findViewById(R.id.t_iv_aftercleaning);

        if (BF_URL == null) {
        } else {
            File f = new File(BF_URL);
            Toast.makeText(this, "Updating 2nd time txt", Toast.LENGTH_SHORT).show();
            before.setImageURI(Uri.fromFile(f));
        }

        if (AF_URL == null) {
        } else {
            File f1 = new File(AF_URL);
            Toast.makeText(this, "Updatime txt", Toast.LENGTH_SHORT).show();
            after.setImageURI(Uri.fromFile(f1));
        }


        Button button1 = findViewById(R.id.t_btnbfclean);
        Button button2 = findViewById(R.id.t_btnaftclean);
        Button button3 = findViewById(R.id.t_btnsubmit);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);


    }


    public void task_onButtonShowPopupWindowClick(View view) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.t_popup_window, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.t_btnbfclean:
                Toast.makeText(this, "Button 1 clicked", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, BF_URL, Toast.LENGTH_SHORT).show();
                if (iterator != 1) {
                    flag = 0;
                    URL();
                    String timeStamp0 = new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss'Z'").format(new Timestamp(System.currentTimeMillis()));
                    BF_TIMESTAMP = timeStamp0;
                    Toast.makeText(this, timeStamp0, Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(this, "Cannot be edited", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.t_btnaftclean:
                Toast.makeText(this, "Button 2 clicked", Toast.LENGTH_SHORT).show();
                if (iterator != 1) {
                    flag = 1;
                    URL();
                    String timeStamp1 = new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss'Z'").format(new Timestamp(System.currentTimeMillis()));
                    AF_TIMESTAMP = timeStamp1;
                    Toast.makeText(this, timeStamp1, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Cannot be edited", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.t_btnsubmit:
                Toast.makeText(this, "Button 3 clicked", Toast.LENGTH_SHORT).show();
                if (AF_URL != null && BF_URL != null && iterator == 0) {
                    iterator = 1;
                    Log.d("Fucked Twice34 2", "dfsdcsdds " + iterator);
                    String timeStamp = new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss'Z'").format(new Timestamp(System.currentTimeMillis()));
                    TIMESTAMP = timeStamp;
                    int id1 = getIntent().getIntExtra(EXTRA_ID, -1);
                    Log.d("Fucked Twice", "aDtaivgv " + id1);
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);
                    Data data = new Data(id1, bin_id, person_id, latitude, longitude, iterator, BF_TIMESTAMP, BF_URL, AF_TIMESTAMP, AF_URL, formattedDate);
                    dataViewModel.insert(data);
                    Log.d("Fucked Twice", "Doneupdating in 2nd db " + id1);

                }
                Intent data = new Intent();
                data.putExtra(Task_Individual.EXTRA_BIN_ID, bin_id);
                data.putExtra(Task_Individual.EXTRA_PID, person_id);
                data.putExtra(Task_Individual.EXTRA_LAT, latitude);
                data.putExtra(Task_Individual.EXTRA_LON, longitude);
                data.putExtra(Task_Individual.EXTRA_ITERATOR, iterator);
                data.putExtra(Task_Individual.EXTRA_BF_TIMESTAMP, BF_TIMESTAMP);
                data.putExtra(Task_Individual.EXTRA_BF_URL, BF_URL);
                data.putExtra(Task_Individual.EXTRA_AF_TIMESTAMP, AF_TIMESTAMP);
                data.putExtra(Task_Individual.EXTRA_AF_URL, AF_URL);
                data.putExtra(Task_Individual.EXTRA_TIMESTAMP, TIMESTAMP);
                int id = getIntent().getIntExtra(EXTRA_ID, -1);
                if (id != -1) {
                    data.putExtra(EXTRA_ID, id);
                }
                setResult(RESULT_OK, data);
                finish();
        }
    }

    private void URL() {
        askCameraPermission();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                File f15 = new File(main_url);
                Toast.makeText(this, main_url, Toast.LENGTH_SHORT).show();
                if (flag == 0) {
                    BF_URL = main_url;
                    before.setImageURI(Uri.fromFile(f15));
                }
                if (flag == 1) {
                    AF_URL = main_url;
                    after.setImageURI(Uri.fromFile(f15));
                }
                Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(f15));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f15);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
            }
        }
    }


    public void askCameraPermission() {
        Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PER_CODE);

        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PER_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        // File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        main_url = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Sorry cannot create Image File", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }

    }
}
