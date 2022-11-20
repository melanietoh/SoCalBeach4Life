package com.example.socalbeach4life;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.lang.Thread.sleep;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class signUpWhiteBox {
    @Rule
    public final ActivityTestRule<SignUpActivity> rule = new ActivityTestRule<>(SignUpActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    // Test case #1
    @Test
    public void emailValidation() {
        onView(withId(R.id.displayNameField)).perform(typeText("Melanie"));
        onView(withId(R.id.emailField)).perform(typeText("hihi"), closeSoftKeyboard());
        onView(withId(R.id.passwordField)).perform(typeText("hieric"), closeSoftKeyboard());
        onView(withId(R.id.logInButton)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.emailField)).check(matches(withText(""))); // We clear all fields when sign up is unsuccessful
    }

    // Test case #2
    @Test
    public void passwordValidation() {
        onView(withId(R.id.displayNameField)).perform(typeText("Melanie"));
        onView(withId(R.id.emailField)).perform(typeText("melanietoh@hotmail.com"), closeSoftKeyboard());
        onView(withId(R.id.passwordField)).perform(typeText("hi"), closeSoftKeyboard());
        onView(withId(R.id.logInButton)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.displayNameField)).check(matches(withText(""))); // We clear all fields when sign up is unsuccessful
    }

    // Test case #3
    @Test
    public void signUpRedirects() {
        onView(withId(R.id.displayNameField)).perform(typeText("Melanie"));
        onView(withId(R.id.emailField)).perform(typeText("melanietoh@hotmail.com"), closeSoftKeyboard());
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

    @After
    public void tearDown() throws Exception {
        Intents.release();
    }
}
