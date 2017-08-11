package com.example.itrain.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {


        private GoogleMap mMap;
        LocationManager locationManager;
        String provider;
        private EditText location;

        @Override
        protected void onResume() {
            super.onResume();
            if (checkLocationPermission()) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission. ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    //Request location updates:
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,1, this);
                    if (locationManager != null) {
                        Location location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            Log.d("debug", String.valueOf(location.getLatitude()));
                            Log.d("debug", String.valueOf(location.getLongitude()));
                        }
                    }
                }
            }    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_map);


            Button button = (Button)findViewById(R.id.button);
            location = (EditText) findViewById(R.id.location);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MapActivity.this, DateTimeActivity.class);
                    startActivity(intent);
                }
            });
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            provider = locationManager.getBestProvider(new Criteria(), false);

            Log.d("debug","test debug");
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
        }

        public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        Log.d("here", "here");


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission. ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission. ACCESS_FINE_LOCATION)) {
                Log.d("here", "1");
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("permission")
                        .setMessage("permission ")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                Log.d("here", "2");
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission. ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            Log.d("here", "3");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission. ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Log.d("debug", String.valueOf(latitude));
        LatLng latLng = new LatLng(latitude, longitude);
        //intantiate class geocoder
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude,1);
            String str = addressList.get(0).getCountryName();
            str += addressList.get(0).getLocality();

            mMap.addMarker(new MarkerOptions().position(latLng).title(str));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.5f));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("debug", "status changed");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("debug", "status enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("debug", "Provider disabled");

    }
}
