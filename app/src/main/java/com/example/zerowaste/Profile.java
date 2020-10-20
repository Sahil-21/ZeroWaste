package com.example.zerowaste;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
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

import de.hdodenhof.circleimageview.CircleImageView;

import com.example.zerowaste.historicdata.data_main;
import com.example.zerowaste.model.User;
import com.example.zerowaste.model.databaseHelper;
import com.example.zerowaste.note.note_add_Frag;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.zerowaste.Login.EMAIL;
import static com.example.zerowaste.Login.SHARED_PREFS;


public class Profile extends AppCompatActivity {
    public static final int CAMERA_PER_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    public static final int RESTORAGE = 105;
    public static final int WESTORAGE = 106;
    CircleImageView circleImageView;
    String currentPhotoPath;
    TextView tv_name,tv_bio,tv_email,tv_phone,tv_loc;
    User curr_user;
    databaseHelper db;
    String curr_name,curr_profuri,curr_bio,curr_email,curr_phone,curr_loc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        circleImageView = findViewById(R.id.profile_pic);
        tv_name= findViewById(R.id.profile_name);
        tv_bio= findViewById(R.id.edtbio);
        tv_email= findViewById(R.id.pp_edtemail);
        tv_phone= findViewById(R.id.pp_phone);
        tv_loc= findViewById(R.id.pp_loc);

        db = new databaseHelper(this);
        loadData();
        curr_user = new User();
        curr_user= db.getUser(curr_email);
        curr_name = curr_user.getName();
        curr_bio = curr_user.getBio();
        curr_profuri = curr_user.getProf_uri();
        curr_phone = curr_user.getPhone();
        curr_loc = curr_user.getLoc();
        set_values();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edtprofile, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_prof:
                startActivity(new Intent(this, Profile_Edit.class));
                finish();
                return true;
            case R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void set_values() {
        Toast.makeText(this, curr_name, Toast.LENGTH_SHORT).show();
        tv_name.setText(curr_name);
        tv_bio.setText(curr_bio);
        tv_email.setText(curr_email);
        tv_phone.setText(curr_phone);
        tv_loc.setText(curr_loc);
        if(curr_profuri.equals("null1")){
            circleImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tqt));
        }
        else{
            File f = new File(curr_profuri);
            circleImageView.setImageURI(Uri.fromFile(f));
        }

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        curr_email = sharedPreferences.getString(EMAIL,"null");
     //   Toast.makeText(this, curr_email, Toast.LENGTH_SHORT).show();

    }

    public void onclick_task_card(View view) {

        startActivity(new Intent(getApplicationContext(), note_add_Frag.class));
    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.profile_popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void askCameraPermission(View view) {
        Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PER_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else {
                Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void OpenGallary(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE){
            if(resultCode  == Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                circleImageView.setImageURI(Uri.fromFile(f));
                curr_profuri = currentPhotoPath;
                updatedatabase();
                Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
            }

        }
        if(requestCode == GALLERY_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp +"."+getFileExt(contentUri);
                Log.d("tag", "onActivityResult: Gallery Image Uri:  " +  imageFileName);
                circleImageView.setImageURI(contentUri);
            }

        }
    }


    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());           // Create an image file name
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);       // File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);         //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Sorry", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private void updatedatabase() {
        curr_user.setProf_uri(curr_profuri);
        db.updateUser(curr_user);
    }

    public void remove_prof(View view){
        curr_profuri = "null1";
        curr_user.setProf_uri(curr_profuri);
        db.updateUser(curr_user);
        circleImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tqt));

    }

}