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
import com.example.chriszhang.roomio.model.Task;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;
import com.example.chriszhang.roomio.utils.Utils;

import java.util.Optional;

/**
 * Activity for adding tasks to the task list
 */
public class AddTaskActivity extends AppCompatActivity {

    Button saveTaskButton;
    EditText taskDescriptionEditText, taskAssigneeEditText, taskNameEditText;
    Task generatedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        saveTaskButton = findViewById(R.id.saveTaskButton);
        taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
        taskAssigneeEditText = findViewById(R.id.taskAssigneeEditText);
        taskNameEditText = findViewById(R.id.taskNameEditText);

        saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                int result = saveTask();
                if(result == RESULT_OK) {
                    sendTaskNotification();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private int saveTask(){
        if(taskDescriptionEditText != null &&
                taskAssigneeEditText != null &&
                taskNameEditText != null){
            if(State.hasGroup()){
                Group group = State.getGroup();
                User current = State.getCurrentUser();
                Optional<User> assignee =
                        group.getMemberFromUsername(taskAssigneeEditText.getText().toString());
                return createTask(assignee, current, group);
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        "You are not currently in a group.",
                        Snackbar.LENGTH_LONG).show();
                return RESULT_CANCELED;
            }
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "All fields of the form must be filled!",
                    Snackbar.LENGTH_LONG).show();
            return RESULT_CANCELED;
        }
    }

    private void sendTaskNotification() {
        Group group = State.getGroup();
        User assignee = group.getMemberFromId(generatedTask.getAssigneeUserId()).get();
        User assigner = State.getCurrentUser();
        String message = String.format("%s has assigned you a task: %s", assigner.getUsername(),
                 generatedTask.getTaskName());
        assignee.addNotification(new Notification(
                "",
                message,
                assignee.getUserId(),
                assigner.getUserId(),
                Notification.Type.ASSIGNMENT,
                Optional.<Jsonable>empty()));
    }

    private int createTask(Optional<User> assignee, User current, Group group){
        if(assignee.isPresent()){
            Task task = new Task(
                    "",
                    assignee.get().getUserId(),
                    current.getUserId(),
                    taskNameEditText.getText().toString(),
                    taskDescriptionEditText.getText().toString(),
                    Utils.getCurrentDate());
            this.generatedTask = task;
            group.addTask(task);
            return RESULT_OK;
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "Could not find user associated with the assignee.",
                    Snackbar.LENGTH_LONG).show();
            return RESULT_CANCELED;
        }
    }
}
