package com.example.chriszhang.roomio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Optional;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button signup, signin;
    private Optional<String> usernameText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText2);
        signup = findViewById(R.id.signUpButton);
        signin = findViewById(R.id.signInButton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionToSignUp();
            }
        });

        this.getSupportActionBar().setTitle("Welcome to Room.io!");
    }

    private void transitionToSignUp(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}
