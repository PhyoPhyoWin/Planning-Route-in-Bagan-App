package com.bagan.test.bagan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class NearestRestaurant extends AppCompatActivity {
   double latitude,longitude=0.0;
    double current_latitude=21.1905616;
    double current_longitude=94.8936555;
    double compare_dist[];
    double totalDistance;
    String[]restName;
    double[]latlong_lat;
    double[]latlong_lon;
    double [][]distance;
    double min=Double.MAX_VALUE;
    double min_distance[];
    double[]last_rest=new double[5];
    String[]near;
    List<String>nearest_rest;
    TextView nearestfinal;
    Button restmap;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearest_rest);
        nearestfinal=(TextView)findViewById(R.id.show);
        restmap=(Button)findViewById(R.id.restmap);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        Intent in=getIntent();
        latitude =in.getDoubleExtra("latitude",0.0);
        longitude=in.getDoubleExtra("longitude",0.0);
        //Toast.makeText(NearestRestaurant.this, "Latlong"+latitude+","+longitude, Toast.LENGTH_SHORT).show();
        List<String> rest = databaseAccess.getRest();
        restName=rest.toArray(new String[rest.size()]);
        latlong_lon = databaseAccess.getLongRest();
        latlong_lat = databaseAccess.getLatRest();
        min_distance=new double[restName.length];
        compare_dist=new double[restName.length];
        for (int i = 0; i < restName.length; i++) {
            min_distance[i] = getDistance(current_latitude, current_longitude, latlong_lat[i], latlong_lon[i]);
         //  Toast.makeText(NearestRestaurant.this,"minimum"+min_distance[i]+"rest"+restName[i],Toast.LENGTH_LONG).show();

        }
        for (int i = 0; i < restName.length; i++) {
            compare_dist[i] = getDistance(current_latitude, current_longitude, latlong_lat[i], latlong_lon[i]);

        }
      //  Toast.makeText(NearestRestaurant.this,"Unsorted"+compare_dist.length,Toast.LENGTH_LONG).show();


        Arrays.sort(min_distance);
        for (int i = 0; i < min_distance.length; i++) {
        }
      //  Toast.makeText(NearestRestaurant.this, "Sorted Restaurants" + min_distance.length, Toast.LENGTH_LONG).show();


        for(int i=0;i<restName.length;i++){
          //  Toast.makeText(NearestRestaurant.this,"Restaurants"+restName[i],Toast.LENGTH_LONG).show();

        }
        for(int i=0;i<5;i++){
            last_rest[i]=min_distance[i];
            // Toast.makeText(NearestRestaurant.this,"Minimum rest"+last_rest[i],Toast.LENGTH_LONG).show();
        }
        int[]visited=new int[compare_dist.length];
        final String[]nearest_show=new String[last_rest.length];
        for(int i=0;i<last_rest.length;i++){
            for(int j=0;j<compare_dist.length;j++){
                if(last_rest[i]==compare_dist[j]&&visited[j]==0){
                    nearest_show[i]=restName[j];
                    visited[j]=1;
                    break;
                   // Toast.makeText(NearestRestaurant.this,"Restaurants"+nearest_show[i],Toast.LENGTH_LONG).show();
                }
            }

        }
        String result="      ";
        for(int k=0;k<5;k++){
            result+=nearest_show[k]+" Restaurant";
            result+="\n";
            result+="       ";
        }
        nearestfinal.setText("\n   \n \n    Nearest Restaurants are:\n "+result);
        restmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(NearestRestaurant.this, MapsActivity.class);
                intent1.putExtra("Mappp",nearest_show);
                startActivity(intent1);
                finish();
            }
        });
    }


    //get the latitude longitude distance
    private double getDistance(double lat_One, double lon_One,double lat_Two, double lon_Two) {


        int R = 6371; // Radius of the earth in km

        double dLat = deg2rad(lat_Two-lat_One);  // deg2rad below


        double dLon = deg2rad(lon_Two-lon_One);

        double a =

                Math.sin(dLat/2) * Math.sin(dLat/2) +

                        Math.cos(deg2rad(lat_One)) * Math.cos(deg2rad(lat_Two)) *

                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;



        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));



        double d = R * c; // Distance in km
        d=round(d*0.621371192,2);//in mile
        return d;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    private double deg2rad(double d) {

        return d * (Math.PI/180);

    }


}



