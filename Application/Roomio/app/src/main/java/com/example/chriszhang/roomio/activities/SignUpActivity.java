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

import java.util.Optional;

/**
 * Activity for users signing up
 */
public final class SignUpActivity extends AppCompatActivity {

    Button signUpButton;
    EditText nameEditText, emailEditText,
            usernameEditText, passwordEditText,
            reenterEditText, intendedGroupEditText;
    boolean creatingNewGroup = false;

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
            // TODO: implement this method with backend calls
            Group intended = getGroupOrCreate();
            Optional<String> adminGroup = Optional.empty();
            if(creatingNewGroup){
                adminGroup = Optional.of(intended.getGroupId());
            }
            User user = new User(
                    "",
                    usernameEditText.getText().toString(),
                    nameEditText.getText().toString(),
                    emailEditText.getText().toString(),
                    adminGroup,
                    //TODO: replace this with the id of var intended once function is created
                    Optional.<String>empty());
            // TODO: send user to backend
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "You must fill out all fields before signing up!",
                    Snackbar.LENGTH_LONG).show();
        }

    }

    //TODO: implement this method ==========================
    private Group getGroupOrCreate(){
        return null;
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
