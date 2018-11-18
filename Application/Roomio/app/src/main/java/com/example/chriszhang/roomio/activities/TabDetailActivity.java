package com.example.chriszhang.roomio.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.JsonToObjectException;
import com.example.chriszhang.roomio.model.Notification;
import com.example.chriszhang.roomio.model.Tab;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import org.json.JSONException;
import org.json.JSONObject;

public class TabDetailActivity extends AppCompatActivity {

    TextView tabDetailReasonText,
            tabDetailAmountText,
            tabDetailDateText,
            tabDetailAssignerText,
            tabDetailAssigneeText;

    Button tabDetailClearButton, tabDetailDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_detail);
        Intent fromListView = getIntent();
        String tabJson = fromListView.getExtras().get("current_tab").toString();
        tabDetailAmountText = findViewById(R.id.tabDetailAmountText);
        tabDetailReasonText = findViewById(R.id.tabDetailReasonText);
        tabDetailDateText = findViewById(R.id.tabDetailDateText);
        tabDetailAssignerText = findViewById(R.id.tabDetailAssignerText);
        tabDetailAssigneeText = findViewById(R.id.tabDetailAssigneeText);
        tabDetailClearButton = findViewById(R.id.tabDetailClearButton);
        tabDetailDeleteButton = findViewById(R.id.tabDetailDeleteButton);

        tabDetailClearButton.setEnabled(false);
        tabDetailDeleteButton.setEnabled(false);

        try{
            final JSONObject obj = new JSONObject(tabJson);
            Group group = State.getGroup();
            final String assigneeId = obj.get("assignee").toString();
            final String assignerId = obj.get("assigner").toString();
            final String reason = obj.get("reason").toString();
            appendToText(tabDetailAmountText, " " + obj.get("amount"));
            appendToText(tabDetailAssigneeText, " " +
                    group.getMemberFromId(assigneeId).get());
            appendToText(tabDetailAssignerText, " " +
                    group.getMemberFromId(assignerId).get());
            appendToText(tabDetailDateText, " " + obj.get("date_assigned"));
            appendToText(tabDetailReasonText, " " + reason);
            if(obj.get("assignee").equals(State.getCurrentUser().getUsername())){
                tabDetailClearButton.setEnabled(true);
            }
            if(obj.get("assigner").equals(State.getCurrentUser().getUsername())){
                tabDetailDeleteButton.setEnabled(true);
            }
            tabDetailClearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createClearNotification(assigneeId, assignerId, reason);
                    Snackbar.make(getWindow().getDecorView().getRootView(),
                            "Notification sent to assigner!" , Snackbar.LENGTH_LONG).show();
                }
            });

            tabDetailDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteThis(obj, assigneeId, assignerId);
                }
            });

        } catch (JSONException e){
            e.printStackTrace();
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "An error occurred when retrieving the data for this tab item.",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void createClearNotification(
            String assigneeUserId, String assignerUserId, String reason){
        Group group = State.getGroup();
        User assignee = group.getMemberFromId(assigneeUserId).get();
        User assigner = group.getMemberFromId(assignerUserId).get();
        String message = String.format("%s wants to clear tab %s.", assignee.getUsername(), reason);
        assigner.addNotification(new Notification("",
                message,
                assignee.getUserId(),
                State.getCurrentUser().getUserId(),
                Notification.Type.CLEAR_TAB_REQ));
    }

    private void deleteThis(JSONObject obj, String assigneeUserId, String assignerUserId){
        Group group = State.getGroup();
        User assignee = group.getMemberFromId(assigneeUserId).get();
        User assigner = group.getMemberFromId(assignerUserId).get();
        Intent intent = new Intent(this, MyTabsActivity.class);
        try{
            Tab tab = Tab.from(obj);
            assignee.deleteTab(tab);
            assigner.deleteTab(tab);
            startActivity(intent);
        } catch(JSONException | JsonToObjectException e){
            e.printStackTrace();
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "Could not delete this tab -- malformed JSON Object",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private static void appendToText(TextView text, String append) {
        String currentText = text.getText().toString();
        text.setText(currentText + append);
    }
}
