package com.lustermaniacs.companion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UsrDB userDB;

    @Autowired
    public UserService(UsrDB userDB) {
        this.userDB = userDB;
    }

    public void addUser(User user) {
        userDB.addUser(user);
    }

    public Optional<User> getUserByUsername(String username) {
        userDB.getUserByUsername(username);
    }

    public List<User> getAllSysmatchUser(String username) {
        userDB.getMatchedUsers(username);
    }

    public void updateUserByUsername(String username, User newUser) {
        usrrDB.userUpdate(username, newUser);
    }

}
