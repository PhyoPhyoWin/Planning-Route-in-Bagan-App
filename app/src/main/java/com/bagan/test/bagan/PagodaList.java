package com.bagan.test.bagan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PagodaList extends AppCompatActivity {

    MyCustomAdapter dataAdapter;
    double latitude,longitude=0.0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearest_pagoda);
        Intent i=getIntent();
        latitude =i.getDoubleExtra("latitude",0.0);
        longitude=i.getDoubleExtra("longitude",0.0);
        //Generate list View from ArrayList
        displayListView();

        checkButtonClick();

    }

    private void displayListView() {

        //Array list of countries
        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        ArrayList<Pagoda> pagodaList = databaseAccess.getPagoda();
        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.pagoda_info, pagodaList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Pagoda country = (Pagoda) parent.getItemAtPosition(position);
               /* Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + country.getPagodaName(),
                        Toast.LENGTH_LONG).show();*/
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<Pagoda> {

        private ArrayList<Pagoda> pagodaList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Pagoda> pagodaList) {
            super(context, textViewResourceId, pagodaList);
            this.pagodaList = new ArrayList<Pagoda>();
            this.pagodaList.addAll(pagodaList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.pagoda_info, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Pagoda pagoda= (Pagoda) cb.getTag();
                       /* Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();*/
                        pagoda.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Pagoda pagoda = pagodaList.get(position);
            holder.code.setText(" (" +  pagoda.getName() + ")");
            holder.name.setText(pagoda.getPagodaName());
            holder.name.setChecked(pagoda.isSelected());
            holder.name.setTag(pagoda);

            return convertView;

        }

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                // responseText.append("The following were selected...\n");

                ArrayList<Pagoda> pagodaList = dataAdapter.pagodaList;
                List<String> data = new ArrayList<String>();
                for (int i = 0; i < pagodaList.size(); i++) {
                    Pagoda pagoda = pagodaList.get(i);
                    if (pagoda.isSelected()) {
                        //responseText.append("\n" + pagoda.getPagodaName());
                        data.add(pagoda.getPagodaName().toString());

                    }


                }
                if ( data.size() > 10 ) {
                    new AlertDialog.Builder(PagodaList.this)
                            .setTitle("Choose Pagoda")
                            .setMessage("Dear User " + "\nYou cannot choose more than 10")
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }

                            }).show();
                }
                else if ( data.size() ==0 ) {
                    new AlertDialog.Builder(PagodaList.this)
                            .setTitle("Choose Pagoda")
                            .setMessage("Dear User " + "\nYou must choose Pagodas correctly")
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }

                            }).show();
                }
                else if(data.size()==1){
                    String tsp=data.get(0);
                    Intent intent = new Intent(getApplicationContext(), MapsActivity4.class);
                    intent.putExtra("pagodaName", tsp);
                    intent.putExtra("latitude",latitude);
                    intent.putExtra("longitude",longitude);
                    startActivity(intent);

                }
                else{
                    String[] tsp = new String[data.size()];
                    data.toArray(tsp);
                    Intent intent = new Intent(getApplicationContext(), TSP.class);
                    intent.putExtra("pagodaName", tsp);
                    intent.putExtra("latitude",latitude);
                    intent.putExtra("longitude",longitude);
                    startActivity(intent);
                }
            }

               /* Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();*/


        });

    }

}
