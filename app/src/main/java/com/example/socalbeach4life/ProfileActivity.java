package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Parcelable;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class ProfileActivity extends AppCompatActivity {
    /*
    Viewing user information, including saved trips, reviews (including delete functionality).
     */
    private boolean buttonOnePressed = true;
    private boolean buttonTwoPressed = false;
//    private ArrayList<Trip>;
//    private ArrayList<Review>;
    private ArrayList<String> arrayListOne;
    private ArrayList<String> arrayListTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("RUNNING PROFILE ACTIVITY");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // start on "Saved Trips"
        arrayListOne = new ArrayList<String>();
        arrayListTwo = new ArrayList<String>();
        arrayListOne.add("Trip 1");
        arrayListTwo.add("Review 1");
        Button b3 = (Button) findViewById(R.id.button3);
        b3.setOnClickListener(this::onClickButton);
//        Button b4 = (Button)findViewById(R.id.button4);
//        b4.setOnClickListener(this::onClick);
        // alertView("testing alert controller");
        // Initialize the SDK
        Places.initialize(getApplicationContext(), "AIzaSyBis7UdegqS1LDfzWWWOlwrYbo9W3eRQoU");
        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(this);
    }
//    private void alertView( String message ) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        dialog.setTitle( "Hello" )
//                .setIcon(R.drawable.car)
//                .setMessage(message)
//        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                //set what would happen when positive button is clicked
//                finish();
//            }
//        })
//     .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface dialoginterface, int i) {
//          dialoginterface.cancel();
//          }})
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialoginterface, int i) {
//                        // dialoginterface.cancel();
//                        finish();
//                    }
//                }).show();
//    }
    // display either ArrayList<Trip> or ArrayList<Review> depending on which button was clicked
    // hard part is dynamically figuring out TableView but it should be fine with Mel's code
    public void onClickButton(View v) {
        System.out.println("Button is being clicked");
        Button button = (Button)findViewById(v.getId());
        // based on button, set one to on and the other off
        button.setBackgroundColor(Color.RED);
        TextView tv = findViewById(R.id.testText);
        if (button.getId() == R.id.button3) {
            // set button 2 to false
            // dynamically show array
            // add text field to see if clicking on diff buttons works before fully implementing grid layout
            // text.
            tv.setText("Saved Trips Data");
        }
        else {
            // set button 1 to false
            // dynamically show array, OR JUST SHOW BOTH OUTSIDE OF IF/ELSE? idk yet tbh
            tv.setText("Reviews Data");
        }
    }
}