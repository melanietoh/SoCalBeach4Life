package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SignUpActivity extends AppCompatActivity {
    /*
    Creating new user account.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Clickable signUpLabel
        final TextView logInView = findViewById(R.id.logInLabel);
        logInView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent switchToLogInView = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(switchToLogInView);
            }
        });
    }

    public void signUp(View view) {
        // Retrieve user input
        EditText displayNameField = findViewById(R.id.displayNameField);
        EditText emailField = findViewById(R.id.emailField);
        EditText passwordField = findViewById(R.id.passwordField);
        String displayName = displayNameField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        // Send to database to create account
        boolean hasCreated = false;
        // hasCreated = createAccount(displayName, email, password);
        if(hasCreated) { //
            Intent switchToHomepageView = new Intent(SignUpActivity.this, HomepageActivity.class);
            startActivity(switchToHomepageView);
        }
        else { // Sign up failed error pop up + clear fields
            // Pop up -> Cate
            displayNameField.setText("");
            emailField.setText("");
            passwordField.setText("");
        }
    }
}