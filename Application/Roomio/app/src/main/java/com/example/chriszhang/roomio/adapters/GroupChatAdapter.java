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
import com.example.chriszhang.roomio.model.Message;

public class GroupChatAdapter extends ArrayAdapter {

    private final Activity context;
    private Message[] messages;

    public GroupChatAdapter(@NonNull Activity context, Message[] messages){
        super(context, R.layout.group_chat_row, messages);
        this.context = context;
        this.messages = messages;
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
        Message currentMessage = messages[position];
        dateText.setText(currentMessage.getDateSent());
        //TODO: swap for actual username
        nameText.setText(currentMessage.getSenderId());
        messageText.setText(currentMessage.getMessageContents());
        return row;
    }
}
