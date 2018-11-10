package com.example.chriszhang.roomio.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chriszhang.roomio.R;

public class AddTabActivity extends AppCompatActivity {

    Button saveTabButton;
    EditText amountEditText, tabAssigneeEditText, tabReasonEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tab);
        saveTabButton = findViewById(R.id.saveTabButton);
        amountEditText = findViewById(R.id.amountEditText);
        tabAssigneeEditText = findViewById(R.id.tabAssigneeEditText);
        tabReasonEditText = findViewById(R.id.tabReasonEditText);

        saveTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void saveData(){
        Intent intent  = new Intent(this, MyTabsActivity.class);
        startActivity(intent);
    }
}
