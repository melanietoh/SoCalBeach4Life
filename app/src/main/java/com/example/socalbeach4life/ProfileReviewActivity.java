package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class ProfileReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_review);

        // Clickable logo -> Return to homepage
        ImageView homepageView = findViewById(R.id.logo);
        homepageView.setClickable(true);
        homepageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent switchToHomepageView = new Intent(ProfileReviewActivity.this, BeachMapsActivity.class);
                startActivity(switchToHomepageView);
            }
        });
    }

    public void savedTrips(View view) {
        Intent switchToSavedTripsView = new Intent(ProfileReviewActivity.this, ProfileActivity.class);
        startActivity(switchToSavedTripsView);
    }

    public void deleteReview(View view) {
        // Database call
    }
}