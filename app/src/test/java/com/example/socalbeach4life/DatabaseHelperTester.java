package com.example.socalbeach4life;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseHelperTester {

    /** Setup method executed before each test */
    @Before
    public void setup() {
        //no setup needed for static abstract method
    }
    /** JUnit test methods to test isqrt. */
    @Test
    public void testWalkingRoute() {
        String output = "https://www.google.com/maps?f=d&saddr=2600+Highland+AveManhattan+Beach,+CA,+USA&daddr=2114+Highland+Ave+Manhattan+Beach,+CA,+USA&dirflg=w";
        String start = "2600 Highland AveManhattan Beach, CA 90266";
        String destination = "2114 Highland Ave Manhattan Beach, CA 90266";
        assertEquals("Walking route maps link", output, DatabaseHelper.generateWalkingRoute(start,destination));
    }

}
