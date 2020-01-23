package com.bagan.test.bagan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class Home extends AppCompatActivity  {
    Button viewPagoda,choosePagoda,viewRest,findRest;
    double latitude,longitude=0.0;
    ImageView viewPagoda_img,ebike_img,viewRest_img,findRest_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Intent i=getIntent();
        latitude =i.getDoubleExtra("latitude",0.0);
        longitude=i.getDoubleExtra("longitude",0.0);
        viewPagoda_img=(ImageView)findViewById(R.id.choosepagoda_image);
        ebike_img=(ImageView)findViewById(R.id.ebike_list);
        viewRest_img=(ImageView)findViewById(R.id.rest_img);
        findRest_img=(ImageView)findViewById(R.id.nearest_rest);
        viewPagoda=(Button)findViewById(R.id.pagodaList);
        choosePagoda=(Button)findViewById(R.id.choosePagoda);
        viewRest=(Button)findViewById(R.id.restList);
        findRest=(Button)findViewById(R.id.findrest);
        viewPagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getApplicationContext(),Rental.class);
                in.putExtra("latitude",latitude);
                in.putExtra("longitude",longitude);
                startActivity(in);

            }
        });
        choosePagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PagodaList.class);
                i.putExtra("latitude",latitude);
                i.putExtra("longitude",longitude);
                startActivity(i);
            }
        });
        viewRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(getApplicationContext(),Hotel.class);
                startActivity(inte);
            }
        });
        findRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NearestRestaurant.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                startActivity(intent);

            }
        });

        viewPagoda_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),PagodaList.class);
                i.putExtra("latitude",latitude);
                i.putExtra("longitude",longitude);
                startActivity(i);


            }
        });
        ebike_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),Rental.class);
                in.putExtra("latitude",latitude);
                in.putExtra("longitude",longitude);
                startActivity(in);
            }
        });
        viewRest_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte=new Intent(getApplicationContext(),Hotel.class);
                startActivity(inte);
            }
        });
        findRest_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(getApplicationContext(),NearestRestaurant.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                startActivity(intent);

            }
        });
    }

}


