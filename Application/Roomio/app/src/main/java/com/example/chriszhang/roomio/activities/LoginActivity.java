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
import com.example.chriszhang.roomio.state.State;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

/**
 * Activity for the login screen
 */
public final class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button signup, signin;

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
                login();
            }
        });
    }

    private void login() {
        if(hasAllFields()){
            String mUsername = username.getText().toString();
            String mPass = password.getText().toString();
            JSONObject loginObject = new JSONObject();
            Client client = new Client(this);
            try {
                loginObject.put("username", mUsername);
                loginObject.put("password", mPass);
                //TODO: post to backend
                client.postLogin(loginObject, getWindow().getDecorView().getRootView());
            } catch (JSONException e) {
                e.printStackTrace();
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        "Error sending username",
                        Snackbar.LENGTH_LONG).show();
            }
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    "You must fill in both the username and password field",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean hasAllFields(){
        return username.getText() != null &&
                password.getText() != null;
    }

    private<T> void transition(Class<T> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
