package com.example.chriszhang.roomio.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.model.Tab;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class TabAdapter extends BaseAdapter {

    private final Activity context;
    private List<Tab> tabs;

    public TabAdapter(Activity context, List<Tab> tabs) {
        this.context = context;
        this.tabs = tabs;
    }

    public void refreshTabs(List<Tab> tabs){
        this.tabs.clear();
        this.tabs.addAll(tabs);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public Object getItem(int position) {
        return tabs.get(position);
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
        Tab currTab = tabs.get(position);

        tabName.setText(currTab.getReason());

        User tabAssignee = State.getGroup().getMemberFromId(currTab.getAssigneeUserId()).get();
        User tabAssigner = State.getGroup().getMemberFromId(currTab.getAssignerUserId()).get();
        NumberFormat formatter = new DecimalFormat("#0.00");
        String amt = formatter.format(currTab.getAmount());
        assignee.setText("Assignee: " + tabAssignee.getName());
        assigner.setText("Assigner: " + tabAssigner.getName());
        amount.setText("$" + amt);
        return row;
    }
}
