package com.example.chriszhang.roomio.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import java.util.List;

public class GroupInfoAdapter extends BaseAdapter {

    private final Activity context;
    private List<User> users;

    public GroupInfoAdapter(@NonNull Activity context, List<User> users){
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
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
                .inflate(R.layout.group_info_row, null, true);

        TextView username = row.findViewById(R.id.row_username_text);
        if(State.hasGroup()){
            User currentUser = State.getGroup().getMembers().get(position);
            username.setText(currentUser.getUserId());
        }
        return row;
    }
}
