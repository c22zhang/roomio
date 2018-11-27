package com.example.chriszhang.roomio;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.example.chriszhang.roomio.activities.NotificationsActivity;
import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.Jsonable;
import com.example.chriszhang.roomio.model.Notification;
import com.example.chriszhang.roomio.model.Tab;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

public class NotificationTest {

    static User test1 = new User("ABCD",
            "test",
            "test",
            "adsf",
            Optional.of("asdf"),
            Optional.of("asdf"));

    static User test2 = new User("abcd",
            "test2",
            "test2",
            "adsf2",
            Optional.<String>empty(),
            Optional.of("asdf"));

    static Group group = new Group("asdf",
            "ABCD",
            "asdf");

    @BeforeClass
    public static void setUp(){
        State.getStateInstance().logout();
        Tab tab = new Tab("Hello",
                "ABCD",
                "abcd",
                "ABCD",
                "asdf",
                12.21);;
        group.addMember(test1);
        group.addMember(test2);
        test1.addTab(tab);
        test2.addTab(tab);
        test1.addNotification(new Notification("asdf",
                "test2 wants to clear a tab",
                "ABCD",
                "abcd",
                Notification.Type.CLEAR_TAB_REQ,
                Optional.<Jsonable>of(tab)));
        State.createState(test1);
        State.setGroup(group);
        State.setTestMode(true);
    }

    @Rule
    public ActivityTestRule<NotificationsActivity> rule =
            new ActivityTestRule<>(NotificationsActivity.class);

    @Test
    public void testClearTab(){
        Espresso.onData(anything()).
                inAdapterView(withId(R.id.personalNotificationList))
                .atPosition(0)
                .perform(click());
        Espresso.onView(withId(R.id.clearButton)).perform(click());
        assert(test1.getMyTabs().isEmpty());
        assert(test2.getMyTabs().isEmpty());
        assert(test1.getNotifications().isEmpty());
        assert(!test2.getNotifications().isEmpty());
    }
}
