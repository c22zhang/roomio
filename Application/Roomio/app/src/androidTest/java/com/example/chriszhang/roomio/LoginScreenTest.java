package com.example.chriszhang.roomio;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.chriszhang.roomio.activities.LoginActivity;
import com.example.chriszhang.roomio.state.State;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class LoginScreenTest {

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void testSignInButton() {
        Espresso.onView(withId(R.id.signInButton)).perform(click());

        //User is instantiated in state after pressing button
        assert(State.getCurrentUser().getUsername().equals("Test test"));
    }

    @Test
    public void testSignUpButton(){
    }
}
