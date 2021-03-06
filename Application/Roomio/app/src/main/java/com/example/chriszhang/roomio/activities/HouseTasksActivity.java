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
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import java.util.ArrayList;

/**
 * Activity containing the list of tasks for the household
 */
public final class HouseTasksActivity extends ParentDrawerActivity {

    ArrayList<Task> houseTasks = new ArrayList<>();
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_tasks);
        // State.pullGroup(this, getWindow().getDecorView().getRootView());

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
        adapter = new TaskAdapter(this, houseTasks);
        final ListView listView = findViewById(R.id.taskList);
        listView.setAdapter(adapter);

        if(State.hasGroup()){
            Group group = State.getGroup();
            houseTasks.addAll(group.getTasks());
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
        if(requestCode == 1){
            if(resultCode == RESULT_OK) {
                Group group = State.getGroup();
                houseTasks.addAll(group.getTasks());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        State.pullGroup(this, getWindow().getDecorView().getRootView());
        houseTasks.clear();
        houseTasks.addAll(State.getGroup().getTasks());
        adapter.notifyDataSetChanged();
    }

    private void addButtonTransition() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivityForResult(intent, 1);
    }

    private void getDetails(Object item){
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra("current_task", item.toString());
        startActivity(intent);
    }
}
