package com.example.zerowaste;
import com.example.zerowaste.Login;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.zerowaste.Login.EMAIL;
import static com.example.zerowaste.Login.ISLOGGEDIN;
import static com.example.zerowaste.Login.SHARED_PREFS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class Settings extends AppCompatActivity implements View.OnClickListener{

    CardView settings,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Toolbar toolbar = findViewById(R.id.setting_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");

        settings = findViewById(R.id.cd_settings);
        logout = findViewById(R.id.cd_logout);
        settings.setOnClickListener(this);
        logout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cd_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Profile_Edit.class));
                break;
            case R.id.cd_logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(ISLOGGEDIN, "false");
                editor.putString(EMAIL,"email");
                editor.commit();
                startActivity(new Intent(this, Login.class));
                break;
        }
    }

}
