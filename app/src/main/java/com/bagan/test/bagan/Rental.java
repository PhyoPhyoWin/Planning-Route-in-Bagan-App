package com.bagan.test.bagan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Rental extends AppCompatActivity {

    int[] images = {R.drawable.eb, R.drawable.eb, R.drawable.eb,
            R.drawable.eb, R.drawable.eb, R.drawable.eb,
            R.drawable.eb, R.drawable.eb, R.drawable.eb,
            R.drawable.eb, R.drawable.eb, R.drawable.eb,
            R.drawable.eb

    };

    String[] version ;
    String[] versionNumber=null;
    ListView lView;

    RentalListView lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ebike_rental);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        List<String> data=databaseAccess.getEbike();
        version=new String[data.size()];
        version= data.toArray(new String[data.size()]);

        List<String>result=databaseAccess.getEbikePhone();
        versionNumber=new String[result.size()];
        versionNumber=result.toArray(new String[result.size()]);

        lView = (ListView) findViewById(R.id.androidList);

        lAdapter = new RentalListView(Rental.this, version, versionNumber, images);

        lView.setAdapter(lAdapter);

      /* lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(Rental.this,MapsActivity4.class);
                intent.putExtra("Rental",version[i]);
                startActivity(intent);


            }
        });*/

    }
}