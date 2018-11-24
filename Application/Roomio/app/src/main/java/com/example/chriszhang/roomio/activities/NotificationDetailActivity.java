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
import com.example.chriszhang.roomio.model.Task;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

/**
 * Displays details about a selected notification
 */
public class NotificationDetailActivity extends AppCompatActivity {

    TextView notificationDetail;
    Button clearButton, dontClearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        Intent intent = getIntent();
        String notificationJson = intent.getExtras().get("current_notification").toString();
        Boolean isClearable = (Boolean) intent.getExtras().get("is_clearable");
        notificationDetail = findViewById(R.id.notificationDetail);
        clearButton = findViewById(R.id.clearButton);
        dontClearButton = findViewById(R.id.dontClearButton);

        if(isClearable != null && !isClearable){
            clearButton.setEnabled(false);
            dontClearButton.setEnabled(false);
        }

        try{
            final JSONObject obj = new JSONObject(notificationJson);
            Group g = State.getGroup();
            String message = obj.get("message").toString();
            final User toUser = g.getMemberFromId(obj.get("to_user_id").toString()).get();
            final User fromUser = g.getMemberFromId(obj.get("from_user_id").toString()).get();
            final JSONObject potentialClear = obj.getJSONObject("potential_clear");
            notificationDetail.setText(message);
            clearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        clear(toUser, fromUser, potentialClear, obj);
                    } catch (JsonToObjectException | JSONException e){
                        Snackbar.make(getWindow().getDecorView().getRootView(),
                                "An error occurred when clearing this notification.",
                                Snackbar.LENGTH_LONG).show();
                    }
                }
            });
            dontClearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        dontClear(toUser, fromUser, obj);
                    } catch (JsonToObjectException | JSONException e){
                        Snackbar.make(getWindow().getDecorView().getRootView(),
                                "An error occurred when checking this notification.",
                                Snackbar.LENGTH_LONG).show();
                    }
                }
            });

        } catch(JSONException e) {
            e.printStackTrace();
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "An error occurred when retrieving the data for this notification.",
                    Snackbar.LENGTH_LONG).show();
        }
    }


    private void clear(User sendingUser, User receivingUser,
                       JSONObject potentialClear, JSONObject ogNotification)
            throws JSONException, JsonToObjectException {
        Task task = null;
        Tab tab = null;
        Jsonable cleared = getClearingType(potentialClear);
        Group g = State.getGroup();

        if(cleared instanceof Task){
            task = (Task) cleared;
            g.deleteTask(task);
        } else {
            tab = (Tab) cleared;
            sendingUser.deleteTab(tab);
            receivingUser.deleteTab(tab);
        }

        String description = (task != null)? task.getDescription() : tab.getReason();
        String message = String.format("%s has cleared your tab/task with description %s!",
                sendingUser.getUsername(), description);
        Notification n = new Notification("",
                message,
                receivingUser.getUserId(),
                sendingUser.getUserId(),
                Notification.Type.REQ_ACCEPTANCE,
                Optional.of(cleared));
        receivingUser.addNotification(n);
        sendingUser.deleteNotification(Notification.from(ogNotification));
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);
    }

    private void dontClear(User sendingUser, User receivingUser,
                           JSONObject ogNotification)
            throws JSONException, JsonToObjectException {
        String message = String.format("User %s will not clear your task/tab.", sendingUser);
        Notification n = new Notification("",
                message,
                receivingUser.getUserId(),
                sendingUser.getUserId(),
                Notification.Type.REQ_REJECTION,
                Optional.<Jsonable>empty());
        receivingUser.addNotification(n);
        sendingUser.deleteNotification(Notification.from(ogNotification));
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);
    }

    private Jsonable getClearingType(JSONObject potentialClear)
            throws JSONException, JsonToObjectException {
        if(potentialClear.has("task_id")){
            return Task.from(potentialClear);
        } else {
            return Tab.from(potentialClear);
        }
    }

}
