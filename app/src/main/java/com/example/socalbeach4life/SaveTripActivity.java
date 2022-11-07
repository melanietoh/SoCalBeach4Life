package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

public class SaveTripActivity extends AppCompatActivity {
    /*
    Specifying departure time/reviewing trip information only AFTER parking lot is selected.
    Redirects to Google Maps if Departure time = now, otherwise, stores information (?)
     */
    Button dateSelector, timeSelector;
    EditText dateField, timeField;
    private int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_trip);

        // Clickable logo -> Return to homepage
        ImageView homepageView = findViewById(R.id.logo);
        homepageView.setClickable(true);
        homepageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent switchToHomepageView = new Intent(SaveTripActivity.this, HomepageActivity.class);
                startActivity(switchToHomepageView);
            }
        });

        // Date and time selectors
        dateSelector = findViewById(R.id.dateSelector);
        timeSelector = findViewById(R.id.timeSelector);
        dateField = findViewById(R.id.dateField);
        timeField = findViewById(R.id.timeField);

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
        startActivity(switchToRestaurantView);
    }

    public void leaveReview(View view) {
        Intent switchToReviewView = new Intent(SaveTripActivity.this, CreateReviewActivity.class);
        startActivity(switchToReviewView);
    }
}