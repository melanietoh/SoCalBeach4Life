package com.example.socalbeach4life;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;

public class DataModelTester {
    /** Setup method executed before each test */
    DatabaseModeler db;

    @Before
    public void setup() {
        db = new DatabaseModeler();
    }
    /** Test Case #4 */
    @Test
    public void testBeachSelection() {
        boolean beach1Exists = false;
        boolean beach2Exists = false;
        boolean beach3Exists = false;
        for (int i = 0; i < db.getBeaches().size(); i++) {
            if (db.getBeaches().get(i).name.equals("Bruce's Beach")) {
                beach1Exists = true;
            } else if (db.getBeaches().get(i).name.equals("Venice Beach")) {
                beach2Exists = true;
            } else if (db.getBeaches().get(i).name.equals("this should not match")) {
                beach3Exists = true;
            }
        }

        assertTrue("Beach 1 selected", beach1Exists);
        assertTrue("Beach 2 selected", beach2Exists);
        assertFalse("Beach 3 doesn't exist", beach3Exists);
    }

    /** Test Case #5 */
    @Test
    public void testParkingLotSelection() {
        BeachModel beach = db.getBeaches().get(0);
        boolean lot1Exists = false;
        boolean lot2Exists = false;
        boolean lot3Exists = false;

        for (int i = 0; i < beach.parkingLots.size(); i++) {
            if (beach.parkingLots.get(i).name.equals("Lot 9")) {
                lot1Exists = true;
            } else if (beach.parkingLots.get(i).name.equals("Parking")) {
                lot2Exists = true;
            } else if (beach.parkingLots.get(i).name.equals("invalid parking lot name")) {
                lot3Exists = true;
            }
        }

        assertTrue("Parking Lot 1 selected", lot1Exists);
        assertTrue("Parking Lot 2 selected", lot2Exists);
        assertFalse("Parking Lot 3 doesn't exist", lot3Exists);
    }

    /** Test Case #7 */
    @Test
    public void testRestaurantSelection() {
        BeachModel beach = db.getBeaches().get(0);
        boolean rest1Exists = false;
        boolean rest2Exists = false;
        boolean rest3Exists = false;

        for (int i = 0; i < beach.parkingLots.size(); i++) {
            if (beach.restaruants.get(i).restaurantName.equals("Tocaya - Venice")) {
                rest1Exists = true;
            } else if (beach.restaruants.get(i).restaurantName.equals("High Rooftop Lounge")) {
                rest2Exists = true;
            } else if (beach.restaruants.get(i).restaurantName.equals("invalid restaurant name")) {
                rest3Exists = true;
            }
        }

        assertTrue("Restaurant 1 selected", rest1Exists);
        assertTrue("Restaurant 2 selected", rest2Exists);
        assertFalse("Restaurant 3 doesn't exist", rest3Exists);
    }


}
