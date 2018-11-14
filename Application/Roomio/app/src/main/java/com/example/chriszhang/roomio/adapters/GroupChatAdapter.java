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
import com.example.chriszhang.roomio.model.Message;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import java.util.List;
import java.util.Optional;

public class GroupChatAdapter extends BaseAdapter {

    private final Activity context;
    private List<Message> messages;

    public GroupChatAdapter(@NonNull Activity context, List<Message> messages){
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder")
        View row = inflater
                .inflate(R.layout.group_chat_row, null, true);
        TextView dateText = row.findViewById(R.id.dateText);
        TextView nameText = row.findViewById(R.id.usernameText);
        TextView messageText = row.findViewById(R.id.messageText);
        Message currentMessage = messages.get(position);
        dateText.setText(currentMessage.getDateSent());
        if(State.hasGroup()){
            Optional<User> sender = State.getGroup().getMemberFromId(currentMessage.getSenderId());
            if(sender.isPresent()){
                nameText.setText(sender.get().getName());
            } else {
                nameText.setText(R.string.no_sender);
            }
            messageText.setText(currentMessage.getMessageContents());
        }
        return row;
    }
}
