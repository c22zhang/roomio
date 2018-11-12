package com.example.chriszhang.roomio.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.model.Tab;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

public class TabAdapter extends ArrayAdapter {

    private final Activity context;
    private Tab[] tabs;

    public TabAdapter(Activity context, Tab[] tabs) {
        super(context, R.layout.tab_row, tabs);
        this.context = context;
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder")
        View row = inflater
                .inflate(R.layout.tab_row, null, true);
        TextView amount = row.findViewById(R.id.amount);
        TextView tabName = row.findViewById(R.id.tabName);
        TextView assignee = row.findViewById(R.id.tabAssignee);
        TextView assigner = row.findViewById(R.id.tabAssigner);
        Tab currTab = tabs[position];
        amount.setText("$" + currTab.getAmount().toString());
        tabName.setText(currTab.getTabName());

        User tabAssignee = State.getGroup().getMemberFromId(currTab.getAssigneeUserId()).get();
        User tabAssigner = State.getGroup().getMemberFromId(currTab.getAssignerUserId()).get();
        assignee.setText(tabAssignee.getName());
        assigner.setText(tabAssigner.getName());
        return row;
    }
}
