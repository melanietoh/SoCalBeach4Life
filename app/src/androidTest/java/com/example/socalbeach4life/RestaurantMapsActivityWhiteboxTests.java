package com.example.socalbeach4life;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.google.android.gms.common.data.DataBufferUtils.hasData;
import static org.hamcrest.CoreMatchers.containsString;
import static java.util.EnumSet.allOf;

public class RestaurantMapsActivityWhiteboxTests {
    @Rule
    public ActivityScenarioRule<BeachMapsActivity> activityScenarioRule
            = new ActivityScenarioRule<>(BeachMapsActivity.class);
    @Rule
    public final ActivityTestRule<BeachMapsActivity> rule = new ActivityTestRule<>(BeachMapsActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @Test
    public void whitebox12_test() {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker1 = device.findObject(new UiSelector().descriptionContains("Playa Del Rey Beach"));
        try {
            marker1.click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onView(withId(R.id.selectView)).check(matches(withText(containsString("Select"))));
            onView(withId(R.id.selectView)).perform(click());
            // redirects to new page
            onView(withId(R.id.lotHeaderView)).check(matches(withText(containsString("Parking lots near"))));
            UiObject marker2 = device.findObject(new UiSelector().descriptionContains("AirGarage"));
            try {
                marker2.click();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                onView(withId(R.id.lotSelectView)).check(matches(withText(containsString("Select"))));
                onView(withId(R.id.lotSelectView)).perform(click()); // selecting parking lot
                onView(withId(R.id.nearbyRestaurants)).perform(click()); // redirects to RestaurantMapsActivity
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // onView(withId(R.id.headerView)).check(matches(withText(containsString("marker"))));
                UiObject marker3 = device.findObject(new UiSelector().descriptionContains("The Tripel"));
                try {
                    marker3.click();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    onView(withId(R.id.titleView)).check(matches(withText(containsString("Tripel"))));
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void whitebox13_test() {
        rule.launchActivity(new Intent());
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Playa Del Rey Beach"));
        try {
            marker.click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onView(withId(R.id.selectView)).check(matches(withText(containsString("Select"))));
            onView(withId(R.id.selectView)).perform(click());
            // redirects to new page
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            UiObject marker2 = device.findObject(new UiSelector().descriptionContains("AirGarage"));
            try {
                marker2.click();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                onView(withId(R.id.lotSelectView)).check(matches(withText(containsString("Select AirGarage"))));
                onView(withId(R.id.lotSelectView)).perform(click());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                onView(withId(R.id.nearbyRestaurants)).perform(click()); // clicking on "View Nearby Restaurants" button page
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                UiObject marker3 = device.findObject(new UiSelector().descriptionContains("The Tripel"));
                marker3.click();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                 onView(withId(R.id.yelpView)).perform(click());
                intended(toPackage("com.android.chrome"));
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
}
