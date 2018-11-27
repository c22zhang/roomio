package com.example.chriszhang.roomio;

import android.support.test.rule.ActivityTestRule;

import com.example.chriszhang.roomio.activities.NotificationsActivity;

import org.junit.Rule;

public class NotificationTest {

    @Rule
    public ActivityTestRule<NotificationsActivity> rule =
            new ActivityTestRule<>(NotificationsActivity.class);
}
