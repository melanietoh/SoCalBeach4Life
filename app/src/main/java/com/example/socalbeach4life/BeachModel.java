package com.example.socalbeach4life;

import java.util.ArrayList;
import java.util.HashMap;

public class BeachModel {
    String name;
    String coord;
    ArrayList<ParkingLotModel> parkingLots;
    HashMap<String, ReviewModel> reviews;
    ArrayList<RestaurantModel> restaruants;

    public BeachModel(String name, String coord, ArrayList<ParkingLotModel> parkingLots, HashMap<String, ReviewModel> reviews, ArrayList<RestaurantModel> restaruants) {
        this.name = name;
        this.coord = coord;
        this.parkingLots = parkingLots;
        this.reviews = reviews;
        this.restaruants = restaruants;
    }

    public BeachModel() {
        this.name = "errorbeach";
        this.coord = "0.0";
        this.parkingLots = new ArrayList<>();
        this.reviews = new HashMap<>();
        this.restaruants = new ArrayList<>();
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

    public HashMap<String,ReviewModel> getReviews() {
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
            for (HashMap.Entry<String, ReviewModel> set :
                    reviews.entrySet()) {

                count += set.getValue().getRating();
            }
            count /= reviews.size();
            return count;
        }
    }
}
