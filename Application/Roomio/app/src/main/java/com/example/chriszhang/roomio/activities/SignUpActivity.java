package com.example.chriszhang.roomio.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.Client;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

/**
 * Activity for users signing up
 */
public final class SignUpActivity extends AppCompatActivity {

    Button signUpButton;
    EditText nameEditText, emailEditText,
            usernameEditText, passwordEditText,
            reenterEditText, intendedGroupEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.intendedUsernameEditText);
        passwordEditText = findViewById(R.id.intendedPasswordEditText);
        reenterEditText = findViewById(R.id.reenterEditText);
        intendedGroupEditText = findViewById(R.id.intendedGroupEditText);

        signUpButton = findViewById(R.id.signUpActivityButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Sign Up");
        }
    }

    private void signUp() {
        if(hasAllNecessaryFields()){
            JSONObject newUser = new JSONObject();
            try{
                newUser.put("desired_username", usernameEditText.getText().toString());
                newUser.put("password", passwordEditText.getText().toString());
                newUser.put("group_id", intendedGroupEditText.getText().toString());
                newUser.put("email", emailEditText.getText().toString());
                newUser.put("name", nameEditText.getText().toString());
                Client client = new Client(this);
                client.postSignup(newUser, getWindow().getDecorView().getRootView());
            } catch(JSONException e){
                e.printStackTrace();
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        "An error occurred when trying to sign up. Please try again.",
                        Snackbar.LENGTH_LONG);
            }
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "You must fill out all fields before signing up!",
                    Snackbar.LENGTH_LONG).show();
        }

    }

    private boolean hasAllNecessaryFields() {
        return nameEditText.getText() != null &&
                emailEditText.getText() != null &&
                usernameEditText.getText() != null &&
                passwordEditText.getText() != null &&
                intendedGroupEditText.getText() != null &&
                reenterEditText.getText() != null;
    }
}
