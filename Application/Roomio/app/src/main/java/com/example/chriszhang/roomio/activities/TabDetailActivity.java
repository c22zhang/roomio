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
import com.example.chriszhang.roomio.model.Jsonable;
import com.example.chriszhang.roomio.model.Notification;
import com.example.chriszhang.roomio.model.Tab;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Optional;

/**
 * Activity that displays detailed about a specific tab.
 */
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
            final Double amount = Double.parseDouble(obj.get("amount").toString());
            NumberFormat formatter = new DecimalFormat("#0.00");

            appendToText(tabDetailAmountText, " $" + formatter.format(obj.get("amount")));
            appendToText(tabDetailAssigneeText, " " +
                    group.getMemberFromId(assigneeId).get().getUsername());
            appendToText(tabDetailAssignerText, " " +
                    group.getMemberFromId(assignerId).get().getUsername());
            appendToText(tabDetailDateText, " " + obj.get("date_assigned"));
            appendToText(tabDetailReasonText, " " + reason);
            if(obj.get("assignee").equals(State.getCurrentUser().getUserId())){
                tabDetailClearButton.setEnabled(true);
            }
            if(obj.get("assigner").equals(State.getCurrentUser().getUserId())){
                tabDetailDeleteButton.setEnabled(true);
            }
            tabDetailClearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createClearNotification(assigneeId, assignerId, reason, amount, obj);
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
            String assigneeUserId,
            String assignerUserId,
            String reason,
            Double amount,
            JSONObject obj){
        Group group = State.getGroup();
        User assignee = group.getMemberFromId(assigneeUserId).get();
        User assigner = group.getMemberFromId(assignerUserId).get();
        String message = String.format("%s wants to clear tab %s with amount %.2f",
                assignee.getUsername(), reason, amount);
        try{
            assigner.addNotification(new Notification("",
                    message,
                    assigner.getUserId(),
                    assignee.getUserId(),
                    Notification.Type.CLEAR_TAB_REQ,
                    Optional.<Jsonable>of(Tab.from(obj))));
            State.markGroupAsUpdated(this, getWindow().getDecorView().getRootView());
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "Your clear request has been sent!", Snackbar.LENGTH_LONG).show();
        } catch(JSONException | JsonToObjectException e){
            e.printStackTrace();
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "Malformed object bundle -- could not create notification.",
                    Snackbar.LENGTH_LONG).show();
        }

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
            State.markGroupAsUpdated(this, getWindow().getDecorView().getRootView());
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
