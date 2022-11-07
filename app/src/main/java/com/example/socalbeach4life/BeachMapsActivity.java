package com.example.socalbeach4life;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.TextView;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.socalbeach4life.databinding.ActivityBeachMapsBinding;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import android.content.Intent;

import java.util.ArrayList;

public class BeachMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private boolean beachSelected = false;
    private GoogleMap mMap;
    private ActivityBeachMapsBinding binding;
    private String selectedBeach = "";
    private double lat;
    private double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBeachMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        TextView t = findViewById(R.id.reviewView);
        t.setOnClickListener(this::viewReviewClick);
        TextView s = findViewById(R.id.selectView);
        s.setOnClickListener(this::selectViewClick);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng dockweiler = new LatLng(33.9, -118.4);
        LatLng bruce = new LatLng(33.89, -118.42);
        LatLng venice = new LatLng(33.99, -118.47);
        LatLng playaDelRey = new LatLng(33.95, -118.44);
        LatLng elSegundo = new LatLng(33.91, -118.42);
        LatLng usc = new LatLng(34.02, -118.29);
        // ArrayList<BeachModel> beaches = new ArrayList<BeachModel>();
        mMap.addMarker(new MarkerOptions().position(dockweiler).title("Dockweiler Beach").snippet("Address\nDistance\nHours"));
        mMap.addMarker(new MarkerOptions().position(bruce).title("Bruce's Beach"));
        mMap.addMarker(new MarkerOptions().position(venice).title("Venice Beach"));
        mMap.addMarker(new MarkerOptions().position(playaDelRey).title("Playa Del Rey Beach"));
        mMap.addMarker(new MarkerOptions().position(elSegundo).title("El Segundo Beach"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usc, 10));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // on marker click we are getting the title of our marker
                // which is clicked and displaying it in a toast message.
                beachSelected = true;
                String markerName = marker.getTitle();
                selectedBeach = marker.getTitle();
                lat = marker.getPosition().latitude;
                lon = marker.getPosition().longitude;
                TextView beachInformation = findViewById(R.id.beachInformationView);
                String beachAddress = "Address: ";
                String beachHours = "Hours: ";
                Double rating = 0.0;
                if (selectedBeach.equalsIgnoreCase("Venice Beach")) {
                    beachAddress += "1800 Ocean Front Walk\nVenice, CA 90291";
                    beachHours += "6am - 12am";
                    rating = 4.4;
                }
                else if (selectedBeach.equalsIgnoreCase("Bruce's Beach")) {
                    beachAddress += "2600 Highland Ave\nManhattan Beach, CA 90266";
                    beachHours += "6am - 10PM";
                    rating = 4.7;
                }
                else if (selectedBeach.equalsIgnoreCase("Playa del Rey Beach")) {
                    beachAddress += "Culver Blvd & Pacific Ave\nLos Angeles, CA 90293";
                    beachHours += "9am - 5PM";
                    rating = 4.7;
                }
                else if (selectedBeach.equalsIgnoreCase("El Segundo Beach")) {
                    beachAddress += "Grand Ave & Vista Del Mar Blvd\nEl Segundo, CA 90245";
                    beachHours += "6am - 10PM";
                    rating = 4.6;
                }
                else { // Dockweiler
                    beachAddress += "12000 Vista Del Mar\nPlaya Del Rey, CA 90293";
                    beachHours += "6am - 10PM";
                    rating = 4.5;
                }
                beachInformation.setText( beachAddress + "\n" + beachHours + "\n");
                Toast.makeText(BeachMapsActivity.this, "Clicked location is " + markerName, Toast.LENGTH_SHORT).show();
                TextView t = findViewById(R.id.reviewView);
                t.setText("Rating: " + rating);
                TextView s = findViewById(R.id.selectView);
                s.setText("Select beach");

                // not sure what extra data we want to pass to SaveTripActivity
                Intent intent = new Intent(BeachMapsActivity.this, SaveTripActivity.class);
                // startActivity(intent);
                return false;
            }
        });
    }
    public void viewReviewClick(View view) {
        // start intent to redirect to reviews page
        if (beachSelected) {
            Intent intent = new Intent(BeachMapsActivity.this, BeachReviewActivity.class);
            startActivity(intent);
        }
    }
    public void selectViewClick(View view) {
        System.out.println("Selected Beach");
        if (beachSelected) {
            // start intent to continue with trip information
            Intent intent = new Intent(BeachMapsActivity.this, ParkingLotMapsActivity.class);
            intent.putExtra("beachName", selectedBeach);
            intent.putExtra("latitude", lat);
            intent.putExtra("longitude", lon);
            startActivity(intent);
        }
    }
}