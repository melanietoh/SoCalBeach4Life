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
            String email = user.getEmail();
            String uid = user.getUid();
            TextView profileButton = findViewById(R.id.profileButton);
            profileButton.setText(name);
        }

    }

    public void submitReview(View view) {
        // Retrieve user input
        EditText ratingField = findViewById(R.id.ratingField);
        EditText messageField = findViewById(R.id.messageField);
        String rating = ratingField.getText().toString();
        String message = messageField.getText().toString();

        // Send to database to create review
        boolean hasCreated = false;
        // hasCreated = createAccount(displayName, email, password);
        if(hasCreated) { //
            Intent switchToHomepageView = new Intent(this, HomepageActivity.class);
            startActivity(switchToHomepageView);
        }
    }
}