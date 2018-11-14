package com.example.chriszhang.roomio.activities;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.adapters.GroupChatAdapter;
import com.example.chriszhang.roomio.model.Message;

import java.util.ArrayList;

/**
 * Activity for the Group Chat
 */
public final class GroupChatActivity extends ParentDrawerActivity {

    Button sendButton;
    EditText messageEdit;
    ListView listView;

    //TODO: overwrite with actual data
    ArrayList<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sendButton = findViewById(R.id.sendButton);
        messageEdit = findViewById(R.id.enterMessageEditText);

        GroupChatAdapter adapter = new GroupChatAdapter(this, messages);
        listView = findViewById(R.id.groupChatList);
        listView.setAdapter(adapter);
    }
}
