package com.example.socalbeach4life;
import java.io.Serializable;

public class ParkingLotModel implements Serializable {
    public String name;
    public String coord;
    public String address;
    public Double lat;
    public Double lon;
    public Double distance;

    public String getName() {
        return name;
    }

    public String getCoord() {
        return coord;
    }

    public Double getDistance() {
        return distance;
    }

    public Double getLat() { return lat; }

    public Double getLon() { return lon; }

    public ParkingLotModel(String name, String address, Double distance, Double lat, Double lon) {
        this.name = name;
        this.address = address;
        // this.coord = coord;
        this.distance = distance;
        this.lat = lat;
        this.lon = lon;
    }

    public ParkingLotModel() {
        this.name = "error";
        this.coord = "error";
        this.address = "error";
        this.distance = 0.0;
        this.lat = 0.0;
        this.lon = 0.0;
    }
}
