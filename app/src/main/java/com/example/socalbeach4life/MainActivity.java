package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    /*
    Logging in. Button to redirect user to create account if needed.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Clickable signUpLabel
        final TextView signUpView = findViewById(R.id.signUpLabel);
        signUpView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent switchToSignUpView = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(switchToSignUpView);
            }
        });
    }

    public void logIn(View view) {
        // Retrieve user input
        EditText emailField = findViewById(R.id.emailField);
        EditText passwordField = findViewById(R.id.passwordField);
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        // Send to database to validate
        boolean isValidated = false;
        // isValidated = validateAccount(email, password);
        if(isValidated) { //
            Intent switchToHomepageView = new Intent(this, HomepageActivity.class);
            startActivity(switchToHomepageView);
        }
        else { // Log in failed error pop up + clear password field
            // Pop up -> Cate
            System.out.println(email + " " + password);
            passwordField.setText("");
        }
    }
}