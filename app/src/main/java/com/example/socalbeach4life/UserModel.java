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

    // Overriding equals() to compare two Complex objects
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof UserModel)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        UserModel c = (UserModel) o;

        // Compare the data members and return accordingly
        return this.uid.equals(((UserModel) o).getUid()) && this.getDisplayName().equals(((UserModel) o).getDisplayName()) && this.getEmail().equals(((UserModel) o).getEmail());
    }
}
