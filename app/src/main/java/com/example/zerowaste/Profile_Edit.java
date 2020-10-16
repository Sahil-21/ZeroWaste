package com.example.zerowaste;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.zerowaste.model.User;
import com.example.zerowaste.model.databaseHelper;
import static com.example.zerowaste.Login.EMAIL;
import static com.example.zerowaste.Login.SHARED_PREFS;

public class Profile_Edit extends AppCompatActivity {
    databaseHelper db;
    String curr_name,curr_profuri,curr_bio,curr_email,curr_phone,curr_loc,curr_password;
    EditText ed_name,ed_bio,ed_phone,ed_loc;
    TextView passchange,ed_email;
    Button done1;
    User curr_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_details);
        db = new databaseHelper(this);

        ed_name = findViewById(R.id.pd_name);
        ed_bio = findViewById(R.id.pd_edBio);
        ed_phone = findViewById(R.id.pd_edphone);
        ed_email = findViewById(R.id.pd_email);
        ed_loc= findViewById(R.id.pd_loc);

        passchange = findViewById(R.id.pd_pass);
        done1 = findViewById(R.id.profile_edit_save);

        done1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_done();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_edit_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");


        loadData();
        curr_user = new User();
        curr_user= db.getUser(curr_email);
        curr_name = curr_user.getName();
        curr_password = curr_user.getPassword();
        curr_bio = curr_user.getBio();
        curr_loc = curr_user.getLoc();
        curr_profuri = curr_user.getProf_uri();
        curr_phone = curr_user.getPhone();
        set_values();

    }

    private void edit_done() {
        String name, bio, phone,loc;
        name = ed_name.getText().toString().trim();
        bio = ed_bio.getText().toString().trim();
        phone = ed_phone.getText().toString().trim();
        loc = ed_loc.getText().toString().trim();

        if (name.isEmpty()) {
            ed_name.setError("Name Required");
            ed_name.requestFocus();
        } else if (bio.isEmpty()) {
            ed_bio.setError("Bio Required");
            ed_bio.requestFocus();
        } else if (loc.isEmpty()) {
            ed_loc.setError("Bio Required");
            ed_loc.requestFocus();
        } else if (phone.isEmpty()) {
            ed_phone.setError("Bio Required");
            ed_phone.requestFocus();
        } else {
            curr_user.setName(name);
            curr_user.setBio(bio);
            curr_user.setPhone(phone);
            curr_user.setLoc(loc);
            try {
                db.updateUser(curr_user);
                Toast.makeText(this, "Sucessful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Profile.class));
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("FUCK Password change", e.toString());
            }
        }
    }


    public void change_password(View view){
        startActivity(new Intent(this, Profile_Change_Password.class));
    }


    private void set_values() {
        Toast.makeText(this, curr_name, Toast.LENGTH_SHORT).show();
        ed_name.setText(curr_name);
        ed_bio.setText(curr_bio);
        ed_email.setText(curr_email);
        ed_phone.setText(curr_phone);
        ed_loc.setText(curr_loc);

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        curr_email = sharedPreferences.getString(EMAIL,"null");
        Toast.makeText(this, curr_email, Toast.LENGTH_SHORT).show();

    }
}
