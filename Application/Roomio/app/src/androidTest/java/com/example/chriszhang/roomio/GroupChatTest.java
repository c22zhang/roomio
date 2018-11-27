package com.example.chriszhang.roomio;

import android.support.test.rule.ActivityTestRule;

import com.example.chriszhang.roomio.activities.GroupChatActivity;

import org.junit.Rule;

public class GroupChatTest {

    @Rule
    public ActivityTestRule<GroupChatActivity> rule =
            new ActivityTestRule<>(GroupChatActivity.class);
}
