package com.example.socalbeach4life;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public abstract class DatabaseHelper {

    /**
     * Craetes a review tied to a user and a beach
     * @param beachName Must match beach name exactly (BeachModel.getName())
     * @param rating double for how many stars out of 5
     * @param message Optional field. Leave empty string if no message. CANNOT BE NULL
     */
    public static void createReview(String beachName, Double rating, String message) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, uid
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();

            String timeID = String.valueOf(System.currentTimeMillis());
            ReviewModel reviewToAdd = new ReviewModel(timeID, uid, beachName, name, message, rating);

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            Map<String, Object> reviewValues = reviewToAdd.toMap();

            Map<String, Object> childUpdates = new HashMap<>();

            childUpdates.put("/users/" + uid + "/reviews/" + timeID, reviewValues);
            childUpdates.put("/beaches/" + reviewToAdd.getBeachName() + "/reviews/" + timeID, reviewValues);

            mDatabase.updateChildren(childUpdates);
        }
    }

    /**
     * Deletes reviews
     * @param reviewID id of review. stored in ReviewModel.getId
     * @param beachName name of beach. must match exactly
     */
    public static void deleteReview(String reviewID, String beachName) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            DatabaseReference beachReview = mDatabase.child("beaches").child(beachName).child("reviews").child(reviewID);
            beachReview.removeValue();

            DatabaseReference userReview = mDatabase.child("users").child(user.getUid()).child("reviews").child(reviewID);
            userReview.removeValue();
        }
    }

    /**
     * Creates a trip associated with a user
     * @param dateAndTime start time.
     * @param tripMapLink end time
     * @param beach Beach name. Must case match exactly
     * @param parkingLotModel a model object of the parkingLot
     */
    public static void createTrip(String dateAndTime, String tripMapLink, String beach, ParkingLotModel parkingLotModel) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String timeID = String.valueOf(System.currentTimeMillis());
            Map<String, Object> childUpdates = new HashMap<>();

            TripModel trip = new TripModel(timeID, dateAndTime, tripMapLink, beach, parkingLotModel);
            Map<String, Object> tripValues = trip.toMap();
            childUpdates.put("/users/" + user.getUid() + "/trips/" + timeID, tripValues);


            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.updateChildren(childUpdates);
        }
    }
}
