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
        createData();
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

    public void createData() {
        ArrayList<BeachModel> beaches = new ArrayList<BeachModel>();
        ArrayList<ParkingLotModel> parkingLots = new ArrayList<ParkingLotModel>();
        ArrayList<RestaurantModel> restaurants = new ArrayList<RestaurantModel>();

        // DONE
        parkingLots.add(new ParkingLotModel("Lot 9", "89 Ashland Ave, Santa Monica, CA 90405", 14.7, 33.999402300789484, -118.4815504873334));
        parkingLots.add(new ParkingLotModel("Parking", "2100 Ocean Front Walk, Venice, CA 90291", 13.7, 33.98400454721057, -118.47161948151091));
        restaurants.add(new RestaurantModel("Tocaya - Venice", "https://tocaya.com/menu/", "11:30am-10pm", 33.98688477645229, -118.47163373151092, 0.1));
        restaurants.add(new RestaurantModel("High Rooftop Lounge", "https://www.yelp.com/biz/high-rooftop-lounge-venice?osq=high+rooftop+lounge", "3pm-10pm", 33.987277349784016, -118.47210245027276, 0.1));
        restaurants.add(new RestaurantModel("Venice Way Pizza", "https://www.yelp.com/biz/venice-way-pizza-venice", "12pm-8pm", 33.988, -118.47, 0.1));
        restaurants.add(new RestaurantModel("The Cow's End Cafe", "https://www.yelp.com/biz/the-cows-end-cafe-venice", "6am-6pm", 33.98706503224278, -118.47256467568808, 0.6));
        restaurants.add(new RestaurantModel("Cafe Gratitude Venice", "https://www.yelp.com/biz/cafe-gratitude-venice", "9am-9pm", 33.998164033865976, -118.47388330637138, 0.8));
        beaches.add(new BeachModel("Venice Beach", "1800 Ocean Front Walk\nVenice, CA 90291", "6am - 12am",33.99343542017394, -118.48057488378026, parkingLots, null, restaurants));

        // DONE
        parkingLots.clear();
        restaurants.clear();
        System.out.println(parkingLots.size());
        System.out.println(restaurants.size());
        parkingLots.add(new ParkingLotModel("111 26th St Parking", "111 26th St, Manhattan Beach, CA 90266", 18.5, 33.894113037504525, -118.41635668918417));
        parkingLots.add(new ParkingLotModel("Dune Park Parking", "Manhattan Beach, CA 90266", 16.8, 33.89956607792758, -118.41229171617141));
        restaurants.add(new RestaurantModel("Bobo Chinese Deli", "https://www.yelp.com/biz/bobo-chinese-deli-manhattan-beach-2", "11am-9:30pm", 33.90804421018113, -118.42167016722694, 0.2));
        restaurants.add(new RestaurantModel("North End Caffe", "https://www.google.com/search?client=safari&rls=en&q=north+end+caffe&ie=UTF-8&oe=UTF-8", "8am-3pm", 33.89969178791067, -118.4173513450068, 0.4));
        restaurants.add(new RestaurantModel("Fishbar Manhattan Beach Seafood Restaurant", "https://www.fishbarmb.com/#fishbarr", "11am-12am", 33.901792852938804, -118.41827650611611, 1.8));
        restaurants.add(new RestaurantModel("zinc@shade", "https://www.yelp.com/biz/zinc-shade-manhattan-beach", "7am-9pm", 33.88664672948085, -118.40836401617169, 0.8));
        restaurants.add(new RestaurantModel("Pancho's Restaurant", "https://www.yelp.com/biz/panchos-restaurant-manhattan-beach-6", "11am-9pm", 33.903204228724455, -118.41795794045026, 0.5));
        beaches.add(new BeachModel("Bruce's Beach", "2600 Highland Ave\nManhattan Beach, CA 90266", "6am - 10PM", 33.89433245994964, -118.41539750267782, parkingLots, null, restaurants));
        System.out.println(parkingLots.size());
        System.out.println(restaurants.size());
        parkingLots.clear();
        restaurants.clear();
        parkingLots.add(new ParkingLotModel("Grand Ave Parking Lot", "12793 Vista Del Mar, Playa Del Rey, CA 90293", 18.0, 33.8943279897412, -118.41537864167768));
        parkingLots.add(new ParkingLotModel("Dockweiler - Grand Ave", "699 W Grand Ave, El Segundo, CA 90293", 17.7, 33.916678250642896, -118.4275165585001));
        restaurants.add(new RestaurantModel("Oceans Cafe", "https://www.yelp.com/biz/oceans-cafe-and-grill-playa-del-rey-2", "9am-3pm", 33.92298222875978, -118.43208084685432, 0.8));
        restaurants.add(new RestaurantModel("Rock & Brews", "https://www.yelp.com/biz/rock-and-brews-el-segundo-el-segundo?osq=Rock+%26+Brews", "11am-9:30pm", 33.917939389188234, -118.4161345926185, 1.0));
        restaurants.add(new RestaurantModel("Fishbar Manhattan Beach Seafood Restaurant", "https://www.fishbarmb.com/#fishbarr", "11am-12am", 33.90185518706421, -118.41819067543258, 1.8));
        restaurants.add(new RestaurantModel("Chef Hannes Restaurant", "https://www.yelp.com/biz/chef-hannes-restaurant-el-segundo?osq=Chef+Hannes+Restaurant", "5pm-10pm", 33.92, -118.416, 0.9));
        restaurants.add(new RestaurantModel("Jame Enoteca", "https://www.yelp.com/biz/jame-enoteca-el-segundo-2", "5pm-10pm", 33.915, -118.404, 1.0));
        beaches.add(new BeachModel("Dockweiler Beach", "12000 Vista Del Mar\nPlaya Del Rey, CA 90293", "6am - 10PM", 33.93071731982929, -118.43560955849973, parkingLots, null, restaurants));

        // DONE
        parkingLots.clear();
        restaurants.clear();
        parkingLots.add(new ParkingLotModel("AirGarage", "200 Culver Blvd, Playa Del Rey, CA 90293", 16.0, 33.958924162187316, -118.44808048918254));
        parkingLots.add(new ParkingLotModel("422 Campdell St Parking", "422 Campdell St, Playa Del Rey, CA 90293", 17.0, 33.9570592602268, -118.44335595849905));
        restaurants.add(new RestaurantModel("Bacari PDR", "https://www.yelp.com/biz/bacari-pdr-playa-del-rey-2?osq=Best+Ocean+View+Restaurants", "5pm-10pm", 33.958835755952656, -118.44840127384074, 0.1));
        restaurants.add(new RestaurantModel("Hank's Pizza", "https://www.yelp.com/biz/hanks-pizza-playa-del-rey", "10am-10pm", 33.95638056105454, -118.4427927450054, 0.4));
        restaurants.add(new RestaurantModel("Sushi Beluga", "https://www.yelp.com/biz/sushi-beluga-playa-del-rey-2", "11:30am-10pm", 33.95979095714571, -118.44847237384067, 0.2));
        restaurants.add(new RestaurantModel("The Good Pizza", "https://www.yelp.com/biz/the-good-pizza-playa-del-rey", "11am-9pm", 33.958935966487985, -118.43818491616989, 0.7));
        restaurants.add(new RestaurantModel("The Tripel", "https://www.yelp.com/biz/the-tripel-playa-del-rey", "5pm-12am", 33.96046686562387, -118.44696064689981, 0.2));
        for (int i=0; i<restaurants.size(); i++) {
            System.out.println(restaurants.get(i).getRestaurantName());
        }
        beaches.add(new BeachModel("Playa Del Rey Beach", "Culver Blvd & Pacific Ave\nLos Angeles, CA 90293", "9am - 5PM", 33.95225367090568, -118.44884424130709, parkingLots, null, restaurants));
        for (int i=0; i<restaurants.size(); i++) {
            System.out.println(restaurants.get(i).getRestaurantName());
        }
        // DONE
        parkingLots.clear();
        restaurants.clear();
        parkingLots.add(new ParkingLotModel("Grand Ave Parking Lot", "12793 Vista Del Mar, Playa Del Rey, CA 90293", 18.0, 33.8943279897412, -118.41537864167768));
        parkingLots.add(new ParkingLotModel("533 Main St Parking", "533 Main St, El Segundo, CA 90245", 16.0, 33.9233681174722, -118.41618558686702));
        restaurants.add(new RestaurantModel("The Habit Burger Grill", "https://www.yelp.com/biz/the-habit-burger-grill-el-segundo", "10:30am-11pm", 33.9202460755348, -118.39637985803158, 0.3));
        restaurants.add(new RestaurantModel("Pisces Sushi", "https://www.yelp.com/biz/pisces-sushi-manhattan-beach?osq=all+you+can+eat+sushi+buffet", "11:30am-9pm", 33.898360177846776,-118.4165514364223, 0.2));
        restaurants.add(new RestaurantModel("Ensenada's Surf N Turf Grill", "https://www.yelp.com/biz/ensenadas-surf-n-turf-grill-el-segundo", "10am-8pm", 33.91932465096471, -118.41325873104434, 0.4));
        restaurants.add(new RestaurantModel("Chef Hannes Restaurant", "https://m.yelp.com/biz/chef-hannes-restaurant-el-segundo	11:30am-2pm", "5-9pm", 33.921414075479774, -118.41616867585113, 0.4));
        restaurants.add(new RestaurantModel("Sloopy's Beach Cafe", "https://www.yelp.com/biz/sloopys-beach-cafe-manhattan-beach", "11:30am-8:30pm", 33.89975099292145, -118.41714959914451, 0.2));
        beaches.add(new BeachModel("El Segundo Beach", "Grand Ave & Vista Del Mar Blvd\nEl Segundo, CA 90245", "6am - 10PM", 33.91381164523799, -118.42774427977326, parkingLots, null, restaurants));

//        FirebaseDatabase root;
//        DatabaseReference reference;
//        root = FirebaseDatabase.getInstance();
//
//        reference = root.getReference("beaches");
        System.out.println("PRINTING FROM CREATEDATA()");
        for (BeachModel testBeach : beaches) {
            // reference.child(testBeach.getName()).setValue(testBeach);
            System.out.println(testBeach.getName());
            for (int j=0; j<testBeach.getRestaruants().size(); j++) {
                System.out.println(testBeach.getRestaruants().get(j).getRestaurantName());
            }
        }
    }

}