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
import com.example.chriszhang.roomio.model.Notification;

public class PersonalNotificationAdapter extends ArrayAdapter {

    private final Activity context;

    private Notification[] notificationTitles;


    public PersonalNotificationAdapter(@NonNull Activity context, Notification[] notificationTitles) {
        super(context, R.layout.personal_notification_row, notificationTitles);
        this.context = context;
        this.notificationTitles = notificationTitles;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder")
        View row = inflater
                .inflate(R.layout.personal_notification_row, null, true);
        TextView notificationTitle = row.findViewById(R.id.notificationText);
        notificationTitle.setText(notificationTitles[position].getMessage());
        return row;
    }
}
