package com.example.chriszhang.roomio.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chriszhang.roomio.R;

public class AddTaskActivity extends AppCompatActivity {

    Button saveTaskButton;
    EditText taskDescriptionEditText, taskAssigneeEditText, taskNameEditText;

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
                saveTask();
            }
        });
    }

    private void saveTask(){
        Intent intent = new Intent(this, HouseTasksActivity.class);
        startActivity(intent);
    }
}
