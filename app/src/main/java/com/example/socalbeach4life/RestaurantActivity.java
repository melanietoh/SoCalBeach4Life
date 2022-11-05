package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RestaurantActivity extends AppCompatActivity {
    /*
    Viewing restaurants with Google Maps API: selecting radius to narrow search,
    can view restaurant menu with click
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
    }
}