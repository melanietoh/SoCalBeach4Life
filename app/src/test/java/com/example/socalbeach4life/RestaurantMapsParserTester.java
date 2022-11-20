package com.example.socalbeach4life;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class RestaurantMapsParserTester {
    /** Setup method executed before each test */
    DatabaseModeler db;

    @Before
    public void setup() {
        db = new DatabaseModeler();
    }

    /** Test Case #6 */
    @Test
    public void testRestaurantRadius() {
        ArrayList<RestaurantModel> restaurants = db.searchBeach("Bruce's Beach").getRestaruants();
        ArrayList<RestaurantModel> onethousand = new ArrayList<>();
        ArrayList<RestaurantModel> twothousand = new ArrayList<>();
        ArrayList<RestaurantModel> threethousand = new ArrayList<>();
        for (int i=0; i<restaurants.size(); i++) {
            // 1k radius
            if (restaurants.get(i).getDist()*5280 < 1000) {
                onethousand.add(restaurants.get(i));
            }
            // 2k radius
            if (restaurants.get(i).getDist()*5280 < 2000) {
                twothousand.add(restaurants.get(i));
            }
            // 3k radius
            if (restaurants.get(i).getDist()*5280 < 3000) {
                threethousand.add(restaurants.get(i));
            }
        }
        System.out.println("xxxxx" + onethousand.size() + " "+ twothousand.size() + " " + threethousand.size());

        assertEquals("5 Restaurants in total", 5, restaurants.size());
        assertEquals("0 restaurants within 1k radius", 0, onethousand.size());
        assertEquals("1 restaurants within 1k radius", 1, twothousand.size());
        assertEquals("3 restaurants within 1k radius", 3, threethousand.size());

    }
}
