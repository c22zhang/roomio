package com.example.chriszhang.roomio;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;


import com.example.chriszhang.roomio.activities.HouseTasksActivity;
import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.Task;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

public class TaskTest {

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
        group.addMember(test1);
        group.addMember(test2);
        group.addTask(new Task("Hello",
                "ABCD",
                "abcd",
                "asdf",
                "asdf",
                "12/11"));
        State.createState(test1);
        State.setGroup(group);
        State.setTestMode(true);
    }
    @Rule
    public ActivityTestRule<HouseTasksActivity> rule =
            new ActivityTestRule<>(HouseTasksActivity.class);

    @Test
    public void testListViewItem() throws Exception {
        Espresso.onData(anything()).
                inAdapterView(withId(R.id.taskList))
                .atPosition(0)
                .perform(click());
        Espresso.onView(withId(R.id.taskDetailDeleteButton)).perform(click());
        assert(group.getTasks().isEmpty());
    }
}
