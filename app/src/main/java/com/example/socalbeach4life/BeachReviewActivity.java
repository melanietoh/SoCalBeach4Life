package com.example.socalbeach4life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

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
            String email = user.getEmail();
            String uid = user.getUid();
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
                    System.out.println(beachResult);
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