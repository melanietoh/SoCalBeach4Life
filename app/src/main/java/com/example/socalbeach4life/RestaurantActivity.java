package com.example.socalbeach4life;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

public class RestaurantActivity extends AppCompatActivity {
    /*
    Viewing restaurants with Google Maps API: selecting radius to narrow search,
    can view restaurant menu with click
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        // Clickable logo -> Return to homepage
        ImageView homepageView = findViewById(R.id.logo);
        homepageView.setClickable(true);
        homepageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent switchToHomepageView = new Intent(RestaurantActivity.this, HomepageActivity.class);
                startActivity(switchToHomepageView);
            }
        });
    }

    public void selectRadius(View view) {
        boolean selected = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.ft1000Button:
                if (selected)
                    // Database call
                    break;
            case R.id.ft2000Button:
                if (selected)
                    // Database call
                    break;
            case R.id.ft3000Button:
                if(selected)
                    // Database call
                    break;
        }
    }

    public void viewMenu(View view) {
        Intent switchToMenuView = new Intent(RestaurantActivity.this, RestaurantMenuActivity.class);
        startActivity(switchToMenuView);
    }
}