package com.example.chriszhang.roomio.model;

import com.example.chriszhang.roomio.state.State;

import org.junit.Test;

import java.util.Optional;

public class StateTest {

    User user = new User("asdf",
            "asdf",
            "asdf",
            "asdf",
            Optional.of("asdf"),
            Optional.of("asdf"));

    User user2 = new User("dasd",
            "wer",
            "asdfe",
            "as",
            Optional.of("wer"),
            Optional.of("sdf"));

    Group group = new Group("aw223",
            "asdf",
            "My group");

    State state = State.createState(user);
    @Test
    public void testSingleton(){
        assert(state.getCurrentUser().equals(user));
        State.createState(user2);
        assert(state.getCurrentUser().equals(user));

        assert(State.getStateInstance().equals(state));
    }

    @Test
    public void testHasGroup(){
        System.out.println(State.getGroup());
        assert(!State.hasGroup());
        State.setGroup(group);
        assert(State.hasGroup());
    }

    @Test
    public void testGetCurrentUser(){
        assert(State.getCurrentUser().equals(user));
    }
}
