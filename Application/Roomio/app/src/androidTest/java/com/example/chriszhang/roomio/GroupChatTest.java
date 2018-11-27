package com.example.chriszhang.roomio;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.example.chriszhang.roomio.activities.GroupChatActivity;
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

public class GroupChatTest {

    User test1 = new User("ABCD",
            "test",
            "test",
            "adsf",
            Optional.of("asdf"),
            Optional.of("asdf"));

    Group group = new Group("asdf",
            "ABCD",
            "asdf");

    @Rule
    public ActivityTestRule<GroupChatActivity> rule =
            new ActivityTestRule<>(GroupChatActivity.class);

    @Test
    public void testSendMessage(){
        State.createState(test1);
        State.setGroup(group);
        Espresso.onView(withId(R.id.enterMessageEditText)).perform(
                typeText("hello"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.sendButton)).perform(click());
        assert(group.getGroupChatMessages().get(0).getMessageContents().equals("Hello"));
    }

    @Test
    public void testEmptyMessage() {
        State.createState(test1);
        State.setGroup(group);
        State.setTestMode(true);
        Espresso.onView(withId(R.id.sendButton)).perform(click());
        assert (group.getGroupChatMessages().isEmpty());
    }
}
