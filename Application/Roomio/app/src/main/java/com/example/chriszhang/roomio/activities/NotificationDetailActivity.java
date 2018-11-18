package com.example.chriszhang.roomio.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.chriszhang.roomio.R;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationDetailActivity extends AppCompatActivity {

    TextView notificationDetail;
    Button clearButton, dontClearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        Intent intent = getIntent();
        String notificationJson = intent.getExtras().get("current_notification").toString();
        Boolean isClearable = (Boolean) intent.getExtras().get("is_clearable");
        notificationDetail = findViewById(R.id.notificationDetail);
        clearButton = findViewById(R.id.clearButton);
        dontClearButton = findViewById(R.id.dontClearButton);

        if(isClearable != null && !isClearable){
            clearButton.setEnabled(false);
            dontClearButton.setEnabled(false);
        }

        try{
            JSONObject obj = new JSONObject(notificationJson);
            String message = obj.get("message").toString();
            notificationDetail.setText(message);

        } catch(JSONException e) {
            e.printStackTrace();
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "An error occurred when retrieving the data for this notification.",
                    Snackbar.LENGTH_LONG).show();
        }
    }
}
