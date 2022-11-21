package com.example.socalbeach4life;

import android.app.Activity;
import android.content.Intent;

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

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.containsString;

import com.google.android.gms.maps.internal.IMapFragmentDelegate;

@RunWith(AndroidJUnit4.class)

public class whitebox5 {
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
    public void whitebox5_test() {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Playa Del Rey Beach"));
        try {
            marker.click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onView(withId(R.id.selectView)).check(matches(withText(containsString("Select Playa Del Rey Beach"))));
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whitebox6_test() {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Playa Del Rey Beach"));
        try {
            marker.click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onView(withId(R.id.selectView)).check(matches(withText(containsString("Select Playa Del Rey Beach"))));
            onView(withId(R.id.selectView)).perform(click());
            rule.launchActivity(new Intent());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            intended(hasComponent(ParkingLotMapsActivity.class.getName()));
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }

}
