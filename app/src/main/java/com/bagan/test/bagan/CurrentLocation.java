package com.bagan.test.bagan;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CurrentLocation extends AppCompatActivity implements LocationListener {

    Button getLocationBtn;
    TextView locationText;
    double latitude,longitude=0.0;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_current);

        getLocationBtn = (Button) findViewById(R.id.getLocationBtn);
        // locationText = (TextView)findViewById(R.id.locationText);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }


        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CurrentLocation.this)
                        .setTitle("Get Location ")
                        .setMessage("Dear User " + "\n Please Wait a few seconds coz getting location depends on Internet")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                getLocation();
                                Intent i=new Intent(CurrentLocation.this,Home.class);
                                i.putExtra("latitude",latitude);
                                i.putExtra("longitude",longitude);
                                startActivity(i);
                            }

                        }).show();
            }
        });
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);

        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        Intent i=new Intent(CurrentLocation.this,Home.class);
        i.putExtra("latitude",latitude);
        i.putExtra("longitude",longitude);
        startActivity(i);
        Toast.makeText(CurrentLocation.this,"Now,ready to use",Toast.LENGTH_LONG).show();
        //locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

    /*    try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
        }catch(Exception e)
        {

        }*/

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(CurrentLocation.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
}

