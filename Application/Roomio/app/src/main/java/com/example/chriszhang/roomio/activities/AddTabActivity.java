package com.example.chriszhang.roomio.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.Tab;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;
import com.example.chriszhang.roomio.utils.Utils;

import java.util.Optional;

/**
 * Activity for adding tabs to the TabList
 */
public class AddTabActivity extends AppCompatActivity {

    Button saveTabButton;
    EditText amountEditText, tabAssigneeEditText, tabReasonEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tab);
        saveTabButton = findViewById(R.id.saveTabButton);
        amountEditText = findViewById(R.id.amountEditText);
        tabAssigneeEditText = findViewById(R.id.tabAssigneeEditText);
        tabReasonEditText = findViewById(R.id.tabReasonEditText);

        saveTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                int result = addTab();
                if(result == RESULT_OK){
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void saveData() {
        Intent intent  = new Intent(this, MyTabsActivity.class);
        startActivity(intent);
    }

    private int addTab() {
        if(amountEditText.getText() != null &&
                tabAssigneeEditText != null &&
                tabReasonEditText != null){
            if(State.hasGroup()){
                Group group = State.getGroup();
                User current = State.getCurrentUser();
                Optional<User> assignee = group
                        .getMemberFromUsername(
                                tabAssigneeEditText.getText().toString());
                return maybeCreateAndAddTab(assignee, current, group);
            } else {
               Snackbar.make(getWindow().getDecorView().getRootView(),
                       "You are not currently in a group.",
                       Snackbar.LENGTH_LONG).show();
               return RESULT_CANCELED;
            }
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "You must set all fields within the form!",
                    Snackbar.LENGTH_LONG).show();
            return RESULT_CANCELED;
        }
    }

    private int maybeCreateAndAddTab(Optional<User> assignee, User current, Group group){
        if(Utils.isPresent(assignee,
                "Specified user does not exist.",
                getWindow().getDecorView().getRootView())){
            Tab tab = new Tab (
                    "",
                    tabReasonEditText.getText().toString(),
                    group.getId(assignee.get()),
                    current.getUserId(),
                    Utils.getCurrentDate(),
                    Double.parseDouble(amountEditText.getText().toString()));
            current.addTab(tab);
            assignee.get().addTab(tab);
            return RESULT_OK;
        } else {
            return RESULT_CANCELED;
        }
    }
}
