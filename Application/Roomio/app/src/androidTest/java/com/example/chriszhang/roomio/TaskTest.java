package com.example.chriszhang.roomio;

import android.support.test.rule.ActivityTestRule;


import com.example.chriszhang.roomio.activities.HouseTasksActivity;

import org.junit.Rule;

public class TaskTest {

    @Rule
    public ActivityTestRule<HouseTasksActivity> rule =
            new ActivityTestRule<>(HouseTasksActivity.class);
}
