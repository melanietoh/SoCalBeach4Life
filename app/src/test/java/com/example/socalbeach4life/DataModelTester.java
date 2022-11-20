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
        BeachModel beach = db.searchBeach("Venice Beach");
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
        BeachModel beach = db.searchBeach("Venice Beach");
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

    /** Test Case #8 */
    @Test
    public void yelpLinkVerification() {
        BeachModel beach = db.searchBeach("Venice Beach");
        RestaurantModel r1 = new RestaurantModel();
        RestaurantModel r2 = new RestaurantModel();

        for (int i = 0; i < beach.parkingLots.size(); i++) {
            if (beach.restaruants.get(i).restaurantName.equals("Tocaya - Venice")) {
                r1 = beach.restaruants.get(i);
            } else if (beach.restaruants.get(i).restaurantName.equals("High Rooftop Lounge")) {
                r2 = beach.restaruants.get(i);
            }
        }

        assertEquals("Restaurant 1 yelp link is matched", r1.yelpLink, "https://tocaya.com/menu/");
        assertEquals("Restaurant 2 yelp link is matched", r2.yelpLink, "https://www.yelp.com/biz/high-rooftop-lounge-venice?osq=high+rooftop+lounge");

    }

    /** Test Case #9 */
    @Test
    public void createTripTesting() {
        TripModel test = new TripModel("testid", "testdate", "testarrivaltime", "Venice Beach", new ParkingLotModel());
        assertTrue("Creating a trip associated with a user worked", db.addTrip(test));
    }

    /** Test Case #11 */
    @Test
    public void retrieveTripTesting() {
        TripModel test = new TripModel("testid", "testdate", "testarrivaltime", "Venice Beach", new ParkingLotModel());
        db.addTrip(test);
        assertTrue("Pulling trip associated with user matched", db.checkTrip(test));
    }

    /** Test Case #13 */
    @Test
    public void createFirstReviewTesting() {
        ReviewModel test = new ReviewModel("testid", "testuid", "Venice Beach", "testdisplayname", false, "testrating", 2.2);
        assertTrue("Creating first instance of review for a beach for a user works", db.addReview(test));
    }

    /** Test Case #12 */
    @Test
    public void retrieveUserReviewTesting() {
        ReviewModel test = new ReviewModel("testid", "testuid", "Venice Beach", "testdisplayname", false, "testrating", 2.2);
        db.addReview(test);
        assertTrue("Retrieving review associated with a user works", db.checkReview(test));
    }

    /** Test Case #14 */
    @Test
    public void createSecondReviewTesting() {
        ReviewModel test = new ReviewModel("testid", "testuid", "Venice Beach", "testdisplayname", false, "testrating", 2.2);
        db.addReview(test);
        ReviewModel test2 = new ReviewModel("newid", "testuid", "Venice Beach", "testdisplayname", false, "testrating", 2.2);
        db.addReview(test2);
        //search is done via id, and matched for duplicate based on beachname
        assertTrue("Second review added exists after overwrite", db.checkReview(test2));
        assertFalse("First review no longer exists after overwrite.", db.checkReview(test));
    }

    /** Test Case #10 */
    @Test
    public void verifyDynamicBeachRating() {
        BeachModel beach = db.searchBeach("Venice Beach");
        ReviewModel test = new ReviewModel("testid", "testuid", "Venice Beach", "testdisplayname", false, "testrating", 2.2);
        assertEquals("Beach with no reviews correctly returns 0",0.0, beach.calculateRating(), 0.1);
        db.addReview(test);

        assertEquals("First review correctly gives beach review value",2.2, beach.calculateRating(), 0.1);
        test = new ReviewModel("testid2", "testuid", "Venice Beach", "testdisplayname", false, "testrating", 5.0);
        db.addReview(test);
        assertEquals("Replacement review correctly gives new review value",5.0, beach.calculateRating(), 0.1);
        //placing a second unique review
        beach.reviews.put("second review value", new ReviewModel("testid3", "testuid", "Venice Beach", "testdisplayname", false, "testrating", 1.0));
        assertEquals("Second review adds gives correct beach rating average",3.0, beach.calculateRating(), 0.1);

    }

}
