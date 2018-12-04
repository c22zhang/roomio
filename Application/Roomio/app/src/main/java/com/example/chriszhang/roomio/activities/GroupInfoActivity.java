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
import android.widget.ListView;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.adapters.GroupInfoAdapter;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import java.util.ArrayList;
import java.util.List;

public class GroupInfoActivity extends ParentDrawerActivity {

    ListView listView;
    List<User> members;
    GroupInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupinfo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        State.pullGroup(this, getWindow().getDecorView().getRootView());

        if(State.hasGroup()) {
            String gName = State.getGroup().getGroupId();
            String currentUsername = State.getCurrentUser().getUserId();
            toolbar.setTitle(String.format("%s, current user: %s", gName, currentUsername));
            members = State.getGroup().getMembers();
        } else {
            members = new ArrayList<>();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView = findViewById(R.id.groupInfoListView);

        if(State.hasGroup()){
            adapter = new GroupInfoAdapter(this, members);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        State.pullGroup(this, getWindow().getDecorView().getRootView());
        members.clear();
        members.addAll(State.getGroup().getMembers());
        adapter.notifyDataSetChanged();
    }
}
