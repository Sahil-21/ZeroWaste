package com.example.zerowaste;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zerowaste.model.User;
import com.example.zerowaste.model.databaseHelper;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_up extends AppCompatActivity {
    TextView t2;
    Button create,google,fb;
    private EditText edname,edemail,edpassword;
    databaseHelper db;
    String name,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
        t2 = findViewById(R.id.signintologin);

        edname = findViewById(R.id.edname);
        edemail = findViewById(R.id.edemailsign);
        edpassword = findViewById(R.id.edpasswordsign);
        db = new databaseHelper(this);
        create = findViewById(R.id.create_acc_button);


        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Sign_up.this, Login.class));
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               create_user() ;
            }
        });


    }

    public void create_user() {
        email = edemail.getText().toString().trim();
        password = edpassword.getText().toString().trim();
        name = edname.getText().toString().trim();

        if(name.isEmpty()){
            edname.setError("Name Required");
            edname.requestFocus();
            return;
        }
        else if(password.isEmpty()){
            edpassword.setError("Password Required");
            edpassword.requestFocus();
            return;
        }
        else if(email.isEmpty()){
            edemail.setError("Email Required");
            edemail.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edemail.setError("Please Provide Valid Email");
            edemail.requestFocus();
            return;
        }
        else if(password.length() < 6){
            edpassword.setError("Password Greater than 6");
            edpassword.requestFocus();
            return;
        }
        else{
            long val = db.addUser(name,email,password);
            if((val>0)&& db.checkUser(email)){
                Toast.makeText(this,"Registration Sucessful",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Sign_up.this, Login.class));
                finish();
            }else{
                Toast.makeText(this,"Registration Error",Toast.LENGTH_SHORT).show();

            }

        }

    }
}