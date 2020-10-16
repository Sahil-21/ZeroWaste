package com.example.zerowaste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.zerowaste.historicdata.data_main;
import com.example.zerowaste.model.User;
import com.example.zerowaste.model.databaseHelper;
import com.example.zerowaste.note.*;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.zerowaste.Login.EMAIL;
import static com.example.zerowaste.Login.SHARED_PREFS;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String FILE_NAME = "dato.txt";
    String ts1, ts2;
    private DrawerLayout drawer;
    NavigationView navigationView;
    private NoteViewModel noteViewModel;
    User curr_user;
    databaseHelper db;
    String curr_name, curr_profuri, curr_bio, curr_email, curr_phone, curr_loc;
    ImageView navhead_pp1;
    TextView navhead_name1, navheademail1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        navhead_name1 = (TextView) headerView.findViewById(R.id.tv_namenv);
        navheademail1 = (TextView) headerView.findViewById(R.id.tv_emnv);
        navhead_pp1 = (ImageView) headerView.findViewById(R.id.im_ppnv);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


        db = new databaseHelper(this);
        loadData();
        curr_user = new User();
        curr_user = db.getUser(curr_email);
        curr_name = curr_user.getName();
        curr_bio = curr_user.getBio();
        curr_profuri = curr_user.getProf_uri();
        curr_phone = curr_user.getPhone();
        curr_loc = curr_user.getLoc();
        navhead_name1.setText(curr_name);
        set_values1();


        FileInputStream fis = null;
        ts1 = "0";
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
            ts1 = sb.toString();
            if (ts1 == null) {
                ts1 = "08";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd", Locale.getDefault());
        //  ts2 = String.valueOf(df.format(c));
        ts2 = "11";
        Toast.makeText(this, ts1, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, ts2, Toast.LENGTH_SHORT).show();
        if (Integer.valueOf(ts1) != Integer.valueOf(ts2)) {
            Toast.makeText(this, ts1, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ts2, Toast.LENGTH_SHORT).show();
            noteViewModel.deleteAllNotes();
      //      Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
            FileOutputStream fos = null;

            try {
                fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                fos.write(ts2.getBytes());
        //        Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    private void set_values1() {

        navhead_name1.setText(curr_name);
        navheademail1.setText(curr_email);
        if (curr_profuri.equals("null1")) {
            navhead_pp1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.tqt));
        } else {
            File f = new File(curr_profuri);
            navhead_pp1.setImageURI(Uri.fromFile(f));
        }


    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        curr_email = sharedPreferences.getString(EMAIL, "null");
        Toast.makeText(this, curr_email, Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_profile:
                startActivity(new Intent(getApplicationContext(), Profile.class));
                navigationView.setCheckedItem(R.id.nav_home);
                break;
            case R.id.nav_task:
                startActivity(new Intent(getApplicationContext(), note_main.class));
                navigationView.setCheckedItem(R.id.nav_home);
                break;
            case R.id.nav_message:
                startActivity(new Intent(getApplicationContext(), MessageFragment.class));
                navigationView.setCheckedItem(R.id.nav_home);
                break;

            case R.id.nav_tracking:
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                navigationView.setCheckedItem(R.id.nav_home);
                break;

            case R.id.nav_archive:
                startActivity(new Intent(getApplicationContext(), data_main.class));
                navigationView.setCheckedItem(R.id.nav_home);
                break;
            case R.id.nav_setting:
                startActivity(new Intent(getApplicationContext(), Settings.class));
                navigationView.setCheckedItem(R.id.nav_home);
                break;
            case R.id.nav_about:
                startActivity(new Intent(getApplicationContext(), About.class));
                navigationView.setCheckedItem(R.id.nav_home);
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}