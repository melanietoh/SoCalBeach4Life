package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SaveTripActivity extends AppCompatActivity {
    /*
    Specifying departure time/reviewing trip information only AFTER parking lot is selected.
    Redirects to Google Maps if Departure time = now, otherwise, stores information (?)
     */
    Button dateSelector, timeSelector;
    EditText dateField, timeField;
    private int year, month, day, hour, minute;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String beachName, parkingLotName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_trip);
        Intent intent = getIntent();
        beachName = intent.getStringExtra("beachName");
        parkingLotName = intent.getStringExtra("parkingLot");
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

        // Setting trip information
        TextView beachName = findViewById(R.id.beachName);
//        beachName.setText(BeachModel.getName());
        TextView parkingLotName = findViewById(R.id.parkingLotLabel);
//        parkingLotName.setText(ParkingLotModel.getName());

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

    }

    public void viewNearbyRestaurants(View view) {
        Intent switchToRestaurantView = new Intent(SaveTripActivity.this, RestaurantActivity.class);
        switchToRestaurantView.putExtra("beachName", beachName);
        startActivity(switchToRestaurantView);
    }

    public void leaveReview(View view) {
        Intent switchToReviewView = new Intent(SaveTripActivity.this, CreateReviewActivity.class);
        startActivity(switchToReviewView);
    }
    
    public void departNowRedirect(View view) {
       // Uri uri = Uri.parse("http://www.google.com");
        // Uri uri = Uri.parse("https://maps.googleapis.com/maps/api/directions/json?origin=Disneyland&destination=Universal+Studios+Hollywood&key=AIzaSyCEnsK36FKyv44d_stqm4i0jwwBAIPS8zg");
        Uri uri = Uri.parse("https://www.google.com/maps/dir/34.0324863,-118.2819881/University+of+Southern+California,+Los+Angeles,+CA+90007/@34.0274191,-118.2883858,16z/data=!3m1!4b1!4m17!1m6!3m5!1s0x80c2c7e49c71a5ed:0xaa905a5bb427a2c4!2sUniversity+of+Southern+California!8m2!3d34.0223519!4d-118.285117!4m9!1m1!4e1!1m5!1m1!1s0x80c2c7e49c71a5ed:0xaa905a5bb427a2c4!2m2!1d-118.285117!2d34.0223519!3e2");
        // https://www.google.com/maps/dir/34.0324863,-118.2819881//@34.0325965,-118.3172156,13z/data=!4m5!4m4!1m1!4e1!1m0!3e2
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
                    break;
            case R.id.ft2000Button:
                if (selected)
                    // Database call
                    break;
            case R.id.ft3000Button:
                if(selected)
                    // Database call
                    break;
        }
    }
}