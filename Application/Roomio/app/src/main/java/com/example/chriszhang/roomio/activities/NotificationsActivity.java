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

    ArrayList<Notification> notifications = new ArrayList<>();
    PersonalNotificationAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        State.pullGroup(this, getWindow().getDecorView().getRootView());

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

        if(State.getCurrentUser() != null){
            User currentUser = State.getCurrentUser();
            notifications.addAll(currentUser.getNotifications());
        }

        adapter = new PersonalNotificationAdapter(this, notifications);

        listView = findViewById(R.id.personalNotificationList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = listView.getItemAtPosition(position);
                getDetails(item);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        notifications.clear();
        notifications.addAll(State.getCurrentUser().getNotifications());
        adapter.notifyDataSetChanged();
    }

    private void getDetails(Object item){
        Notification not = (Notification) item;
        Intent intent = new Intent(this, NotificationDetailActivity.class);
        intent.putExtra("current_notification", item.toString());
        intent.putExtra("is_clearable", not.isClearable());
        startActivity(intent);
    }
}
