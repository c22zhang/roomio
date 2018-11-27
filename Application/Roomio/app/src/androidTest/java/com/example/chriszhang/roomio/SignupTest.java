package com.example.chriszhang.roomio;

import android.support.test.rule.ActivityTestRule;

import com.example.chriszhang.roomio.activities.SignUpActivity;

import org.junit.Rule;

public class SignupTest {

    @Rule
    public ActivityTestRule<SignUpActivity> rule =
            new ActivityTestRule<>(SignUpActivity.class);
}
