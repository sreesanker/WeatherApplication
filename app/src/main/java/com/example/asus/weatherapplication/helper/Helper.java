package com.example.asus.weatherapplication.helper;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Helper {
    //step 10:
    static String stream = null;
    //step 11:
    public Helper(){

    }
    //step 12:

    public String getHTTPData(String urlString){
        //this functionmakes an request to openweathermaps api and return results
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            if (httpURLConnection.getResponseCode() == 200)//ok = 200
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null)
                {
                    sb.append(line);
                    stream = sb.toString();
                    httpURLConnection.disconnect();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }
    //step 13: goto browser for to copy api call and pass key to postman for json parsing
    //step 14: create a new package Model to make each method in JSON parsing
}
