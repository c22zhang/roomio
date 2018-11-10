package com.example.chriszhang.roomio.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.model.Task;

public class TaskAdapter extends ArrayAdapter {

    private final Activity context;

    private Task[] tasks;

    public TaskAdapter(@NonNull Activity context, Task[] tasks){
        super(context, R.layout.task_row, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder")
        View row = inflater
                .inflate(R.layout.task_row, null, true);
        TextView taskName = row.findViewById(R.id.taskName);
        TextView assignee = row.findViewById(R.id.assigneeText);
        TextView assigner = row.findViewById(R.id.assignerText);
        taskName.setText(tasks[position].getTaskName());

        //TODO: resolve to username instead of userID, ok for now as placeholder
        assignee.setText(tasks[position].getAssigneeUserId());
        assigner.setText(tasks[position].getAssignerUserId());
        return row;
    }
}
