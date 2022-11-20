package com.example.socalbeach4life;

import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
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
import static androidx.test.espresso.intent.VerificationModes.times;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(AndroidJUnit4.class)

public class mainWhiteBox {
    @Rule
    public final ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    // Test case #4
    @Test
    public void loginRedirects() {
        onView(withId(R.id.emailField)).perform(typeText("melaniex@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.passwordField)).perform(typeText("hieric"), closeSoftKeyboard());
        onView(withId(R.id.logInButton)).perform(click());
        rule.launchActivity(new Intent());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasComponent(BeachMapsActivity.class.getName()));
    }

    // Test case #9
    @Test
    public void displayNameRedirects() {
        onView(withId(R.id.emailField)).perform(typeText("melaniex@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.passwordField)).perform(typeText("hieric"), closeSoftKeyboard());
        onView(withId(R.id.logInButton)).perform(click());
        rule.launchActivity(new Intent());

        // Beach selection map view
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Playa Del Rey Beach"));
        try {
            marker.click();
            Thread.sleep(1000);
            onView(withId(R.id.reviewView)).check(matches(withText(containsString("Rating"))));
            onView(withId(R.id.reviewView)).perform(click());

            // Beach review view
            Thread.sleep(1000);
            onView(withId(R.id.profileButton)).check(matches(withText(containsString("Melanie Toh"))));
            onView(withId(R.id.profileButton)).perform(click());
            Thread.sleep(1000);
        }
        catch (UiObjectNotFoundException ONFe) {
            ONFe.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        intended(hasComponent(ProfileActivity.class.getName()));
    }

    // Test case #10
    @Test
    public void logoRedirects() {
        onView(withId(R.id.emailField)).perform(typeText("melaniex@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.passwordField)).perform(typeText("hieric"), closeSoftKeyboard());
        onView(withId(R.id.logInButton)).perform(click());
        rule.launchActivity(new Intent());

        // Beach selection map view
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Playa Del Rey Beach"));
        try {
            marker.click();
            Thread.sleep(1000);
            onView(withId(R.id.reviewView)).check(matches(withText(containsString("Rating"))));
            onView(withId(R.id.reviewView)).perform(click());

            // Beach review view
            Thread.sleep(1000);
            onView(withId(R.id.logo)).perform(click());
            Thread.sleep(500);
        }
        catch (UiObjectNotFoundException ONFe) {
            ONFe.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        intended(hasComponent(BeachMapsActivity.class.getName()), times(2));
    }

    // Test case #15
    @Test
    public void logOutRedirects() {
        onView(withId(R.id.emailField)).perform(typeText("melaniex@usc.edu"), closeSoftKeyboard());
        onView(withId(R.id.passwordField)).perform(typeText("hieric"), closeSoftKeyboard());
        onView(withId(R.id.logInButton)).perform(click());
        rule.launchActivity(new Intent());

        // Beach selection map view
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Playa Del Rey Beach"));
        try {
            marker.click();
            Thread.sleep(1000);
            onView(withId(R.id.reviewView)).check(matches(withText(containsString("Rating"))));
            onView(withId(R.id.reviewView)).perform(click());

            // Beach review view
            intended(hasComponent(BeachReviewActivity.class.getName()));
            onView(withId(R.id.profileButton)).check(matches(withText(containsString("Melanie Toh"))));
            onView(withId(R.id.profileButton)).perform(click());

            // Profile view
            Thread.sleep(1000);
            intended(hasComponent(ProfileActivity.class.getName()));
            onView(withId(R.id.profileButton)).check(matches(withText(containsString("Log Out"))));
            onView(withId(R.id.textView)).perform(click());
            Thread.sleep(1000);
        }
        catch (UiObjectNotFoundException ONFe) {
            ONFe.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        intended(hasComponent(MainActivity.class.getName()));
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
}
