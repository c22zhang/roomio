package com.example.chriszhang.roomio.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chriszhang.roomio.R;
import com.example.chriszhang.roomio.model.Group;
import com.example.chriszhang.roomio.model.User;
import com.example.chriszhang.roomio.state.State;

import java.util.Optional;

/**
 * Activity for the login screen
 */
public final class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button signup, signin;
    private Optional<String> usernameText, passwordText;

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
                user.setHouseholdGroupId(Optional.of(g.getGroupId()));
                user.setAdminedGroupId(Optional.of(g.getGroupId()));
                g.addMember(user1);
                g.addMember(user2);
                State.setGroup(g);
                transition(NotificationsActivity.class);
            }
        });
    }

    private<T> void transition(Class<T> clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

}
