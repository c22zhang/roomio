package com.example.chriszhang.roomio.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chriszhang.roomio.R;

import java.util.Optional;

/**
 * Activity for the login screen
 */
public final class LoginActivity extends AppCompatActivity {

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
                transition(SignUpActivity.class);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition(NotificationsActivity.class);
            }
        });
    }

    private<T> void transition(Class<T> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
