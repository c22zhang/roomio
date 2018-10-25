package com.example.chriszhang.roomio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class PersonalNotificationActivity extends AppCompatActivity {

    //TODO: Replace with actual user data once other stuff is completed
    String[] placeholders = {"This is a Notification.", "This is also a notification."};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_notification);

        PersonalNotificationAdapter notificationAdapter =
                new PersonalNotificationAdapter(this, placeholders);

        listView = findViewById(R.id.personalNotificationList);
        listView.setAdapter(notificationAdapter);
    }
}
