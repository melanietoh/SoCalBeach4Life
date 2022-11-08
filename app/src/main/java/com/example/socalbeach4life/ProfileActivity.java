package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Parcelable;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.location.Location;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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
            String email = user.getEmail();
            String uid = user.getUid();

            TextView displayNameLabel = findViewById(R.id.displayNameLabel);
            displayNameLabel.setText(name);

            TextView numTripsLabel = findViewById(R.id.numReviewsLabel);
//            numReviewsLabel.setText(); ArrayList.count
        }

        // start on "Saved Trips"
        arrayListOne = new ArrayList<String>();
        arrayListTwo = new ArrayList<String>();
        arrayListOne.add("Trip 1");
        arrayListTwo.add("Review 1");
//        Button b3 = (Button) findViewById(R.id.button3);
//        b3.setOnClickListener(this::onClickButton);
//        Button b4 = (Button)findViewById(R.id.button4);
//        b4.setOnClickListener(this::onClick);
        // alertView("testing alert controller");
        // Initialize the SDK
//        Places.initialize(getApplicationContext(), "AIzaSyBis7UdegqS1LDfzWWWOlwrYbo9W3eRQoU");
//        // Create a new PlacesClient instance
//        PlacesClient placesClient = Places.createClient(this);
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }
//    @Override
//    public void onMapReady(GoogleMap map) {
//        this.map = map;
//        // ...
//        // Turn on the My Location layer and the related control on the map.
//        updateLocationUI();
//        // Get the current location of the device and set the position of the map.
//        getDeviceLocation();
//   }
//    private void alertView( String message ) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        dialog.setTitle( "Hello" )
//                .setIcon(R.drawable.car)
//                .setMessage(message)
//        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                //set what would happen when positive button is clicked
//                finish();
//            }
//        })
//     .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface dialoginterface, int i) {
//          dialoginterface.cancel();
//          }})
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialoginterface, int i) {
//                        // dialoginterface.cancel();
//                        finish();
//                    }
//                }).show();
//    }
    // display either ArrayList<Trip> or ArrayList<Review> depending on which button was clicked
    // hard part is dynamically figuring out TableView but it should be fine with Mel's code
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(0, 0))
//                .title("Marker"));
//    }
    public void myReviews(View v) {
        Intent switchToReviewView = new Intent(ProfileActivity.this, ProfileReviewActivity.class);
        startActivity(switchToReviewView);
    }
}