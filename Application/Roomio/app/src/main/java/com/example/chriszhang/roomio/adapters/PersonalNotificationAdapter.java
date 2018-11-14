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
import com.example.chriszhang.roomio.model.Notification;

import java.util.List;

public class PersonalNotificationAdapter extends BaseAdapter {

    private final Activity context;

    private List<Notification> notificationTitles;


    public PersonalNotificationAdapter(@NonNull Activity context,
                                       List<Notification> notificationTitles) {
        this.context = context;
        this.notificationTitles = notificationTitles;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return notificationTitles.get(position);
    }

    @Override
    public int getCount() {
        return notificationTitles.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder")
        View row = inflater
                .inflate(R.layout.personal_notification_row, null, true);
        TextView notificationTitle = row.findViewById(R.id.notificationText);
        notificationTitle.setText(notificationTitles.get(position).getMessage());
        return row;
    }
}
