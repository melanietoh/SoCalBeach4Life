package com.example.socalbeach4life;

import java.util.ArrayList;
import java.util.HashMap;

public class UserModel {
    public String uid;
    public String email;
    public String displayName;
    public HashMap<String, TripModel> trips;
    public HashMap<String, ReviewModel> reviews;

    public UserModel(String uid, String email, String displayName, HashMap<String, TripModel> trips, HashMap<String, ReviewModel> reviews) {
        this.uid = uid;
        this.email = email;
        this.displayName = displayName;
        this.trips = trips;
        this.reviews = reviews;
    }

    public UserModel() {
        this.uid = "erroruid";
        this.email = "erroruseremail";
        this.displayName = "erroruserdisplayname";
        this.trips = new HashMap<>();
        this.reviews = new HashMap<>();
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public HashMap<String, TripModel> getTrips() {
        return trips;
    }

    public HashMap<String, ReviewModel> getReviews() {
        return reviews;
    }
}
