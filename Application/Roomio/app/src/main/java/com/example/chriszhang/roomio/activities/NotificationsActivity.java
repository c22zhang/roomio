package com.example.chriszhang.roomio.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.adapters.PersonalNotificationAdapter;
import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.Notification;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import java.util.ArrayList;

/**
 * Activity for displaying personal notifications
 */
public final class NotificationsActivity extends ParentDrawerActivity {

    //TODO: Replace with actual user data once other stuff is completed
    ArrayList<Notification> notifications = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        User currentUser = State.getCurrentUser();
        notifications.addAll(currentUser.getNotifications());

        PersonalNotificationAdapter notificationAdapter =
                new PersonalNotificationAdapter(this, notifications);

        listView = findViewById(R.id.personalNotificationList);
        listView.setAdapter(notificationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = listView.getItemAtPosition(position);
                getDetails(item);
            }
        });
    }

    private void getDetails(Object item){
        Intent intent = new Intent(this, NotificationDetailActivity.class);
        startActivity(intent);
    }
}
