package com.bagan.test.bagan;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static java.lang.Math.floor;

public class TSP extends AppCompatActivity {
    String[]tsp=null;
    TextView shortest_route,shortest_distance,total_time_ebike,total_time_bicycle;
    int[]street;
    double ebike,bicycle=0.0;
    String street_bagan=" ";
    String output_route[];
    double current_latitude=21.1905616;
    double current_longitude=94.8936555;
    double totalDistance;
    TSPRoute tspRoute=new TSPRoute();
    double latitude,longitude=0.0;
    Button view_pg;

    String[]pagoda_name;
    double [][]distance;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tsp_route);
        view_pg=(Button)findViewById(R.id.view_route);
        shortest_distance=(TextView)findViewById(R.id.shortest_distance);
        shortest_route=(TextView)findViewById(R.id.shortest_route);
        total_time_ebike=(TextView)findViewById(R.id.total_time_ebike);
        total_time_bicycle=(TextView)findViewById(R.id.total_time_bicycle);
        Intent intent=getIntent();
        latitude =intent.getDoubleExtra("latitude",0.0);
        longitude=intent.getDoubleExtra("longitude",0.0);
        tsp=intent.getStringArrayExtra("pagodaName");//get the choosing pagodas
        int size=tsp.length;
        String[]places=new String[size+1];
        int l=1;
        places[0]="Current Place";

        for(int i=0;i<tsp.length;i++){

            places[l]=tsp[i];
            l++;

        }

        int k=0;
        distance=new double[places.length][places.length];//two dimensional array
        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        for(int i=0;i<places.length;i++){
            for(int j=0;j<places.length;j++){
                if(i==j){
                    distance[i][j]=0;
                    continue;
                }
                else if(places[i].equals("Current Place")){
                    double[]latlong=databaseAccess.getLatLong(places[j]);
                    distance[i][j]=getDistance(current_latitude,current_longitude,latlong[0],latlong[1]);

                }
                else if(!(places[i].equals("Current Place"))) {
                    double[] data = databaseAccess.getLatLong(places[i]);
                    if (places[j].equals("Current Place")) {
                        distance[i][j] = getDistance(data[0], data[1], current_latitude, current_longitude);

                    } else if (!(places[i].equals("Current Place"))) {
                        double[] latlon = databaseAccess.getLatLong(places[j]);
                        distance[i][j] = getDistance(data[0], data[1], latlon[0], latlon[1]);

                    }
                }
            }
        }

        List<Integer> nearest_route=tspRoute.shortestRoute(distance);
        output_route=new String[nearest_route.size()];
        street=new int[nearest_route.size()];
        for(int i=0;i<nearest_route.size();i++){
            street[i]=nearest_route.get(i);
            output_route[i]=places[street[i]];
            if(i==nearest_route.size()-1){
                street_bagan+="  "+output_route[i]+"\n\n ";
            }
            else {
                street_bagan += output_route[i] + "--> ";
            }
         //   Toast.makeText(TSP.this,"Map"+street[i],Toast.LENGTH_LONG).show();

        }

            for(int i=0;i<street.length-1;i++) {
                output_route[i] = places[street[i]];
                int q = street[i];
                int p = street[i + 1];
                String nep = places[street[i + 1]];
                double distance_final = distance[q][p];
                double hours = round(distance_final / 25, 0);
                if(size<=5){

                street_bagan += " From " + output_route[i] + " to " + nep + " --" + "\n   Distance" + "(" +
                        distance_final + " ) " + "miles" + "\n";
                street_bagan += "  ";
               }
               else {
                    street_bagan += " From " + output_route[i] + " to " + nep + " " + "\n";

                    street_bagan += "  ";
                }



                }



        pagoda_name=new String[output_route.length-2];
        for(int i=0;i<pagoda_name.length;i++){
            pagoda_name[i]=output_route[i+1];
            // Toast.makeText(TSP.this,"Map"+pagoda_name[i],Toast.LENGTH_LONG).show();
        }
        double totalDistance=tspRoute.getTotalDistance();
        totalDistance=round(totalDistance,2);
        String total_distance=String.valueOf(totalDistance);
        shortest_route.setText("The route is\n"+" "+street_bagan);
        shortest_distance.setText("The total distance is"+" "+total_distance+"miles");

        ebike=round((totalDistance/25),2);
       // Toast.makeText(TSP.this,"Hour"+ebike,Toast.LENGTH_LONG).show();

        double ebike_minute=round(ebike*60,2);

        String eb=String.valueOf(ebike_minute);
        bicycle=round((totalDistance/9.6),2);
        double bicycle_minute=round(bicycle*60,2);
        String bc=String.valueOf(bicycle_minute);


        if(ebike>1){
            double hour=floor(ebike);
            double mm=round((ebike-hour),2);
            double minute=round((mm*60),0);

            total_time_ebike.setText("Total time of Ebike is "+hour+" hour "+minute+" minutes");

        }
        else {
            total_time_ebike.setText("Total time of Ebike is " + eb + " minutes");
        }
        if(bicycle>1){
            double hour=floor(bicycle);
            double mm=round((bicycle-hour),2);
            double minute=round((mm*60),0);

            total_time_bicycle.setText("Total time of Bicycle is "+hour+" hour "+minute+" minutes\n");

        }
        else {
            total_time_bicycle.setText("Total time of Bicycle is " + bc + "  minutes");
        }
        view_pg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(TSP.this, MapsActivity2.class);
                intent1.putExtra("Pagoda",pagoda_name);
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



