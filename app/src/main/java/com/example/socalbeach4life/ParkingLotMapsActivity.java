package com.example.socalbeach4life;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.socalbeach4life.databinding.ActivityParkingLotMapsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingLotMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityParkingLotMapsBinding binding;
    private String beachName = "";
    private boolean lotSelected = false;
    private String selectedLot = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityParkingLotMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Retrieve selected beach and set relevant info
        Intent intent = getIntent();
        beachName = intent.getStringExtra("beachName");
        TextView tv = findViewById(R.id.lotHeaderView);
        tv.setText("Parking lots near " + beachName);

        TextView s = findViewById(R.id.lotSelectView);
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

        // Marker for selected beach
        Intent intent = getIntent();
        String beachNameToSearch = intent.getStringExtra("beachName");
        System.out.println("Parking lot view: " + beachNameToSearch);

        FirebaseDatabase root = FirebaseDatabase.getInstance();
        root.getReference("beaches").child(beachNameToSearch).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    BeachModel beachResult = task.getResult().getValue(BeachModel.class);
                    LatLng beach = new LatLng(beachResult.getLatitude(), beachResult.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(beach).title(beachNameToSearch).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                    // Iterate through parking lots to create markers
                    ArrayList<ParkingLotModel> parkingLots = beachResult.getParkingLots();
                    for(int i=0; i<parkingLots.size(); i++) {
                        Double lat = parkingLots.get(i).getLatitude();
                        Double lon = parkingLots.get(i).getLongitude();
                        String name = parkingLots.get(i).getName();
                        LatLng parkingLot = new LatLng(lat, lon);
                        mMap.addMarker(new MarkerOptions().position(parkingLot).title(name));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(beach, 11));

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            // on marker click we are getting the title of our marker
                            // which is clicked and displaying it in a toast message.
                            if (marker.getTitle().equalsIgnoreCase(beachName)) {
                                lotSelected = false;
                                String markerName = marker.getTitle();
                                Toast.makeText(ParkingLotMapsActivity.this, "Clicked location is " + markerName, Toast.LENGTH_SHORT).show();
                                TextView t = findViewById(R.id.informationView);
                                t.setText("");
                                TextView s = findViewById(R.id.lotSelectView);
                                s.setText("");
                            }
                            else {
                                lotSelected = true;
                                String markerName = marker.getTitle();
                                selectedLot = marker.getTitle();
                                Toast.makeText(ParkingLotMapsActivity.this, "Clicked location is " + markerName, Toast.LENGTH_SHORT).show();
                                TextView t = findViewById(R.id.informationView);
                                TextView s = findViewById(R.id.lotSelectView);
                                for(int i=0; i<parkingLots.size(); i++) {
                                    String name = parkingLots.get(i).getName();
                                    String address = parkingLots.get(i).getAddress();
                                    Double distance = parkingLots.get(i).getDistance();
                                    if (markerName.equals(name)) {
                                        t.setText("Address: " + address + "\nDistance: " + distance + " miles");
                                        s.setText("Select " + name);
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

    public void selectViewClick(View view) {
        System.out.println("Selected Beach");
        if (lotSelected) {
            // start intent to continue with trip information
            Intent intent = new Intent(ParkingLotMapsActivity.this, SaveTripActivity.class);
            intent.putExtra("beachName", beachName);
            intent.putExtra("parkingLot", selectedLot);
             startActivity(intent);
        }
    }
}