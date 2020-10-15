package com.example.zerowaste;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.zerowaste.model.User;
import com.example.zerowaste.model.databaseHelper;

import static com.example.zerowaste.Login.EMAIL;
import static com.example.zerowaste.Login.SHARED_PREFS;

public class Profile_Change_Password extends AppCompatActivity {
    databaseHelper db;
    String curr_name,curr_profuri,curr_bio,curr_email,curr_phone,curr_loc,curr_password;
    EditText curpass,newpass,connewpass;
    User curr_user;
    Button buttonsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_pass);
        db = new databaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar_edit_password);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");

        buttonsv=findViewById(R.id.buttonsv);
        curpass = findViewById(R.id.pass_curr1);
        newpass = findViewById(R.id.pass_new1);
        connewpass = findViewById(R.id.conf);
        loadData();

        curr_user = new User();
        curr_user= db.getUser(curr_email);
        curr_name = curr_user.getName();
        curr_password = curr_user.getPassword();
        curr_bio = curr_user.getBio();
        curr_profuri = curr_user.getProf_uri();
        curr_phone = curr_user.getPhone();
        curr_loc = curr_user.getLoc();

        buttonsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
            }
        });

    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        curr_email = sharedPreferences.getString(EMAIL,"null");
        Toast.makeText(this, curr_email, Toast.LENGTH_SHORT).show();

    }

    public void onSave(){
        String curr,newp,confp;
        curr=curpass.getText().toString().trim();
        newp=newpass.getText().toString().trim();
        confp=connewpass.getText().toString().trim();
        Log.d("FUCK Password change", "entered");
        if(curr.isEmpty() && (curpass.length() < 6)){
            curpass.setError("Password Required");
            curpass.requestFocus();
        }else if(newp.isEmpty() && (newp.length() < 6)){
            newpass.setError("Password Required");
            newpass.requestFocus();
        }else if(confp.isEmpty() && (confp.length() < 6)){
            connewpass.setError("Password Required");
            connewpass.requestFocus();
        }else if(!newp.equals(confp)){
            Toast.makeText(this,"New Password does not match",Toast.LENGTH_SHORT).show();
        }else{
            if(curr.equals(curr_password)){
                try {
                    curr_user.setPassword(newp);
                    db.updateUser(curr_user);
                    Toast.makeText(this,"Sucessful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Profile_Edit.class));
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("FUCK Password change", e.toString());

                }
            }
            else{
                Toast.makeText(this,"Password change Unsucessful",Toast.LENGTH_SHORT).show();

            }

        }

    }


}
