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
import com.example.chriszhang.roomio.model.Task;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity for viewing details about a specific task.
 */
public class TaskDetailActivity extends AppCompatActivity {

    TextView taskDetailName,
            taskDetailAssignerText,
            taskDetailAssigneeText,
            taskDetailDateText,
            taskDetailDetails;

    Button taskDetailClearButton, taskDetailDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Intent fromParent = getIntent();
        String taskJson = fromParent.getExtras().get("current_task").toString();

        taskDetailName = findViewById(R.id.taskDetailName);
        taskDetailAssignerText = findViewById(R.id.taskDetailAssignerText);
        taskDetailAssigneeText = findViewById(R.id.taskDetailAssigneeText);
        taskDetailDateText = findViewById(R.id.taskDetailDateText);
        taskDetailDetails = findViewById(R.id.taskDetailDetails);
        taskDetailClearButton = findViewById(R.id.taskDetailClearButton);
        taskDetailDeleteButton = findViewById(R.id.taskDetailDeleteButton);

        taskDetailClearButton.setEnabled(false);
        taskDetailDeleteButton.setEnabled(false);

        try{
            Group group = State.getGroup();
            final JSONObject obj = new JSONObject(taskJson);
            final String assignerId = obj.get("assigner").toString();
            final String assigneeId = obj.get("assignee").toString();
            final String description = obj.get("description").toString();
            final String taskName = obj.get("task_name").toString();
            appendToText(taskDetailName, " " + taskName);
            appendToText(taskDetailAssignerText, " "
                    + group.getMemberFromId(assignerId).get());
            appendToText(taskDetailAssigneeText, " " +
                    group.getMemberFromId(assigneeId).get());
            appendToText(taskDetailDateText, " " + obj.get("date_assigned"));
            appendToText(taskDetailDetails, " " + description);

            if(obj.get("assignee").equals(State.getCurrentUser().getUsername())){
                taskDetailClearButton.setEnabled(true);
            }
            if(obj.get("assigner").equals(State.getCurrentUser().getUsername())){
                taskDetailDeleteButton.setEnabled(true);
            }

            taskDetailClearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createClearNotification(assigneeId, assignerId, taskName);
                }
            });

            taskDetailDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteThisTask(obj);
                }
            });

        } catch(JSONException e){
            e.printStackTrace();
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "An error occurred when retrieving the data for this task item.",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void createClearNotification(String assigneeId, String assignerId, String taskName){
        Group group = State.getGroup();
        User assignee = group.getMemberFromId(assigneeId).get();
        User assigner = group.getMemberFromId(assignerId).get();
        String message = String.format("%s wants to clear task %s.",
                assignee.getUsername(), taskName);
        Notification notification = new Notification(
                "",
                message,
                assigner.getUserId(),
                assignee.getUserId(),
                Notification.Type.CLEAR_TASK_REQ);
        assigner.addNotification(notification);
        Snackbar.make(getWindow().getDecorView().getRootView().getRootView(),
                "Your clear request has been sent!", Snackbar.LENGTH_LONG).show();
    }

    private void deleteThisTask(JSONObject obj){
        Intent intent = new Intent(this, HouseTasksActivity.class);
        Group group = State.getGroup();
        try{
            group.deleteTask(Task.from(obj));
            startActivity(intent);
        } catch (JSONException | JsonToObjectException e){
            e.printStackTrace();
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "Malformed bundle -- could not make Task object.",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private static void appendToText(TextView text, String append) {
        String currentText = text.getText().toString();
        text.setText(currentText + append);
    }
}
