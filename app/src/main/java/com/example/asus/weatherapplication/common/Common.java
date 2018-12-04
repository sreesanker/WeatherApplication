package com.example.asus.weatherapplication.common;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    //step 3:
    public static String API_KEY = "74bd68e34b7708b3bc9efd600d3ebcf8";
    public static String API_LINK = "http://api.openweathermap.org/data/2.5/weather";//step 3 ends

    //step 4:
    @NonNull
    public static String apiRequest(String lat, String lng){
        StringBuilder sb = new StringBuilder(API_LINK);
        sb.append(String.format("?lat=%s&lon=%s&APPID=%s&units=metric",lat,lng,API_KEY));
        return sb.toString();
    }
    /*step 5: goto browser and browse for openweathermap.org
              to get the api key and copy it in API_KEY */

    //step 6:
    public static String unixTimeStampToDateTime(double unixTimeStamp){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long)unixTimeStamp*1000);
        return dateFormat.format(date);
    }

    //step 7:
    public static String getImage(String icon){
        return  String.format("http://openweathermap.org/img/w/%s.png",icon);
    }

    //step 8:
    public static String getDatenow(){
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM YYYY HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
    //step 9: create a new package called helper
}
