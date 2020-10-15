package com.example.zerowaste;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zerowaste.model.databaseHelper;


public class Login extends AppCompatActivity {
    EditText email, password;
    String eml, pwd;
    databaseHelper db;
    Button login, google_login, facebook_login;
    String log;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String ISLOGGEDIN = "false";
    public static final String EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        log =loadData();
        if(log.equals("true")){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }


        db = new databaseHelper(this);
        email = findViewById(R.id.EmailAddress);
        password = findViewById(R.id.Password2);
        login = findViewById(R.id.login_button1);
        google_login = findViewById(R.id.google_login);
        facebook_login = findViewById(R.id.facebook_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });


    }

    private String loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String isloggedin = sharedPreferences.getString(ISLOGGEDIN,"false");
        return isloggedin;
    }

    public void Check(){
        eml = email.getText().toString().trim();
        pwd = password.getText().toString().trim();
        verifyFromSQLite();
    }

    private void verifyFromSQLite() {
        if(eml.isEmpty()){
            email.setError("Email Required");
            email.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(eml).matches()){
            email.setError("Please Provide Valid Email");
            email.requestFocus();
            return;
        }
        else if(pwd.isEmpty()){
            password.setError("Password Required");
            password.requestFocus();
            return;
        }
        else{
            Boolean res = db.checkUser(eml,pwd);
            if (res == true){
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(ISLOGGEDIN, "true");
                editor.putString(EMAIL,eml);
                editor.commit();
                Toast.makeText(this,"Sucessful",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();

            }else{
                Toast.makeText(this,"Unsucessful",Toast.LENGTH_SHORT).show();

            }
        }
        
    }


    public void Open_SignUp(View view){
        startActivity(new Intent(this, Sign_up.class));
    }
}