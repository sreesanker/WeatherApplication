package com.example.asus.weatherapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.weatherapplication.Model.OpenweatherMap;
import com.example.asus.weatherapplication.common.Common;
import com.example.asus.weatherapplication.helper.Helper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;


/*step 1: add two lib dependency
          com.google.code.gson:gson
          com.squareup.picasso:picasso*/
//step 2: start a new package -  common wiith a public class Common
public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView textLastUpdates, textCity, textHumidity, textDescription, textTime, textCelisius;
    ImageView imageView;

    LocationManager locationManager;
    static double lat, lng;
    String provider;
    OpenweatherMap openweatherMap = new OpenweatherMap();
    int MY_PERMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLastUpdates = (TextView) findViewById(R.id.textLastupdates);
        textCelisius = (TextView) findViewById(R.id.textCelsius);
        textCity = (TextView) findViewById(R.id.textCity);
        textDescription = (TextView) findViewById(R.id.textDescription);
        textHumidity = (TextView) findViewById(R.id.textHumidity);
        textCity = (TextView) findViewById(R.id.textCity);
        textTime = (TextView) findViewById(R.id.textTime);
        imageView = (ImageView) findViewById(R.id.imageView);

        //step after all the classes
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null) {
            Log.e("Tag", "NO Location");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSION);
        }
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },MY_PERMISSION);
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lng = location.getLongitude();

            new GetWeather().execute(Common.apiRequest(String.valueOf(lat),String.valueOf(lng)));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class GetWeather extends AsyncTask<String,Void,String>{
        ProgressDialog pd = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setTitle("please wait....");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String stream = null;
            String urlString = params[0];
            Helper http = new Helper();
            stream = http.getHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.contains("Error: Not found city")){
                pd.dismiss();
                return;
            }

            Gson gson = new Gson();
            Type mtype = new TypeToken<OpenweatherMap>(){}.getType();
            openweatherMap = gson.fromJson(s,mtype);
            pd.dismiss();

            textCity.setText(String.format("%s %s",openweatherMap.getName(),openweatherMap.getSys().getCountry()));
            textLastUpdates.setText(String.format("Last Updates: %s", Common.getDatenow()));
            textDescription.setText(String.format("%s",openweatherMap.getWeather().get(0).getDescription()));
            textHumidity.setText(String.format("%d%%",openweatherMap.getMain().getHumidity()));
            textTime.setText(String.format("%s/%s",Common.unixTimeStampToDateTime(openweatherMap.getSys().getSunrise()),Common.unixTimeStampToDateTime(openweatherMap.getSys().getSunset())));
            textCelisius.setText(String.format("%2f °C",openweatherMap.getMain().getTemp()));
            Picasso.with(MainActivity.this)
                    .load(Common.getImage(openweatherMap.getWeather().get(0).getIcon()))
                    .into(imageView);

        }
    }
}
