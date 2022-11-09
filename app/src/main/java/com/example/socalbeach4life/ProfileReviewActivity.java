package com.example.socalbeach4life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
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
                        HashMap<String, ReviewModel> reviewsHashMap = result.getReviews();
                        ArrayList<ReviewModel> reviews = new ArrayList<>(reviewsHashMap.values());

                        TextView numReviewsLabel = findViewById(R.id.numReviewsLabel);
                        numReviewsLabel.setText(reviews.size() + " reviews");
                        System.out.println("Number of reviews: " + reviews.size());

                        // No reviews
                        if(reviews.isEmpty()) {
                            TableLayout table = findViewById(R.id.tableLayout);
                            TableRow row1 = (TableRow) LayoutInflater.from(ProfileReviewActivity.this).inflate(R.layout.review_row1, null);
                            ((TextView)row1.findViewById(R.id.firstRowLabel)).setText("No reviews yet!");
                            ((RatingBar)row1.findViewById(R.id.firstRowRating)).setVisibility(View.GONE);
                            table.addView(row1);
                        }
                        // Dynamically allocate rows to display each review
                        else {
                            TableLayout table = findViewById(R.id.tableLayout);

                            for(int i=0; i<reviews.size(); i++) {
                                String beachName = reviews.get(i).getBeachName();
                                Float userRating = reviews.get(i).getRating().floatValue();
                                String message = reviews.get(i).getMessage();

                                TableRow row1 = (TableRow) LayoutInflater.from(ProfileReviewActivity.this).inflate(R.layout.review_row1, null);
                                ((TextView)row1.findViewById(R.id.firstRowLabel)).setText(beachName);
                                ((RatingBar)row1.findViewById(R.id.firstRowRating)).setRating(userRating);
                                table.addView(row1);

                                if(!message.isEmpty()) {
                                    TableRow row2 = (TableRow) LayoutInflater.from(ProfileReviewActivity.this).inflate(R.layout.review_row2, null);
                                    ((TextView)row2.findViewById(R.id.secondRowMessage)).setText(message);
                                    table.addView(row2);
                                }

                                TableRow row3 = (TableRow) LayoutInflater.from(ProfileReviewActivity.this).inflate(R.layout.review_row3, null);
                                ((Button)row3.findViewById(R.id.thirdRowButton)).setText("Delete review: " + beachName);
                                table.addView(row3);

                                TableRow divider = (TableRow) LayoutInflater.from(ProfileReviewActivity.this).inflate(R.layout.review_divider, null);
                                table.addView(divider);
                            }
                        }
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
        Button button = (Button)view;
        int length = button.getText().length();
        String beachName = (String) button.getText().subSequence(15, length);
        System.out.println("Deleting: " + beachName);
        DatabaseHelper.deleteReviewByBeachName(beachName);

        // Reload
        finish();
        startActivity(getIntent());
    }
}