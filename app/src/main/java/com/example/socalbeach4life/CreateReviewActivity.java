package com.example.socalbeach4life;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.socalbeach4life.databinding.ActivityCreateReviewBinding;

public class CreateReviewActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
private ActivityCreateReviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);

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