package com.example.socalbeach4life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful()) {
                    System.out.println(getApplicationContext() + "Registration successful!");

                    // if the user created intent to login activity
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    System.out.println(user.getDisplayName());
                    System.out.println(user.getEmail());
                    System.out.println(user.getUid());

                    Intent switchToHomepageView = new Intent(MainActivity.this, BeachMapsActivity.class);
                    startActivity(switchToHomepageView);
                }
                else {

                    // Registration failed
                    System.out.println(task.getException());
                    System.out.println(
                            getApplicationContext() +
                                    " Login failed!!"
                                    + " Please try again later");
                    passwordField.setText("");
                }
            }
        });
    }
}