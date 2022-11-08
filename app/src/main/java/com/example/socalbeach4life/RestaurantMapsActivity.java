package com.example.socalbeach4life;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
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
        radius = intent.getIntExtra("radius", 1000);
        beachName = intent.getStringExtra("beachName");
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
                }
            }
        });
        String beachNameToSearch = intent.getStringExtra("beachName");
        root.getReference("beaches").child(beachNameToSearch).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    BeachModel beachResult = task.getResult().getValue(BeachModel.class);
                    System.out.println(beachResult);
                    LatLng beach = new LatLng(beachResult.getLatitude(), beachResult.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(beach).title(beachResult.getName()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(beach, 15));
//                    for (int i=0; i<beachResult.getRestaruants(); i++) {
//
//                    }
                    Circle circle = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(-34, 151))
                            .radius(radius/3.28)
                            .strokeColor(Color.RED)
                    );
                }
            }
        });
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        LatLng test2 = new LatLng(-34.3, 151.3);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.addMarker(new MarkerOptions().position(test2).title("Marker Test2"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(beach, 15));
//        Circle circle = mMap.addCircle(new CircleOptions()
//                .center(new LatLng(-34, 151))
//                        .radius(radius/3.28)
//                        .strokeColor(Color.RED)
//        );
    }
}