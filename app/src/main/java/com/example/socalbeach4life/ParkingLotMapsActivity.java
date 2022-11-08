package com.example.socalbeach4life;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
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

import java.util.ArrayList;

public class ParkingLotMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityParkingLotMapsBinding binding;
    private String beachName = "";
    private ArrayList<ParkingLotModel> parkingLots;
    private boolean lotSelected = false;
    private String selectedLot = "";
    private Double beachLat;
    private double beachLon;
    private Double lotDist;
    private ParkingLotModel lotOneObj;
    private ParkingLotModel lotTwoObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityParkingLotMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        beachName = intent.getStringExtra("beachName");
        beachLat = intent.getDoubleExtra("latitude", 0.0);
        beachLon = intent.getDoubleExtra("longitude", 0.0);
        TextView tv = findViewById(R.id.headerView);
        tv.setText("Parking Lots near " + beachName);
        parkingLots = new ArrayList<ParkingLotModel>(); // not sure how to pass ArrayList of custom class from BeachMaps Activity
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
        LatLng lot1 = null;
        String lotOneName = "";
        LatLng lot2 = null;
        String lotTwoName = "";
        // Add a marker in Sydney and move the camera
        if (beachName.equalsIgnoreCase("Venice Beach")) {
            lotOneObj = new ParkingLotModel("Lot 9", "89 Ashland Ave, Santa Monica, CA 90405", 14.7, 34.0, -118.48);
            lot1 = new LatLng(34, -118.48);
            lotOneName = "Lot 9, 89 Ashland Ave, Santa Monica, CA 90405";
            lotTwoObj = new ParkingLotModel("Parking", "2100 Ocean Front Walk, Venice, CA 90291", 13.7, 33.98, -118.47);
            lot2 = new LatLng(33.98, -118.47);
            lotTwoName = "Parking, 2100 Ocean Front Walk, Venice, CA 90291";
        }
        else if (beachName.equalsIgnoreCase("Bruce's Beach")) {
            lotOneObj = new ParkingLotModel("111 26th St Parking", "111 26th St, Manhattan Beach, CA 90266", 18.5, 33.89, -118.41);
            lot1 = new LatLng(lotOneObj.lat, lotOneObj.lon);
            lotTwoObj = new ParkingLotModel("Dune Park Parking", "Manhattan Beach, CA 90266", 16.8, 33.90, -118.41);
            lot2 = new LatLng(lotTwoObj.lat, lotTwoObj.lon);
        }
        else if (beachName.equalsIgnoreCase("Playa del Rey Beach")) {
            lotOneObj = new ParkingLotModel("AirGarage", "200 Culver Blvd, Playa Del Rey, CA 90293", 16.0, 33.959, -118.448);
            lot1 = new LatLng(lotOneObj.lat, lotOneObj.lon);
            lotTwoObj = new ParkingLotModel("422 Campdell St Parking", "422 Campdell St, Playa Del Rey, CA 90293", 17.0, 33.957, -118.44);
            lot2 = new LatLng(lotTwoObj.lat, lotTwoObj.lon);
        }
        else if (beachName.equalsIgnoreCase("El Segundo Beach")) {
            lotOneObj = new ParkingLotModel("Grand Ave Parking Lot", "12793 Vista Del Mar, Playa Del Rey, CA 90293", 18.0, 33.94, -118.44);
            lot1 = new LatLng(lotOneObj.lat, lotOneObj.lon);
            lotTwoObj = new ParkingLotModel("533 Main St Parking", "533 Main St, El Segundo, CA 90245", 16.0, 33.923, -118.416);
            lot2 = new LatLng(lotTwoObj.lat, lotTwoObj.lon);
        }
        else {
            lotOneObj = new ParkingLotModel("Grand Ave Parking Lot", "12793 Vista Del Mar, Playa Del Rey, CA 90293", 18.0, 33.94, -118.44);
            lot1 = new LatLng(lotOneObj.lat, lotOneObj.lon);
            lotTwoObj = new ParkingLotModel("Dockweiler - Grand Ave", "699 W Grand Ave, El Segundo, CA 90293", 17.7, 33.919, -118.4165);
            lot2 = new LatLng(lotTwoObj.lat, lotTwoObj.lon);
        }
        LatLng beach = new LatLng(beachLat, beachLon);
        mMap.addMarker(new MarkerOptions().position(beach).title(beachName).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.addMarker(new MarkerOptions().position(lot1).title(lotOneObj.name));
        mMap.addMarker(new MarkerOptions().position(lot2).title(lotTwoObj.name));
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
                    TextView s = findViewById(R.id.selectView);
                    s.setText("");
                }
                else {
                    lotSelected = true;
                    String markerName = marker.getTitle();
                    selectedLot = marker.getTitle();
                    Toast.makeText(ParkingLotMapsActivity.this, "Clicked location is " + markerName, Toast.LENGTH_SHORT).show();
                    TextView t = findViewById(R.id.informationView);
                    if (t.getText().equals(lotOneObj.name)) {
                        t.setText("");
                        t.setText("Address: " + lotOneObj.address + "\nDistance: " + lotOneObj.distance);
                    }
                    else { // clicked on Lot 2
                        t.setText("");
                        t.setText("Address: " + lotTwoObj.address + "\nDistance: " + lotTwoObj.distance);
                    }
                    TextView s = findViewById(R.id.selectView);
                    s.setText("Select beach");
                }
                // not sure what extra data we want to pass to SaveTripActivity
                Intent intent = new Intent(ParkingLotMapsActivity.this, SaveTripActivity.class);
                // startActivity(intent);
                return false;
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
//                    intent.putExtra("latitude", lat);
//                    intent.putExtra("longitude", lon);
             startActivity(intent);
        }
    }
}