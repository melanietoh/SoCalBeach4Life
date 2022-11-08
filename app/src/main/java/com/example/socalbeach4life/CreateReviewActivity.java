package com.example.socalbeach4life;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
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

        // Send to database to create review
        DatabaseHelper.createReview(beachNameToSearch, rating, message, false);

        Intent switchToProfileReviewView = new Intent(this, ProfileReviewActivity.class);
        startActivity(switchToProfileReviewView);
    }

    public void goToProfileView(View view) {
        Intent switchToProfileView = new Intent(CreateReviewActivity.this, ProfileActivity.class);
        startActivity(switchToProfileView);
    }
}