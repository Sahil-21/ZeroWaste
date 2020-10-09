package com.example.zerowaste;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(this,MainActivity.class));
            this.finish();

        }

    }
}