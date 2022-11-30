package com.example.socalbeach4life;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.socalbeach4life.databinding.ActivityRestaurantMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestaurantMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityRestaurantMapsBinding binding;
    private int radius;
    private String beachName;
    private BeachModel beachObj;
    private String restaurantName;
    private boolean isRestaurant;
    private String link;
    private double restaurantLat;
    private double restaurantLong;
    private String selectedLot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRestaurantMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        RestaurantModel restaurantOne = new RestaurantModel("Restaurant Name", "Yelp.com", "2pm-8pm", 0.0, 0.0, 0.0);
        Intent intent = getIntent();
        radius = intent.getIntExtra("radius", 0);
        beachName = intent.getStringExtra("beachName");
        selectedLot = intent.getStringExtra("selectedLot");
        View yelp = findViewById(R.id.yelpView);
        yelp.setOnClickListener(this::menuLinkClicked);
        View openMapsNow = findViewById(R.id.googleMapsNow);
        openMapsNow.setOnClickListener(this::directionsClicked);
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
        Intent intent = getIntent();
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        root.getReference("beaches").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    GenericTypeIndicator<HashMap<String, BeachModel>> t = new GenericTypeIndicator<HashMap<String,BeachModel>>() {}; //beaches are Id'd by name

                    List<BeachModel> beachList = new ArrayList<>(task.getResult().getValue(t).values());
                    Log.d("firebase", String.valueOf(beachList));

                    String beachNameToSearch = intent.getStringExtra("beachName");
                    System.out.println("Searching for Restaurants Near Beach: " + beachNameToSearch);
                    root.getReference("beaches").child(beachNameToSearch).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else {
                                BeachModel beachResult = task.getResult().getValue(BeachModel.class);
                                beachObj = beachResult;
                                // System.out.println(beachResult);
                                LatLng beach = new LatLng(beachResult.getLatitude(), beachResult.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(beach).title(beachResult.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                                System.out.println("Beach Latitude: " + beachResult.getLatitude() + ", longitude: " + beachResult.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(beach, 10));
                                ArrayList<RestaurantModel> restaurants = beachResult.getRestaruants();
                                if (radius == 0) {
                                    for (int i=0; i<restaurants.size(); i++) {
                                        LatLng res = new LatLng(restaurants.get(i).getLatitude(), restaurants.get(i).getLongitude());
                                        mMap.addMarker(new MarkerOptions().position(res).title(restaurants.get(i).getRestaurantName()));
                                    }
                                }
                                else {
                                    for (int i=0; i<restaurants.size(); i++) {
                                        // display on map if within radius
                                        if (restaurants.get(i).getDist()*5280 < radius) {
                                            // System.out.println(restaurants.get(i).getRestaurantName());
                                            LatLng res = new LatLng(restaurants.get(i).getLatitude(), restaurants.get(i).getLongitude());
                                            mMap.addMarker(new MarkerOptions().position(res).title(restaurants.get(i).getRestaurantName()));
                                        }
                                    }
                                }
                                Circle circle = mMap.addCircle(new CircleOptions()
                                        .center(new LatLng(beachResult.getLatitude(), beachResult.getLongitude()))
                                        .radius(radius/3.28)
                                        .strokeColor(Color.RED)
                                );
                                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {
                                        // on marker click we are getting the title of our marker
                                        // which is clicked and displaying it in a toast message.
                                        if (marker.getTitle().equalsIgnoreCase(beachName)) {
                                            isRestaurant = false;
                                            restaurantName = "";
                                            String markerName = marker.getTitle();
                                            Toast.makeText(RestaurantMapsActivity.this, "Clicked location is " + markerName, Toast.LENGTH_SHORT).show();
                                            TextView t = findViewById(R.id.informationView);
                                            t.setText("");
                                            TextView s = findViewById(R.id.titleView);
                                            s.setText("");
                                            TextView y = findViewById(R.id.yelpView);
                                            y.setText("");
                                        }
                                        else {
                                            isRestaurant = true;
                                            String markerName = marker.getTitle();
                                            restaurantName = markerName;
                                            link = DatabaseHelper.generateWalkingRoute(beachName, restaurantName);
                                            Toast.makeText(RestaurantMapsActivity.this, "Clicked location is " + markerName, Toast.LENGTH_SHORT).show();
                                            TextView t = findViewById(R.id.informationView);
                                            TextView s = findViewById(R.id.titleView);
                                            TextView y = findViewById(R.id.yelpView);
                                            TextView m = findViewById(R.id.googleMapsNow);
                                            for(int i=0; i<beachResult.getRestaruants().size(); i++) {
                                                String name = beachResult.getRestaruants().get(i).getRestaurantName();
                                                String hours = beachResult.getRestaruants().get(i).getHours();
                                                String yelpLink = beachResult.getRestaruants().get(i).getYelpLink();
                                                if (markerName.equals(name)) {
                                                    s.setText("Select " + name);
                                                    t.setText("Hours: " + hours + "\n");
                                                    y.setText(R.string.restaurantMenu);
                                                    m.setText(R.string.restaurantMapsLink);
                                                    restaurantLat = beachResult.getRestaruants().get(i).getLatitude();
                                                    restaurantLong = beachResult.getRestaruants().get(i).getLongitude();
                                                    break;
                                                }
                                            }
                                        }
                                        return false;
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    // TODO
    public void selectRestaurant(View view) {
        // Intent to savedTripActivity
        Intent intent = new Intent(RestaurantMapsActivity.this, SaveTripActivity.class);
        // Pass over the restaurant name + address
        intent.putExtra("restaurantName", restaurantName);
        intent.putExtra("restaurantAddressLat", restaurantLat);
        intent.putExtra("restaurantAddressLong", restaurantLong);
        intent.putExtra("beachName", beachName);
        intent.putExtra("parkingLot", selectedLot);
        startActivity(intent);
    }
    public void menuLinkClicked(View view) {
        String link = "";
        System.out.println("link clicked");
        System.out.println("TESTTESTTEST: " + beachObj.getName());
        if (isRestaurant) {
            for (int i=0; i<beachObj.getRestaruants().size(); i++) {
                if (beachObj.getRestaruants().get(i).getRestaurantName().equalsIgnoreCase(restaurantName)) {
                    link = beachObj.getRestaruants().get(i).getYelpLink();
                }
            }
            Uri uri = Uri.parse(link);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
    public void directionsClicked(View view) {
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}