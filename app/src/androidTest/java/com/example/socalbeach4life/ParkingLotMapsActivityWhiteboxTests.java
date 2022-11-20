package com.example.socalbeach4life;
import android.app.Activity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.containsString;

public class ParkingLotMapsActivityWhiteboxTests {
//    @Rule
//    public ActivityScenarioRule<ParkingLotMapsActivity> activityScenarioRule
//            = new ActivityScenarioRule<>(ParkingLotMapsActivity.class);
    @Rule
    public ActivityScenarioRule<BeachMapsActivity> activityScenarioRule
            = new ActivityScenarioRule<>(BeachMapsActivity.class);
    @Test
    public void whitebox7_test() {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Bruce's Beach"));
        try {
            marker.click();
            // System.out.println("found marker for Bruce's Beach");
            onView(withId(R.id.selectView)).check(matches(withText(containsString("Select"))));
            onView(withId(R.id.selectView)).perform(click());
            // redirects to new page
            onView(withId(R.id.headerView)).check(matches(withText(containsString("Parking lots near"))));
            UiObject marker2 = device.findObject(new UiSelector().descriptionContains("Dune Park Parking"));
            try {
                // System.out.println("found parking lot marker");
                marker2.click();
                onView(withId(R.id.selectView)).check(matches(withText(containsString("Select"))));
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        // System.out.println("Done with Whitebox Test 7");
    }
}
