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
import com.example.chriszhang.roomio.adapters.TaskAdapter;
import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.Task;
import com.example.chriszhang.roomio.state.State;

/**
 * Activity containing the list of tasks for the household
 */
public final class HouseTasksActivity extends ParentDrawerActivity {

    Task[] houseTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addButtonTransition();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(State.hasGroup()){
            Group group = State.getGroup();
            houseTasks = group.getTasks().toArray(
                    new Task[group.getTasks().size()]);
            TaskAdapter adapter = new TaskAdapter(this, houseTasks);
            final ListView listView = findViewById(R.id.taskList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object item = listView.getItemAtPosition(position);
                    getDetails(item);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addButtonTransition() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    private void getDetails(Object item){
        Intent intent = new Intent(this, TaskDetailActivity.class);
        startActivityForResult(intent, 1);
    }
}
