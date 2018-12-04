package com.example.asus.weatherapplication.Model;

public class Coord {
    //step 15: look postman for variables
    private double lat;
    private double lon;

    //step 16: generate constructor(alt+insert)

    public Coord(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
    //step 17: generate getter and setter
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
    //step 18: create a new class weather(postman)
}
