package com.example.chriszhang.roomio;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.chriszhang.roomio.activities.AddTaskActivity;
import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.Task;
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
public class AddTaskTest {

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
    public ActivityTestRule<AddTaskActivity> rule =
            new ActivityTestRule<>(AddTaskActivity.class);

    @Before
    public void setUp(){
        State.createState(testUser);
        testgroup.addMember(testAssignee);
        State.setGroup(testgroup);
    }

    @Test
    public void testAddTab() {
        Espresso.onView(withId(R.id.taskDescriptionEditText))
                .perform(typeText("asdf"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.taskAssigneeEditText))
                .perform(typeText("test2"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.taskNameEditText))
                .perform(typeText("hello world"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.saveTaskButton))
                .perform(click());

        Task task = State.getGroup().getTasks().get(0);
        assert(task.getDescription().equals("asdf"));
        assert(task.getTaskName().equals("hello world"));
        assert(task.getAssigneeUserId().equals("test2"));
        assert(task.getAssignerUserId().equals("test"));
    }


}
