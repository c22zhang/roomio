package com.example.chriszhang.roomio.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.state.State;

import org.json.JSONException;
import org.json.JSONObject;

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
            JSONObject obj = new JSONObject(taskJson);
            appendToText(taskDetailName, " " + obj.get("task_name"));
            appendToText(taskDetailAssignerText, " " + obj.get("assigner"));
            appendToText(taskDetailAssigneeText, " " + obj.get("assignee"));
            appendToText(taskDetailDateText, " " + obj.get("date_assigned"));
            appendToText(taskDetailDetails, " " + obj.get("description"));

            if(obj.get("assignee").equals(State.getCurrentUser().getUsername())){
                taskDetailClearButton.setEnabled(true);
            }
            if(obj.get("assigner").equals(State.getCurrentUser().getUsername())){
                taskDetailDeleteButton.setEnabled(true);
            }

        } catch(JSONException e){
            e.printStackTrace();
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "An error occurred when retrieving the data for this task item.",
                    Snackbar.LENGTH_LONG).show();
        }


    }

    private static void appendToText(TextView text, String append) {
        String currentText = text.getText().toString();
        text.setText(currentText + append);
    }
}
