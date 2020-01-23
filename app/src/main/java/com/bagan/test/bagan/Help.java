package com.bagan.test.bagan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Help extends AppCompatActivity {
    TextView help;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        help=(TextView)findViewById(R.id.help_text);
        String text="In this application, firstly, you must open the GPS and Internet.\n" +
                "\nSo, please wait a few second to get the current location.\n"+
                "\nAfter getting the current location, you can choose whether pagodas or hotels or nearest restaurants"+
                "or ebike rental lists.\n"+"\nIf you choose the pagods, this app will show the optimal route and total distance.\n"+
                "\nMoreover, this system provides the total time based on using ebike or bicycle.\n\nYou can see the route on a google map.";
        help.setText(text);
    }
}
