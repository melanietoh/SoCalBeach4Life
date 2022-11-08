package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileReviewActivity extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

        // Setting user related info
        if (user != null) {
            // Name, email address, uid
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();

            TextView displayNameLabel = findViewById(R.id.displayNameLabel);
            displayNameLabel.setText(name);

            TextView numReviewsLabel = findViewById(R.id.numReviewsLabel);
//            numReviewsLabel.setText(); ArrayList.count
        }
    }

    public void savedTrips(View view) {
        Intent switchToSavedTripsView = new Intent(ProfileReviewActivity.this, ProfileActivity.class);
        startActivity(switchToSavedTripsView);
    }

    public void deleteReview(View view) {
        // Database call
        /**
         * Deletes reviews
         * @param reviewID id of review. stored in ReviewModel.getId
         * @param beachName name of beach. must match exactly
         */
        DatabaseHelper.deleteReview("1667887691369", "beach1");
    }
}