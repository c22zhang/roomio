package com.example.chriszhang.roomio;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.example.chriszhang.roomio.activities.SignUpActivity;
import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class SignupTest {

    static User test1 = new User("ABCD",
            "test",
            "test",
            "adsf",
            Optional.of("asdf"),
            Optional.of("asdf"));

    static Group group = new Group("asdf",
            "ABCD",
            "asdf");

    @Rule
    public ActivityTestRule<SignUpActivity> rule =
            new ActivityTestRule<>(SignUpActivity.class);

    @Test
    public void testSignupEntries(){
        Espresso.onView(withId(R.id.nameEditText))
                .perform(typeText("asdf"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.emailEditText))
                .perform(typeText("asdfsdf"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.intendedUsernameEditText))
                .perform(typeText("sdfsdfa"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.intendedPasswordEditText))
                .perform(typeText("eeeeee"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.reenterEditText))
                .perform(typeText("eeeeee"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.intendedGroupEditText))
                .perform(typeText("aaa"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signUpActivityButton)).perform(click());
    }
}
