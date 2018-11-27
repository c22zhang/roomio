package com.example.chriszhang.roomio;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.example.chriszhang.roomio.activities.MyTabsActivity;
import com.example.chriszhang.roomio.model.Group;
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

public class TabTest {

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
        group.addMember(test1);
        group.addMember(test2);
        Tab tab = new Tab("Hello",
                "ABCD",
                "abcd",
                "ABCD",
                "asdf",
                12.21);
        test1.addTab(tab);
        test2.addTab(tab);
        State.createState(test1);
        State.setGroup(group);
        State.setTestMode(true);
    }

    @Rule
    public ActivityTestRule<MyTabsActivity> rule =
            new ActivityTestRule<>(MyTabsActivity.class);

    @Test
    public void testTabListView() {
        Espresso.onData(anything()).
                inAdapterView(withId(R.id.tabList))
                .atPosition(0)
                .perform(click());
        Espresso.onView(withId(R.id.tabDetailDeleteButton)).perform(click());
        assert(test1.getMyTabs().isEmpty());
    }
}
