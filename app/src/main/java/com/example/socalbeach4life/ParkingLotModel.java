package com.example.socalbeach4life;
import java.io.Serializable;

public class ParkingLotModel implements Serializable {
    public String name;
    public Double latitude;
    public Double longitude;
    public String address;
    public Double distance;

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public Double getDistance() {
        return distance;
    }


    public ParkingLotModel(String name, String address, Double distance, Double latitude, Double longitude ) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.distance = distance;
    }

    public ParkingLotModel() {
        this.name = "error";
        this.longitude = 0.0;
        this.latitude = 0.0;
        this.address = "error";
        this.distance = 0.0;
    }
}
