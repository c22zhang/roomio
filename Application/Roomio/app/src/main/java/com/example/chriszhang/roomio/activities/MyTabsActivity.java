package com.example.chriszhang.roomio.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.chriszhang.roomio.adapters.TabAdapter;
import com.example.chriszhang.roomio.model.Tab;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import java.util.ArrayList;

/**
 * Activity for displaying your tabs
 */
public final class MyTabsActivity extends ParentDrawerActivity {

    ArrayList<Tab> tabs = new ArrayList<>();
    TabAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tabs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButtonTransition();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        User currentUser = State.getCurrentUser();
        tabs.addAll(currentUser.getMyTabs());
        adapter = new TabAdapter(this, tabs);
        listView = findViewById(R.id.tabList);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK) {
                User currentUser = State.getCurrentUser();
                adapter.refreshTabs(currentUser.getMyTabs());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        tabs.clear();
        tabs.addAll(State.getCurrentUser().getMyTabs());
        adapter.notifyDataSetChanged();
    }

    private void addButtonTransition() {
        Intent intent = new Intent(this, AddTabActivity.class);
        startActivityForResult(intent, 1);
    }

    private void getDetails(Object item) {
        Intent intent = new Intent(this, TabDetailActivity.class);
        intent.putExtra("current_tab", item.toString());
        startActivity(intent);
    }

}
