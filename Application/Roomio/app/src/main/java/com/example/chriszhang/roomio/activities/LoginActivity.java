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

    //TODO: FOR TEST PURPOSES ONLY, REPLACE ONCE SERVER WORKS
    private static final User user =
            new User("ABC",
                    "Test test",
                    "Test Test",
                    "abc@123.net",
                    Optional.<String>empty(),
                    Optional.<String>empty());
    User user1 = new User(
            "asdf",
            "ChrisZhang",
            "Chris Zhang",
            "asdf@gmail.com",
            Optional.<String>empty(),
            Optional.<String>empty());
    User user2 = new User(
            "1234",
            "LeBron LUL",
            "LeBron James",
            "asdf123@gmail.com",
            Optional.<String>empty(),
            Optional.<String>empty());


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
                State.createState(user);
                Group g = new Group("",
                        user.getUserId(),
                        "some name");
                g.addMember(user);
                user.setHouseholdGroupId(Optional.of(g.getGroupId()));
                user.setAdminedGroupId(Optional.of(g.getGroupId()));
                g.addMember(user1);
                g.addMember(user2);
                State.setGroup(g);
                login();
                transition(NotificationsActivity.class);
            }
        });
    }

    private void login() {
        if(hasAllFields()){
            String mUsername = username.getText().toString();
            String mPass = password.getText().toString();
            JSONObject loginObject = new JSONObject();
            try {
                loginObject.put("username", mUsername);
                loginObject.put("password", mPass);
                //TODO: post to backend
                getUserWithPassword();
                getGroupWithUser();
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

    // TODO: implement these
    private User getUserWithPassword(){
        return null;
    }

    // TODO: implement this
    private Group getGroupWithUser(){
        return null;
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
