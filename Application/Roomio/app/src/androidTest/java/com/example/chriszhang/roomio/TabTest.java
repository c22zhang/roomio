package com.example.chriszhang.roomio;

import android.support.test.rule.ActivityTestRule;

import com.example.chriszhang.roomio.activities.MyTabsActivity;

import org.junit.Rule;

public class TabTest {

    @Rule
    public ActivityTestRule<MyTabsActivity> rule =
            new ActivityTestRule<>(MyTabsActivity.class);
}
