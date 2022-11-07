package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BeachReviewActivity extends AppCompatActivity {
    /*
    Displays reviews for a beach.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beach_review);

        // Clickable logo -> Return to homepage
        ImageView homepageView = findViewById(R.id.logo);
        homepageView.setClickable(true);
        homepageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent switchToHomepageView = new Intent(BeachReviewActivity.this, HomepageActivity.class);
                startActivity(switchToHomepageView);
            }
        });
    }

    public void returnToBeach(View view) {
        Intent switchToBeachView = new Intent(BeachReviewActivity.this, BeachActivity.class);
        startActivity(switchToBeachView);
    }
}