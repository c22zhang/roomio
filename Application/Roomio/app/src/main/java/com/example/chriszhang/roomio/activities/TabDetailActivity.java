package com.example.chriszhang.roomio.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.state.State;

import org.json.JSONException;
import org.json.JSONObject;

public class TabDetailActivity extends AppCompatActivity {

    TextView tabDetailReasonText,
            tabDetailAmountText,
            tabDetailDateText,
            tabDetailAssignerText,
            tabDetailAssigneeText;

    Button tabDetailClearButton, tabDetailDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_detail);
        Intent fromListView = getIntent();
        String tabJson = fromListView.getExtras().get("current_tab").toString();
        tabDetailAmountText = findViewById(R.id.tabDetailAmountText);
        tabDetailReasonText = findViewById(R.id.tabDetailReasonText);
        tabDetailDateText = findViewById(R.id.tabDetailDateText);
        tabDetailAssignerText = findViewById(R.id.tabDetailAssignerText);
        tabDetailAssigneeText = findViewById(R.id.tabDetailAssigneeText);
        tabDetailClearButton = findViewById(R.id.tabDetailClearButton);
        tabDetailDeleteButton = findViewById(R.id.tabDetailDeleteButton);

        tabDetailClearButton.setEnabled(false);
        tabDetailDeleteButton.setEnabled(false);

        tabDetailClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tabDetailDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        try{
            JSONObject obj = new JSONObject(tabJson);
            appendToText(tabDetailAmountText, " " + obj.get("amount"));
            appendToText(tabDetailAssigneeText, " " + obj.get("assignee"));
            appendToText(tabDetailAssignerText, " " + obj.get("assigner"));
            appendToText(tabDetailDateText, " " + obj.get("date_assigned"));
            appendToText(tabDetailReasonText, " " + obj.get("reason"));
            if(obj.get("assignee").equals(State.getCurrentUser().getUsername())){
                tabDetailClearButton.setEnabled(true);
            }
            if(obj.get("assigner").equals(State.getCurrentUser().getUsername())){
                tabDetailDeleteButton.setEnabled(true);
            }

        } catch (JSONException e){
            e.printStackTrace();
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "An error occurred when retrieving the data for this tab item.",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private static void appendToText(TextView text, String append) {
        String currentText = text.getText().toString();
        text.setText(currentText + append);
    }
}
