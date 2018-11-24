package com.example.chriszhang.roomio;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.chriszhang.roomio.activities.EditGroupActivity;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class CreateGroupTest {

    User test1 = new User("ABCD",
            "test",
            "test",
            "adsf",
            Optional.<String>empty(),
            Optional.<String>empty());

    @Rule
    public ActivityTestRule<EditGroupActivity> rule =
            new ActivityTestRule<>(EditGroupActivity.class);

    @Test
    public void testCreateGroup(){
        State.createState(test1);
        Espresso.onView(withId(R.id.addEditText)).perform(
                typeText("Hello"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.saveButton)).perform(click());
        assert(State.getGroup().getGroupName().equals("Hello"));
        assert(State.getGroup().getMemberFromId("ABCD").equals(test1));
        assert(test1.getAdminedGroupId().get().equals(State.getGroup().getGroupId()));
        assert(test1.getHouseholdGroupId().get().equals(State.getGroup().getGroupId()));
    }
}
