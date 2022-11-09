package com.example.socalbeach4life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

import java.util.HashMap;

public class BeachReviewActivity extends AppCompatActivity {
    /*
    Displays reviews for a beach.
     */

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beach_review);

        // Clickable logo -> Return to homepage
        ImageView homepageView = findViewById(R.id.logo);
        homepageView.setClickable(true);
        homepageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent switchToHomepageView = new Intent(BeachReviewActivity.this, BeachMapsActivity.class);
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

        // Loading reviews for selected beach
        Intent intent = getIntent();
        String beachNameToSearch = intent.getStringExtra("beachName");
        TextView beachName = findViewById(R.id.beachName);
        beachName.setText(beachNameToSearch);

        FirebaseDatabase root = FirebaseDatabase.getInstance();
        root.getReference("beaches").child(beachNameToSearch).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("Firebase", "Error getting data", task.getException());
                }
                else {
                    BeachModel beachResult = task.getResult().getValue(BeachModel.class);
//                    System.out.println(beachResult);
                    // No reviews
                    HashMap<String, ReviewModel> reviews = beachResult.getReviews();
                    if(reviews.isEmpty()) {
                        TextView rating = findViewById(R.id.rating);
                        rating.setText("Rating: N/A");

                        TableLayout table = findViewById(R.id.tableLayout);
                        TableRow row1 = (TableRow) LayoutInflater.from(BeachReviewActivity.this).inflate(R.layout.review_row1, null);
                        ((TextView)row1.findViewById(R.id.firstRowLabel)).setText("No reviews yet!");
                        ((RatingBar)row1.findViewById(R.id.firstRowRating)).setVisibility(View.GONE);
                        table.addView(row1);
                    }
                    // Display reviews
                    else {
                        TableLayout table = findViewById(R.id.tableLayout);
                        TextView rating = findViewById(R.id.rating);
                        rating.setText("Rating: " + beachResult.calculateRating());

                        for(int i=0; i<reviews.size(); i++) {
                            String displayName = reviews.get(i).getDisplayName();
                            if(reviews.get(i).isAnonymous()) {
                                displayName = "Anonymous user";
                            }
                            Float userRating = reviews.get(i).getRating().floatValue();
                            String message = reviews.get(i).getMessage();

                            TableRow row1 = (TableRow) LayoutInflater.from(BeachReviewActivity.this).inflate(R.layout.review_row1, null);
                            ((TextView)row1.findViewById(R.id.firstRowLabel)).setText(displayName);
                            ((RatingBar)row1.findViewById(R.id.firstRowRating)).setRating(userRating);
                            table.addView(row1);

                            if(!message.isEmpty()) {
                                TableRow row2 = (TableRow) LayoutInflater.from(BeachReviewActivity.this).inflate(R.layout.review_row2, null);
                                ((TextView)row2.findViewById(R.id.secondRowMessage)).setText(message);
                                table.addView(row2);
                            }

                            TableRow divider = (TableRow) LayoutInflater.from(BeachReviewActivity.this).inflate(R.layout.review_divider, null);
                            table.addView(divider);
                        }
                    }
                }
            }
        });
    }

    public void returnToBeach(View view) {
        Intent switchToBeachView = new Intent(BeachReviewActivity.this, BeachMapsActivity.class);
        startActivity(switchToBeachView);
    }

    public void goToProfileView(View view) {
        Intent switchToProfileView = new Intent(BeachReviewActivity.this, ProfileActivity.class);
        startActivity(switchToProfileView);
    }
}