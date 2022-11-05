package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BeachActivity extends AppCompatActivity {
    /*
    Displays parking lots close to a beach. Beach must be selected to reach this page.
    Once a user selects a parking lot, they will be redirected to SaveTripActivity, which allows
    the user to specify more about their trip (set departure/review trip information).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beach);
    }
}