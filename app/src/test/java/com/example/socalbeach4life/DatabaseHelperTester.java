package com.example.socalbeach4life;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import android.text.style.TtsSpan;

public class DatabaseHelperTester {

    /** Setup method executed before each test */
    @Before
    public void setup() {
        //no setup needed for static abstract method
    }
    /** Test Case #3 */
    @Test
    public void testWalkingRoute() {
        String output = "https://www.google.com/maps?f=d&saddr=2600+Highland+AveManhattan+Beach,+CA,+USA&daddr=2114+Highland+Ave+Manhattan+Beach,+CA,+USA&dirflg=w";
        String start = "2600 Highland AveManhattan Beach, CA 90266";
        String destination = "2114 Highland Ave Manhattan Beach, CA 90266";
        assertEquals("Walking route maps link", output, DatabaseHelper.generateWalkingRoute(start,destination));
    }

    /** Test Case #15 */
    @Test
    public void testAddressParsing() {
        String address1 = "2600 Highland AveManhattan Beach, CA 90266";
        String address2 = "2114 Highland Ave Manhattan Beach, CA 90266";
        assertEquals("Address 1 parsing", "2600+Highland+AveManhattan+Beach,+CA,+USA", DatabaseHelper.parseAddress(address1));
        assertEquals("Address 2 parsing", "2114+Highland+Ave+Manhattan+Beach,+CA,+USA", DatabaseHelper.parseAddress(address2));
    }

    @Test
    public void testCoordParsing() {
        String address1 = "2600 Highland Ave Manhattan Beach, CA 90266";
        String address2 = "2114 Highland Ave Manhattan Beach, CA 90266";
        assertFalse("address 1 is not waypoint", DatabaseHelper.isWaypoint(address1));
        assertFalse("address 2 is not waypoint", DatabaseHelper.isWaypoint(address2));
        String waypoint1 = "43.12345,-76.12345";
        String waypoint2 = "0,0";
        assertTrue("waypoint 1", DatabaseHelper.isWaypoint(waypoint1));
        assertTrue("waypoint 2", DatabaseHelper.isWaypoint(waypoint2));

    }

    @Test
    public void testMultiStop() {
        String address1 = "2600 Highland Ave Manhattan Beach, CA 90266";
        String waypoint1 = "33.848602407888734,-118.3976579056785";
        System.out.println(DatabaseHelper.generateTwoPartRouteWithCustomStart(DatabaseHelper.USCADDRESS, address1,waypoint1));
        System.out.println(DatabaseHelper.generateTwoPartRouteFromCurrentLocation(address1, waypoint1));
    }

}
