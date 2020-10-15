package com.example.zerowaste;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zerowaste.note.Note;
import com.example.zerowaste.note.NoteViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    public String BF_TIMESTAMP, BF_URL, AF_TIMESTAMP, AF_URL, TIMESTAMP;
    double latitude, longitude;
    public int bin_id, iterator, person_id, ids;


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
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


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


        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    LatLng latLng = new LatLng(lat,lon);

                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(lat,lon,1);
                    String str = addressList.get(0).getLocality()+",";
                    str += addressList.get(0).getCountryName();
                    mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng ,10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
        else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();
                    LatLng latLng = new LatLng(lat,lon);

                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(lat,lon,1);
                        String str = addressList.get(0).getLocality()+",";
                        str += addressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng ,10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng bin = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(bin).title("Bin Dd "+bin_id));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bin ,10.2f));
    }
}