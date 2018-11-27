package com.example.chriszhang.roomio.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.model.Task;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private final Activity context;

    private List<Task> tasks;

    public TaskAdapter(@NonNull Activity context, List<Task> tasks){
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
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
        taskName.setText(tasks.get(position).getTaskName());
        String assigneeId = tasks.get(position).getAssigneeUserId();
        String assignerId = tasks.get(position).getAssignerUserId();
        User taskAssignee =
                State.getGroup().getMemberFromId(assigneeId).get();
        User taskAssigner =
                State.getGroup().getMemberFromId(assignerId).get();
        assignee.setText(taskAssignee.getName());
        assigner.setText(taskAssigner.getName());
        return row;
    }
}
