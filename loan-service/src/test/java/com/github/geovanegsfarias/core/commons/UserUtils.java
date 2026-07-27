package com.github.geovanegsfarias.core.commons;

import com.github.geovanegsfarias.core.entities.User;

public class UserUtils {

    public User newUserToSave() {
        return new User("user@gmail.com", "{bcrypt}$2a$10$yvxv.udlMjSHxePUTYeZG.i5rxBjs2rwd2kM08P7qp1GlPvWoRaCG");
    }

    public User savedUser() {
        return new User(1L, "user@gmail.com", "{bcrypt}$2a$10$yvxv.udlMjSHxePUTYeZG.i5rxBjs2rwd2kM08P7qp1GlPvWoRaCG");
    }
}
