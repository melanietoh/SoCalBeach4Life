package com.example.socalbeach4life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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
            TextView displayNameLabel = findViewById(R.id.displayNameLabel);
            displayNameLabel.setText(name);

            FirebaseDatabase root = FirebaseDatabase.getInstance();

            root.getReference("users").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        //User retrieved
                        UserModel result = task.getResult().getValue(UserModel.class);
                        HashMap<String, ReviewModel> reviews = result.getReviews();

                        TextView numReviewsLabel = findViewById(R.id.numReviewsLabel);
                        numReviewsLabel.setText(reviews.size() + " reviews");
                    }
                }
            });
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