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
import com.example.chriszhang.roomio.model.Jsonable;
import com.example.chriszhang.roomio.model.Notification;
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
    Tab generatedTab;

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
                    addNotification();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
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

    private void addNotification(){
        Group group = State.getGroup();
        User assignee = group.getMemberFromId(generatedTab.getAssigneeUserId()).get();
        String message =
                String.format(
                        "%s has assigned you a tab for the following reason: %s.",
                        State.getCurrentUser().getUsername(),
                        generatedTab.getReason());
        assignee.addNotification(new Notification(
                "",
                message,
                assignee.getUserId(),
                State.getCurrentUser().getUserId(),
                Notification.Type.ASSIGNMENT,
                Optional.<Jsonable>empty()));
    }

    private int maybeCreateAndAddTab(Optional<User> assignee, User current, Group group){
        if(Utils.isPresent(assignee,
                "Specified user does not exist.",
                getWindow().getDecorView().getRootView())){

            if(Double.parseDouble(amountEditText.getText().toString()) <= 0){
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        "You must enter a positive amount greater than 0.",
                        Snackbar.LENGTH_LONG).show();
                return RESULT_CANCELED;
            }
            
            Tab tab = new Tab (
                    "",
                    tabReasonEditText.getText().toString(),
                    group.getId(assignee.get()),
                    current.getUserId(),
                    Utils.getCurrentDate(),
                    Double.parseDouble(amountEditText.getText().toString()));
            this.generatedTab = tab;
            current.addTab(tab);
            assignee.get().addTab(tab);
            return RESULT_OK;
        } else {
            return RESULT_CANCELED;
        }
    }
}
