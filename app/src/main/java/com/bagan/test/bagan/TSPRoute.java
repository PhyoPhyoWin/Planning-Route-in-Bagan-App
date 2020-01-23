package com.bagan.test.bagan;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TSPRoute extends AppCompatActivity{
    double totalDistance=0;
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public List shortestRoute(double[][] inputDistance){

        double  minimalDistance=Float.MAX_VALUE;
        int nextPoint=0;

        int size=inputDistance.length;
        int visited[]=new int[size];
        visited[0]=1;
        int firstPoint=0;
        int actualPoint=firstPoint;

        List<Integer> dist=new ArrayList<Integer>();

        for(int p=0;p<size;p++){
            if(p==size-1){
                totalDistance+= inputDistance[actualPoint][firstPoint];
                dist.add(actualPoint);
                break;
            }
            else{
                for(int q=0;q<size;q++){
                    if (inputDistance[actualPoint][q]>0 && visited[q]==0 && minimalDistance> inputDistance [actualPoint][q]){
                        minimalDistance= inputDistance[actualPoint][q];
                        nextPoint=q;
                    }

                }
                visited[nextPoint]=1;
                dist.add(actualPoint);
                totalDistance+= inputDistance[actualPoint][nextPoint];
                actualPoint=nextPoint;
                minimalDistance=Float.MAX_VALUE;
            }
        }
        dist.add(firstPoint);
        return dist;
    }


    public double getTotalDistance(){
        return totalDistance;
    }

}
