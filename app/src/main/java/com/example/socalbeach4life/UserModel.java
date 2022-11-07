package com.example.socalbeach4life;

import java.util.ArrayList;

public class UserModel {
    public String uid;
    public String email;
    public String password;
    public String displayName;
    public ArrayList<TripModel> trips;
    public ArrayList<ReviewModel> reviews;

    public UserModel(String uid, String email, String password, String displayName, ArrayList<TripModel> trips, ArrayList<ReviewModel> reviews) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.trips = trips;
        this.reviews = reviews;
    }

    public UserModel() {
        this.uid = "erroruid";
        this.email = "erroruseremail";
        this.password = "erroruserpassword";
        this.displayName = "erroruserdisplayname";
        this.trips = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ArrayList<TripModel> getTrips() {
        return trips;
    }

    public ArrayList<ReviewModel> getReviews() {
        return reviews;
    }
}
