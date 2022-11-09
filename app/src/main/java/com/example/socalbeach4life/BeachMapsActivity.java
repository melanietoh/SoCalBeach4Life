package com.example.socalbeach4life;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BeachMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private boolean beachSelected = false;
    private GoogleMap mMap;
    private ActivityBeachMapsBinding binding;
    private String selectedBeach = "";

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

        FirebaseDatabase root = FirebaseDatabase.getInstance();
        root.getReference("beaches").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    GenericTypeIndicator<HashMap<String, BeachModel>> t = new GenericTypeIndicator<HashMap<String,BeachModel>>() {};

                    List<BeachModel> beachList = new ArrayList<>(task.getResult().getValue(t).values());
                    Log.d("firebase", String.valueOf(beachList));
                    for (int i=0; i<beachList.size(); i++) {
                        LatLng temp = new LatLng(beachList.get(i).getLatitude(), beachList.get(i).getLongitude());
                        mMap.addMarker(new MarkerOptions().position(temp).title(beachList.get(i).getName()));
//                        System.out.println(beachList.get(i).getName() + ": " + beachList.get(i).getLatitude() + ", " + beachList.get(i).getLongitude() + "\n");
                    }
                }
            }
        });
        LatLng usc = new LatLng(34.02, -118.29);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(usc, 10));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // on marker click we are getting the title of our marker
                // which is clicked and displaying it in a toast message.
                beachSelected = true;
                String markerName = marker.getTitle();
                selectedBeach = marker.getTitle();
//                System.out.println("Beach view: " + selectedBeach);
                TextView beachInformation = findViewById(R.id.beachInformationView);

                String beachNameToSearch = marker.getTitle();
                FirebaseDatabase root = FirebaseDatabase.getInstance();
                root.getReference("beaches").child(beachNameToSearch).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            BeachModel beachResult = task.getResult().getValue(BeachModel.class);
//                            System.out.println(beachResult);
                            String beachAddress = "Address: " + beachResult.getAddress();
                            String beachHours = "Hours: " + beachResult.getHours();
                            beachInformation.setText(beachAddress + "\n" + beachHours + "\n");
                            Toast.makeText(BeachMapsActivity.this, "Clicked location is " + markerName, Toast.LENGTH_SHORT).show();
                            TextView t = findViewById(R.id.reviewView);
                            if (beachResult.calculateRating() == 0) {
                                t.setText("Rating: N/A");
                            }
                            else {
                                t.setText("Rating: " + beachResult.calculateRating());
                            }
                            TextView s = findViewById(R.id.selectView);
                            s.setText("Select " + beachNameToSearch);
                        }
                    }
                });
                return false;
            }
        });
    }
    public void viewReviewClick(View view) {
        // Start intent to redirect to reviews page
        if (beachSelected) {
            Intent intent = new Intent(BeachMapsActivity.this, BeachReviewActivity.class);
            intent.putExtra("beachName", selectedBeach);
            startActivity(intent);
        }
    }
    public void selectViewClick(View view) {
        if (beachSelected) {
            // Start intent to continue with trip information
            Intent intent = new Intent(BeachMapsActivity.this, ParkingLotMapsActivity.class);
            intent.putExtra("beachName", selectedBeach);
            startActivity(intent);
        }
    }
}