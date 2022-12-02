package com.example.socalbeach4life;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DatabaseHelper {

    public static final String USCADDRESS = "3551 Trousdale Pkwy, Los Angeles, CA 90089";

    /**
     * Creates a review tied to a user and a beach. Deletes any duplicate reviews if they exist
     * @param beachName Must match beach name exactly (BeachModel.getName())
     * @param rating double for how many stars out of 5
     * @param message Optional field. Leave empty string if no message. CANNOT BE NULL
     */
    public static void createReview(String beachName, Double rating, String message, boolean isAnonymous) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, uid
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();

            FirebaseDatabase root = FirebaseDatabase.getInstance();
            root.getReference("users").child(uid).child("reviews").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        GenericTypeIndicator<HashMap<String, ReviewModel>> t = new GenericTypeIndicator<HashMap<String,ReviewModel>>() {}; //beaches are Id'd by name

                        HashMap<String, ReviewModel> pulledReviews = task.getResult().getValue(t);
                        if (pulledReviews != null) {
                            List<ReviewModel> reviewList = new ArrayList<>(pulledReviews.values());
                            for (ReviewModel r : reviewList) {
                                if (r.getBeachName().equals(beachName) && r.getUid().equals(uid)) {
                                    DatabaseHelper.deleteReview(r.getId(), r.beachName);
                                    break;
                                }
                            }
                        }

                        String timeID = String.valueOf(System.currentTimeMillis());
                        ReviewModel reviewToAdd = new ReviewModel(timeID, uid, beachName, name, isAnonymous, message, rating);

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        Map<String, Object> reviewValues = reviewToAdd.toMap();

                        Map<String, Object> childUpdates = new HashMap<>();

                        childUpdates.put("/users/" + uid + "/reviews/" + timeID, reviewValues);
                        childUpdates.put("/beaches/" + reviewToAdd.getBeachName() + "/reviews/" + timeID, reviewValues);

                        mDatabase.updateChildren(childUpdates);

                    }
                }
            });
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

    public static void deleteReviewByBeachName(String beachName) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, uid
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();
            FirebaseDatabase root = FirebaseDatabase.getInstance();
            root.getReference("users").child(uid).child("reviews").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        GenericTypeIndicator<HashMap<String, ReviewModel>> t = new GenericTypeIndicator<HashMap<String,ReviewModel>>() {}; //beaches are Id'd by name
                        HashMap<String, ReviewModel> pulledReviews = task.getResult().getValue(t);
                        if (pulledReviews != null) {
                            List<ReviewModel> reviewList = new ArrayList<>(pulledReviews.values());
                            System.out.println("Starting Review Dupe search with beachname=" + beachName + " uid=" + uid);
                            for (ReviewModel r : reviewList) {
                                System.out.println(r.getId());
                                System.out.println(r.getBeachName());
                                System.out.println(r.getUid());
                                if (r.getBeachName().equals(beachName) && r.getUid().equals(uid)) {
                                    DatabaseHelper.deleteReview(r.getId(), r.beachName);
                                    break;
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * Creates a trip associated with a user
     * @param dateAndTime start time.
     * @param tripMapLink end time
     * @param beach Beach name. Must case match exactly
     * @param parkingLotModel a model object of the parkingLot
     */
    public static void createTrip(String dateAndTime, String arrivalDateAndTime, String tripMapLink, String beach, ParkingLotModel parkingLotModel, String restaurantName, Double restaurantLatitude, Double restaurantLongitude) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String timeID = String.valueOf(System.currentTimeMillis());
            Map<String, Object> childUpdates = new HashMap<>();

            TripModel trip = new TripModel(timeID, dateAndTime, arrivalDateAndTime, tripMapLink, beach, parkingLotModel, restaurantName, restaurantLatitude, restaurantLongitude);
            Map<String, Object> tripValues = trip.toMap();
            childUpdates.put("/users/" + user.getUid() + "/trips/" + timeID, tripValues);


            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.updateChildren(childUpdates);
        }
    }

    /**
    * Generates a Google Maps Route link from USC to the given address
    * @param destination String destination address. Pulled from the Firebase Realtime DB
    */
    public static String generateRouteFromUSC(String destination) {
        //Always start from USC
        String start = DatabaseHelper.USCADDRESS;
        if (!isWaypoint(destination)) {
            destination = parseAddress(destination);
        }

        String link = "https://www.google.com/maps?f=d&saddr=" + parseAddress(start)
                + "&daddr=" + destination + "&dirflg=d";

        return link;
    }

    /**
     * Generates a Google Maps Route link from user's current location to the given address
     * @param destination String destination address. Pulled from Firebase Realtime DB
     */
    public static String generateRouteFromMyLocation(String destination) {
        if (!isWaypoint(destination)) {
            destination = parseAddress(destination);
        }
        String link = "https://www.google.com/maps?saddr=My+Location&daddr=" + destination + "&dirflg=d";
        return link;
    }

    /**
     * Generate a Google Maps Route link from users current location to stopOne and then final destination.
     * @param stopOne First stop
     * @param finalDestination Final stop
     * @return 3 stop route via driving
     */
    public static String generateTwoPartRouteFromCurrentLocation(String stopOne, String finalDestination) {
        if (!isWaypoint(stopOne)) {
            stopOne = parseAddress(stopOne);
        }
        if (!isWaypoint(finalDestination)) {
            finalDestination = parseAddress(finalDestination);
        }
        String start = "My+Location";
        String link = "https://www.google.com/maps/?saddr=" + start +"&daddr=" + stopOne + "+to:" + finalDestination;
        return link;
    }

    /**
     * Generate a Google Maps Route link from users current location to stopOne and then final destination.
     * @param start Initial starting address
     * @param stopOne first stop
     * @param finalDestination final stop
     * @return 3 stop route via driving
     */
    public static String generateTwoPartRouteWithCustomStart(String start, String stopOne, String finalDestination) {
        if (!isWaypoint(start)){
            start = parseAddress(start);
        }
        if (!isWaypoint(stopOne)) {
            stopOne = parseAddress(stopOne);
        }
        if (!isWaypoint(finalDestination)) {
            finalDestination = parseAddress(finalDestination);
        }
        String link = "https://www.google.com/maps/?saddr=" + start +"&daddr=" + stopOne + "+to:" + finalDestination;
        return link;
    }
    
    public static String generateWalkingRoute(String start, String destination) {
        if (!isWaypoint(start)) {
            start = parseAddress(start);
        }
        if (!isWaypoint(destination)) {
            destination = parseAddress(destination);
        }

        String link = "https://www.google.com/maps?f=d&saddr=" + (start)
                + "&daddr=" + (destination) + "&dirflg=w";

        return link;
    }

    public static String parseAddress(String address){
        address = address.substring(0, address.length()-6);
        address += ", USA";
        address = address.replaceAll(" ", "+");
        return address;
    }

    public static boolean isWaypoint(String input) {
        for (char c : input.toCharArray()) {
            if (c == ' ' || c == '-' || c == '.' || c == ',' || c >= '0' && c <= '9') {

            } else {
                return false;
            }
        }
        return true;
    }
}
