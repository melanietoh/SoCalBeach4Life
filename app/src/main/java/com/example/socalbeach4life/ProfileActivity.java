package com.example.socalbeach4life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Parcelable;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.location.Location;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileActivity extends AppCompatActivity {
    // public class ProfileActivity extends AppCompatActivity implements OnMapReadyCallback {
    /*
    Viewing user information, including saved trips, reviews (including delete functionality).
     */
    String mapLink = "";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String beachName = "";
    String restaurantName = "";
    String departure = "";
    String arrival = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        View openMapsNow = findViewById(R.id.thirdRowButton);
//        openMapsNow.setOnClickListener(this::redirectClick);

        // Clickable logo -> Return to homepage
        ImageView homepageView = findViewById(R.id.logo);
        homepageView.setClickable(true);
        homepageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent switchToHomepageView = new Intent(ProfileActivity.this, BeachMapsActivity.class);
                startActivity(switchToHomepageView);
            }
        });

        // Setting user related info
        if (user != null) {
            // Name, email address, uid
            String name = user.getDisplayName();
            TextView displayNameLabel = findViewById(R.id.displayNameLabel);
            displayNameLabel.setText(name);

            FirebaseDatabase root = FirebaseDatabase.getInstance();

            root.getReference("users").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        //User retrieved
                        UserModel result = task.getResult().getValue(UserModel.class);
                        HashMap<String, TripModel> tripsHashMap = result.getTrips();
                        ArrayList<TripModel> trips = new ArrayList<>(tripsHashMap.values());

                        TextView numTripsLabel = findViewById(R.id.numTripsLabel);
                        numTripsLabel.setText(trips.size() + " saved trips");

                        // No saved trips
                        if(trips.isEmpty()) {
                            TableLayout table = findViewById(R.id.tableLayout);
                            TableRow row1 = (TableRow) LayoutInflater.from(ProfileActivity.this).inflate(R.layout.savedtrips_row1, null);
                            ((TextView)row1.findViewById(R.id.firstRowLabel)).setText("No saved trips yet!");
                            table.addView(row1);
                        }
                        // Dynamically allocate rows to display each saved trip
                        else {
                            TableLayout table = findViewById(R.id.tableLayout);
                            TextView rating = findViewById(R.id.rating);

                            for(int i=0; i<trips.size(); i++) {
//                                String beachName = trips.get(i).getBeach();
//                                String restaurantName = trips.get(i).getRestaurantName();
//                                String departure = trips.get(i).getDateAndTime();
//                                String arrival = trips.get(i).getArrivalDateAndTime();
                                beachName = trips.get(i).getBeach();
                                restaurantName = trips.get(i).getRestaurantName();
                                departure = trips.get(i).getDateAndTime();
                                arrival = trips.get(i).getArrivalDateAndTime();
                                mapLink = trips.get(i).getMapsLink();

                                TableRow row1 = (TableRow) LayoutInflater.from(ProfileActivity.this).inflate(R.layout.savedtrips_row1, null);
                                ((TextView) row1.findViewById(R.id.firstRowLabel)).setText(restaurantName);
                                table.addView(row1);

                                if(restaurantName != "***") {
                                    TableRow row2 = (TableRow) LayoutInflater.from(ProfileActivity.this).inflate(R.layout.savedtrips_row1, null);
                                    ((TextView) row2.findViewById(R.id.firstRowLabel)).setText(beachName);
                                    table.addView(row2);
                                }

                                TableRow row3 = (TableRow) LayoutInflater.from(ProfileActivity.this).inflate(R.layout.savedtrips_row2, null);
                                ((TextView) row3.findViewById(R.id.secondRowLabel)).setText("Departure: " + departure);
                                table.addView(row3);

                                TableRow row4 = (TableRow) LayoutInflater.from(ProfileActivity.this).inflate(R.layout.savedtrips_row2, null);
                                ((TextView) row4.findViewById(R.id.secondRowLabel)).setText("Arrival: " + arrival);
                                table.addView(row4);

                                TableRow row5 = (TableRow) LayoutInflater.from(ProfileActivity.this).inflate(R.layout.savedtrips_row3, null);
                                ((Button) row5.findViewById(R.id.thirdRowButton)).setText("Open in Google Maps");
                                table.addView(row5);

                                TableRow row6 = (TableRow) LayoutInflater.from(ProfileActivity.this).inflate(R.layout.savedtrips_row4, null);
                                ((Button) row6.findViewById(R.id.fourthRowButton)).setText("Send invite");
                                table.addView(row6);

                                TableRow divider = (TableRow) LayoutInflater.from(ProfileActivity.this).inflate(R.layout.review_divider, null);
                                table.addView(divider);
                            }
                        }
                    }
                }
            });
        }
    }

    public void myReviews(View view) {
        Intent switchToReviewView = new Intent(ProfileActivity.this, ProfileReviewActivity.class);
        startActivity(switchToReviewView);
    }
    public void redirectClick(View view) {
        Uri uri = Uri.parse(mapLink);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void sendInvite(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        intent.putExtra(Intent.EXTRA_SUBJECT, "View My Trip Information!");
        if (restaurantName.equalsIgnoreCase("***")) {
            intent.putExtra(Intent.EXTRA_TEXT, "Hi! Below are the details for my trip to " + beachName + "!\n\nDeparture: " + departure + "\nScheduled Arrival: " + arrival +"\n\nHope you can make it! :)\n\nBest,\n\t" + user.getDisplayName());
        }
        else {
            intent.putExtra(Intent.EXTRA_TEXT, "Hi! Below are the details for my trip to " + beachName + "!\n\nDeparture: " + departure + "\nScheduled Arrival: " + arrival +"\nWe can go to a nearby restaurant named " + restaurantName + ".\n\nHope you can make it! :)\n\nBest.\n\t" + user.getDisplayName());
        }
        startActivity(intent);
    }
    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent loginView = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(loginView);
    }
}