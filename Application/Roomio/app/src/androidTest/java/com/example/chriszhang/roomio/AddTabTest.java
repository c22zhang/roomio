package com.example.chriszhang.roomio;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.chriszhang.roomio.activities.AddTabActivity;
import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.Tab;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class AddTabTest {

    Group testgroup = new Group(
            "1111",
            "test",
            "hello");

    User testUser = new User(
            "test",
            "test",
            "asdf",
            "abcd",
            Optional.of("1111"),
            Optional.of("1111"));

    User testAssignee = new User(
            "test2",
            "test2",
            "asdf2",
            "abcd2",
            Optional.<String>empty(),
            Optional.of("1111"));

    @Rule
    public ActivityTestRule<AddTabActivity> rule =
            new ActivityTestRule<>(AddTabActivity.class);

    @Before
    public void setUp(){
        State.createState(testUser);
        testgroup.addMember(testAssignee);
        State.setGroup(testgroup);
    }

    @Test
    public void testAddTab() {
        Espresso.onView(withId(R.id.amountEditText))
                .perform(typeText("12.11"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.tabAssigneeEditText))
                .perform(typeText("test2"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.tabReasonEditText))
                .perform(typeText("some reason"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.saveTabButton)).perform(click());

        Tab tab = testUser.getMyTabs().get(0);
        assert(tab.getAmount() == 12.11);
        assert(tab.getReason().equals("some reason"));
        assert(tab.getAssignerUserId().equals(testUser.getUserId()));
        assert(tab.getAssigneeUserId().equals(testAssignee.getUserId()));
        assert(testUser.getMyTabs().get(0).equals(tab));
    }

}
