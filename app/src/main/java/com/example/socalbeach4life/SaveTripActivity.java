package com.example.socalbeach4life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SaveTripActivity extends AppCompatActivity {
    /*
    Specifying departure time/reviewing trip information only AFTER parking lot is selected.
    Redirects to Google Maps if De1233parture time = now, otherwise, stores information (?)
     */
    Button dateSelector, timeSelector, saveTripButton;
    EditText dateField, timeField;
    private int year, month, day, hour, minute;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String beachName, parkingLotName;
    private int radius = 0;
    // private String link = "https://www.google.com/maps/dir/34.0324863,-118.2819881/University+of+Southern+California,+Los+Angeles,+CA+90007/@34.0274191,-118.2883858,16z/data=!3m1!4b1!4m17!1m6!3m5!1s0x80c2c7e49c71a5ed:0xaa905a5bb427a2c4!2sUniversity+of+Southern+California!8m2!3d34.0223519!4d-118.285117!4m9!1m1!4e1!1m5!1m1!1s0x80c2c7e49c71a5ed:0xaa905a5bb427a2c4!2m2!1d-118.285117!2d34.0223519!3e2";
    private String link = "";
    private String restaurantName = "***";
    private Boolean enableSaveTrip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_trip);
        Intent intent = getIntent();
        beachName = intent.getStringExtra("beachName");
        parkingLotName = intent.getStringExtra("parkingLot");
        if (intent.hasExtra("restaurantName")) {
            restaurantName = intent.getStringExtra("restaurantName");
        }
        TextView beachNameHeader = findViewById(R.id.beachName);
        beachNameHeader.setText(beachName);
        TextView lotNameHeader = findViewById(R.id.parkingLotLabel);
        lotNameHeader.setText(parkingLotName);

        // Clickable logo -> Return to homepage
        ImageView homepageView = findViewById(R.id.logo);
        homepageView.setClickable(true);
        homepageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent switchToHomepageView = new Intent(SaveTripActivity.this, BeachMapsActivity.class);
                startActivity(switchToHomepageView);
            }
        });

        // Setting user related info
        if (user != null) {
            // Name, email address, uid
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();
            TextView profileButton = findViewById(R.id.profileButton);
            profileButton.setText(name);
        }

        // Date and time selectors
        dateSelector = findViewById(R.id.dateSelector);
        timeSelector = findViewById(R.id.timeSelector);
        dateField = findViewById(R.id.dateField);
        timeField = findViewById(R.id.timeField);
        View openMapsNow = findViewById(R.id.googleMapsNow);
        openMapsNow.setOnClickListener(this::departNowRedirect);

        dateSelector.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get Current Date
                final Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(SaveTripActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateField.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        timeSelector.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get Current Time
                final Calendar calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(SaveTripActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timeField.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        root.getReference("beaches").child(beachName).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    BeachModel beachResult = task.getResult().getValue(BeachModel.class);
                    System.out.println(beachResult);

                    // Search for parking lot
                    ArrayList<ParkingLotModel> parkingLots = beachResult.getParkingLots();
                    for(int i=0; i<parkingLots.size(); i++) {
                        if (parkingLots.get(i).getName().equals(parkingLotName)) {
                            System.out.println("FOUND PARKING LOT IN ONCREATE()");
                            link = DatabaseHelper.generateRouteFromUSC(parkingLots.get(i).getAddress());
                            System.out.println("link is: " + link);
                            //DatabaseHelper.createTrip(dateAndTime, arrivalDateAndTime, DatabaseHelper.generateRouteFromUSC(parkingLots.get(i).getAddress()), beachName, parkingLots.get(i));
                            break;
                        }
                    }
                }
            }
        });
    }

    public void viewNearbyRestaurants(View view) {
        Intent switchToRestaurant = new Intent(this, RestaurantMapsActivity.class);
        switchToRestaurant.putExtra("beachName", beachName);
        switchToRestaurant.putExtra("radius", radius);
        switchToRestaurant.putExtra("selectedLot", parkingLotName);
        startActivity(switchToRestaurant);
    }

    public void leaveReview(View view) {
        Intent switchToReviewView = new Intent(this, CreateReviewActivity.class);
        switchToReviewView.putExtra("beachName", beachName);
        startActivity(switchToReviewView);
    }
    
    public void departNowRedirect(View view) {
        // System.out.println("clicked on google maps button");
        // System.out.println("2nd link is: " + link);
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void selectRadius(View view) {
        boolean selected = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.ft1000Button:
                if (selected)
                    // Database call
                    radius = 1000;
                    break;
            case R.id.ft2000Button:
                if (selected)
                    // Database call
                    radius = 2000;
                    break;
            case R.id.ft3000Button:
                if(selected)
                    // Database call
                    radius = 3000;
                    break;
        }
    }

    public void goToProfileView(View view) {
        Intent switchToProfileView = new Intent(SaveTripActivity.this, ProfileActivity.class);
        startActivity(switchToProfileView);
    }

    public void saveTrip(View view) {
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        root.getReference("beaches").child(beachName).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    BeachModel beachResult = task.getResult().getValue(BeachModel.class);
                    System.out.println(beachResult);

                    if(!timeField.getText().toString().matches("") && !dateField.getText().toString().matches("")) {
                        enableSaveTrip = true;
                        String dateAndTime = dateField.getText() + " " + timeField.getText();
                        String arrivalDateAndTime = dateField.getText() + " ";
                        String timeFieldText = "" + timeField.getText();
                        if (timeFieldText.length() < 5) {
                            timeFieldText = "0" + timeFieldText;
                        }
                        int arrivalHours = Integer.parseInt(timeFieldText.substring(0,2));
                        int arrivalMinutes = Integer.parseInt(timeFieldText.substring(3,5));
                        arrivalMinutes += 25 + (int)(12*Math.random());

                        if (arrivalMinutes >= 60) {
                            arrivalMinutes -= 60;
                            arrivalHours +=1;
                            //extra day edge case not checked
                        }
                        if (arrivalHours < 10) {
                            arrivalDateAndTime += "0";
                        }
                        arrivalDateAndTime += arrivalHours + ":";
                        if (arrivalMinutes < 10) {
                            arrivalDateAndTime += "0";
                        }
                        arrivalDateAndTime += arrivalMinutes;

                        // Search for parking lot
                        ArrayList<ParkingLotModel> parkingLots = beachResult.getParkingLots();
                        for(int i=0; i<parkingLots.size(); i++) {
                            if (parkingLots.get(i).getName().equals(parkingLotName)) {
                                System.out.println("FOUND PARKING LOT");
                                link = DatabaseHelper.generateRouteFromUSC(parkingLots.get(i).getAddress());
                                System.out.println("link is: " + link);
                                DatabaseHelper.createTrip(dateAndTime,arrivalDateAndTime, DatabaseHelper.generateRouteFromUSC(parkingLots.get(i).getAddress()), beachName, parkingLots.get(i), restaurantName);
                                break;
                            }
                        }
                    }
                }
            }
        });

        // Redirect to profile view once done
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(enableSaveTrip) {
                    //time delay to allow database to update
                    Intent switchToProfileView = new Intent(SaveTripActivity.this, ProfileActivity.class);
                    startActivity(switchToProfileView);
                }

            }
        }, 200);

    }
}