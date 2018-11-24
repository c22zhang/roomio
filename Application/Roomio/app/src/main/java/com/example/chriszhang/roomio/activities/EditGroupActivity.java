package com.example.chriszhang.roomio.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
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
import android.widget.Spinner;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import java.util.Optional;

/**
 * Activity where the user will create/update groups.
 */
public final class EditGroupActivity extends ParentDrawerActivity{

    EditText addEditText, nameEditText;
    Spinner deleteSpinner;
    Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        saveButton = findViewById(R.id.saveButton);
        addEditText = findViewById(R.id.addEditText);
        nameEditText = findViewById(R.id.groupNameEdit);
        deleteSpinner = findViewById(R.id.spinner);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGroup();
            }
        });
    }

    private void saveGroup(){
        String groupName;
        if(State.hasGroup()){
            Group group = State.getGroup();
            if(getGroupName().isPresent()){
                group.setGroupName(getGroupName().get());
            }
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "Edited group!", Snackbar.LENGTH_LONG).show();
            //TODO: lookup usernames from backend and add and remove as necessary
        } else {
            if(getGroupName().isPresent()){
                groupName = getGroupName().get();
                State.getGroup().setGroupName(groupName);
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        "Group name must be set if creating a group from scratch!",
                        Snackbar.LENGTH_LONG).show();
                return;
            }
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "Group saved!", Snackbar.LENGTH_LONG).show();
        }
    }

    // TODO: Implement once servers working
    private User findUserFromAddText() {
        return null;
    }

    private Optional<String> getGroupName() {
        if(nameEditText.getText() != null){
            return Optional.of(nameEditText.getText().toString());
        } else{
            return Optional.empty();
        }
    }

}
