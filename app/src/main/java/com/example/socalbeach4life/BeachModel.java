package com.example.socalbeach4life;

import java.util.ArrayList;
import java.util.HashMap;

public class BeachModel {
    String name;
    String address;
    String hours;
    Double latitude;
    Double longitude;
    ArrayList<ParkingLotModel> parkingLots;
    HashMap<String, ReviewModel> reviews;
    ArrayList<RestaurantModel> restaruants;

    public BeachModel(String name, String address, String hours, Double latitude, Double longitude, ArrayList<ParkingLotModel> parkingLots, HashMap<String, ReviewModel> reviews, ArrayList<RestaurantModel> restaruants) {
        this.name = name;
        this.address = address;
        this.hours = hours;
        this.latitude = latitude;
        this.longitude = longitude;
        this.parkingLots = parkingLots;
        this.reviews = reviews;
        this.restaruants = restaruants;
    }

    public BeachModel() {
        this.name = "errorbeach";
        this.address = "errorbeachaddress";
        this.hours = "errorbeachhours";
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.parkingLots = new ArrayList<>();
        this.reviews = new HashMap<>();
        this.restaruants = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getHours() {
        return hours;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public ArrayList<RestaurantModel> getRestaruants() {
        return restaruants;
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
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", parkingLots=" + parkingLots +
                ", reviews=" + reviews +
                ", restaruants=" + restaruants +
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
