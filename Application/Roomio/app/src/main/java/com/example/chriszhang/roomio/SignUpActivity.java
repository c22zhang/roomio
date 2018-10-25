package com.example.chriszhang.roomio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    Button signUpButton;
    EditText nameEditText, emailEditText, usernameEditText, passwordEditText, reenterEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.getSupportActionBar().setTitle("Sign Up");

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


    }

    private void transitionToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
