package com.bagan.test.bagan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int MY_REQUEST_INT =177 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

            // Here we want to check the permission of Location  GPS

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //Here we put code if permission is not granted!!
                //we need to let the user allowing the permission ....
                //After making android 6 (marshmallow version)
                //android make the user to granted the permission during
                //the runtime   so when the app is running permission
                //check will appear to user;;;

                // Android in this way increases the security of systems
                //apps and user privacy

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},MY_REQUEST_INT);
                }
                return;
            }
            else {

                // here the code of granted permission
                mMap.setMyLocationEnabled(true);

                //for more
            }



            DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        Intent intent=getIntent();

        String[]data=intent.getStringArrayExtra("Mappp");
        for(int i=0;i<data.length;i++){
           // Toast.makeText(MapsActivity.this,"Data"+data[i],Toast.LENGTH_LONG).show();
        }

        for(int i=0;i<data.length;i++){
            double[]latlong=databaseAccess.getLatLongRest(data[i]);
            LatLng bagan = new LatLng(latlong[0], latlong[1]);
            mMap.addMarker(new MarkerOptions().position(bagan).title(data[i]));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(bagan));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        }


    }
}
