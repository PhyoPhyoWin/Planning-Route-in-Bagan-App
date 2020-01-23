package com.bagan.test.bagan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    Cursor cursor;
    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
   /* public List<String> getQuotes() {
        List<String> list = new ArrayList<String>();
        Cursor cursor = database.rawQuery("SELECT * FROM persons", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }*/
    public List<String> getHotelName(){
        List<String> list=new ArrayList<String>();
        cursor=database.rawQuery("select hotelName from Hotel",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                list.add(cursor.getString(0));

                cursor.moveToNext();

            }

        }

        return list;
    }

    public List<String> getName(){
        List<String> list = new ArrayList<String>();
        cursor=database.rawQuery("select pagodaName from Pagodas",null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                list.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }


        return list;

    }
   /* public List<String> getTemple() {
        List<String> list = new ArrayList<String>();
        cursor = database.rawQuery("select name from Pagodas", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }


        return list;
    }*/
    public List<String> getHotelPhone() {
        List<String> list = new ArrayList<String>();
        cursor = database.rawQuery("select phone from Hotel", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }


        return list;
    }
    public List<String> getEbikePhone() {
        List<String> list = new ArrayList<String>();
        cursor = database.rawQuery("select phone from Ebike", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }


        return list;
    }

    public ArrayList<Pagoda>getPagoda(){
        ArrayList<Pagoda>list=new ArrayList<Pagoda>();
        cursor=database.rawQuery("select name,pagodaName,selected from Pagodas",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            boolean flag=Boolean.parseBoolean(cursor.getString(2));
            while(!cursor.isAfterLast()){
                list.add(new Pagoda(cursor.getString(0),cursor.getString(1),flag));

                cursor.moveToNext();

        }

    }

        return list;
    }
    public List<String> getRest(){
        List<String>list=new ArrayList<String>();
        cursor=database.rawQuery("select restaurantName from Restaurants",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                list.add(cursor.getString(0));

                cursor.moveToNext();

            }

        }

        return list;
    }
    public double[] getLatLong(String place){
        double lat,lon;
        double[]latlon= new double[2];
        cursor=database.rawQuery("select latitude,longitude from Pagodas where pagodaName='"+place+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            lat=cursor.getDouble(0);
            lon=cursor.getDouble(1);

            latlon[0]=lat;
            latlon[1]=lon;
            cursor.moveToNext();
        }

        return latlon;
    }
    public double[] getHotelLatLong(String place){
        double lat,lon;
        double[]latlon= new double[2];
        cursor=database.rawQuery("select latitude,longitude from Hotel where hotelName='"+place+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            lat=cursor.getDouble(0);
            lon=cursor.getDouble(1);

            latlon[0]=lat;
            latlon[1]=lon;
            cursor.moveToNext();
        }

        return latlon;
    }

    public double[] getLatLongRest(String place){
        double lat,lon;
        double[]latlon= new double[2];
        cursor=database.rawQuery("select latitude,longitude from Restaurants where restaurantName='"+place+"'",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            lat=cursor.getDouble(0);
            lon=cursor.getDouble(1);

            latlon[0]=lat;
            latlon[1]=lon;
            cursor.moveToNext();
        }

        return latlon;
    }
    public double[] getLatRest(){
        double lat,lon;
        double[]latlon= new double[34];
        int i=0;
        cursor=database.rawQuery("select latitude from Restaurants",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            lat=cursor.getDouble(0);

            latlon[i]=lat;
            cursor.moveToNext();
            i++;
        }

        return latlon;
    }
    public double[] getLongRest(){
        double lon;
        double[]latlon= new double[34];
        int i=0;
        cursor=database.rawQuery("select longitude from Restaurants",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            lon=cursor.getDouble(0);


            latlon[i]=lon;
            cursor.moveToNext();
            i++;
        }

        return latlon;
    }


    public List<String> getEbike(){
        List<String>list=new ArrayList<String>();
        cursor=database.rawQuery("select shopName from Ebike",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                list.add(cursor.getString(0));

                cursor.moveToNext();

            }

        }

        return list;
    }

}







