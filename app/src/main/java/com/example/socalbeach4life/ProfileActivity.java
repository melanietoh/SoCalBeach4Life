package com.example.socalbeach4life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
                                String beachName = trips.get(i).getBeach();
                                String dateAndTime = trips.get(i).getDateAndTime();
                                String mapLink = trips.get(i).getMapsLink();

                                TableRow row1 = (TableRow) LayoutInflater.from(ProfileActivity.this).inflate(R.layout.savedtrips_row1, null);
                                ((TextView) row1.findViewById(R.id.firstRowLabel)).setText(beachName);
                                table.addView(row1);

                                TableRow row2 = (TableRow) LayoutInflater.from(ProfileActivity.this).inflate(R.layout.savedtrips_row2, null);
                                ((TextView) row2.findViewById(R.id.secondRowLabel)).setText(dateAndTime);
                                table.addView(row2);

                                TableRow row3 = (TableRow) LayoutInflater.from(ProfileActivity.this).inflate(R.layout.savedtrips_row3, null);
                                ((Button) row3.findViewById(R.id.thirdRowButton)).setText("<u><a href=\"" + mapLink + "\">Open in Google Maps</a></u>");
                                table.addView(row3);

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
}