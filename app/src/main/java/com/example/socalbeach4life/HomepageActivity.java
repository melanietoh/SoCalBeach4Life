package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomepageActivity extends AppCompatActivity {
    /*
    Viewing nearby beaches. Redirects to BeachReviewActivity to view Beach's reviews.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }
}