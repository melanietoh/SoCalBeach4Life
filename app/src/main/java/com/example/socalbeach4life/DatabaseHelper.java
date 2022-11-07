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
            ReviewModel reviewToAdd = new ReviewModel(uid, beachName, name, message, rating);

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            Map<String, Object> reviewValues = reviewToAdd.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/users/" + uid + "/reviews", reviewValues);
            childUpdates.put("/beaches/" + reviewToAdd.getBeachName() + "/reviews", reviewValues);

            mDatabase.updateChildren(childUpdates);
        }
    }
}
