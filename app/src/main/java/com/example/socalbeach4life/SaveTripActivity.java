package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SaveTripActivity extends AppCompatActivity {
    /*
    Specifying departure time/reviewing trip information only AFTER parking lot is selected.
    Redirects to Google Maps if Departure time = now, otherwise, stores information (?)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_trip);
    }
}