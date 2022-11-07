package com.example.socalbeach4life;

public class ParkingLotModel {
    public String name;
    public String coord;
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

    public ParkingLotModel(String name, String coord, Double distance) {
        this.name = name;
        this.coord = coord;
        this.distance = distance;
    }

    public ParkingLotModel() {
        this.name = "error";
        this.coord = "error";
        this.distance = 0.0;
    }
}
