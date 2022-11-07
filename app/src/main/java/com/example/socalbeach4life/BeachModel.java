package com.example.socalbeach4life;

import java.util.ArrayList;

public class BeachModel {
    String name;
    String coord;
    ArrayList<ParkingLotModel> parkingLots;
    ArrayList<ReviewModel> reviews;

    public BeachModel(String name, String coord, ArrayList<ParkingLotModel> parkingLots, ArrayList<ReviewModel> reviews) {
        this.name = name;
        this.coord = coord;
        this.parkingLots = parkingLots;
        this.reviews = reviews;
    }

    public BeachModel() {
        this.name = "errorbeach";
        this.coord = "0.0";
        this.parkingLots = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getCoord() {
        return coord;
    }

    public ArrayList<ParkingLotModel> getParkingLots() {
        return parkingLots;
    }

    public ArrayList<ReviewModel> getReviews() {
        return reviews;
    }

    @Override
    public String toString() {
        return "BeachModel{" +
                "name='" + name + '\'' +
                ", coord='" + coord + '\'' +
                ", parkingLots=" + parkingLots +
                ", reviews=" + reviews +
                '}';
    }

    public Double calculateRating() {
        if (reviews == null || reviews.size() == 0) {
            return 0.0;
        } else {
            Double count = 0.0;
            for (ReviewModel r : reviews) {
                count += r.getRating();
            }
            count /= reviews.size();
            return count;
        }
    }
}
