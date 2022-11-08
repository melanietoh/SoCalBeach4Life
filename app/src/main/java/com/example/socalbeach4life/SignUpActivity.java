package com.example.socalbeach4life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

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
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful()) {
                    System.out.println(getApplicationContext() + "Registration successful!");
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    String displayName = displayNameField.getText().toString();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(displayName).build();

                    user.updateProfile(profileUpdates);

                    FirebaseDatabase root;
                    DatabaseReference reference;
                    root = FirebaseDatabase.getInstance();

                    UserModel newUser = new UserModel(user.getUid(),user.getEmail(),user.getDisplayName(), new HashMap<>(), new HashMap<>());
                    reference = root.getReference("users");
                    reference.child(newUser.getUid()).setValue(newUser);

                    // Go to homepage
                    Intent switchToHomepageView = new Intent(SignUpActivity.this, BeachMapsActivity.class);
                    startActivity(switchToHomepageView);
                }
                else {

                    // Registration failed
                    System.out.println(task.getException());
                    System.out.println(
                            getApplicationContext() +
                                    " Registration failed!!"
                                    + " Please try again later");
                    displayNameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                }
            }
        });
    }
}