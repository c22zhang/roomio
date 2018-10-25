package com.example.chriszhang.roomio.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chriszhang.roomio.R;

public class SignUpActivity extends AppCompatActivity {

    Button signUpButton;
    EditText nameEditText, emailEditText, usernameEditText, passwordEditText, reenterEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.intendedUsernameEditText);
        passwordEditText = findViewById(R.id.intendedPasswordEditText);
        reenterEditText = findViewById(R.id.reenterEditText);

        signUpButton = findViewById(R.id.signUpActivityButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionToLogin();
            }
        });

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Sign Up");
        }
    }

    private void transitionToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
