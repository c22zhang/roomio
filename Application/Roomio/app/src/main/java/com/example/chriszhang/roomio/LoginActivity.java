package com.example.chriszhang.roomio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.Optional;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button signup, login;
    private Optional<String> usernameText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText2);
        signup = findViewById(R.id.signUpButton);
        login = findViewById(R.id.signInButton);

        this.getActionBar().setTitle("Welcome to Room.io!");
    }

}
