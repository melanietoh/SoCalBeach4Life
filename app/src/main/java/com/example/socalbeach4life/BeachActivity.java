package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

        // Clickable logo -> Return to homepage
        ImageView homepageView = findViewById(R.id.logo);
        homepageView.setClickable(true);
        homepageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent switchToHomepageView = new Intent(BeachActivity.this, HomepageActivity.class);
                startActivity(switchToHomepageView);
            }
        });
    }
}