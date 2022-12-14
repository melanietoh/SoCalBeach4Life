package com.example.socalbeach4life;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateReviewActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String beachNameToSearch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);

        // Clickable logo -> Return to homepage
        ImageView homepageView = findViewById(R.id.logo);
        homepageView.setClickable(true);
        homepageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent switchToHomepageView = new Intent(CreateReviewActivity.this, BeachMapsActivity.class);
                startActivity(switchToHomepageView);
            }
        });

        // Setting user related info
        if (user != null) {
            // Name, email address, uid
            String name = user.getDisplayName();
            TextView profileButton = findViewById(R.id.profileButton);
            profileButton.setText(name);
        }

        // Setting beach information
        Intent intent = getIntent();
        beachNameToSearch = intent.getStringExtra("beachName");
        TextView beachName = findViewById(R.id.beachName);
        beachName.setText(beachNameToSearch);
    }

    public void submitReview(View view) {
        // Retrieve user input
        EditText ratingField = findViewById(R.id.ratingField);
        EditText messageField = findViewById(R.id.messageField);
        Double rating = Double.parseDouble(ratingField.getText().toString());
        String message = messageField.getText().toString();
        SwitchCompat anonymousSwitch = findViewById(R.id.anonymousSwitch);
        Boolean isAnonymous = anonymousSwitch.isChecked();

        if (rating > 5.0)
            rating = 5.0;
        else if (rating < 0)
            rating = 0.0;

        // Send to database to create review
        DatabaseHelper.createReview(beachNameToSearch, rating, message, isAnonymous);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent switchToProfileReviewView = new Intent(CreateReviewActivity.this, ProfileReviewActivity.class);
                startActivity(switchToProfileReviewView);            }
        }, 200);

    }

    public void goToProfileView(View view) {
        Intent switchToProfileView = new Intent(CreateReviewActivity.this, ProfileActivity.class);
        startActivity(switchToProfileView);
    }
}