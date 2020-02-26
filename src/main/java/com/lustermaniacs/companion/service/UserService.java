package com.lustermaniacs.companion.service;

import com.lustermaniacs.companion.database.UsrDB;
import com.lustermaniacs.companion.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UsrDB userDB;

    @Autowired
    public UserService(UsrDB userDB) {
        this.userDB = userDB;
    }

    public int addUser(User user) {
        return userDB.addUser(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return userDB.getUserByUsername(username);
    }

    public List<User> getAllSysmatchUser(String username) {
        return userDB.getMatchedUsers(username);
    }

    public void updateUserByUsername(String username, User newUser) {
        userDB.updateUserByUsername(username, newUser);
    }

    public void setSurvey(String username, String[] results){
        userDB.setSurvey(username, results);
    }
    // Function to match two given users based on a # of shared interest (threshold)
    public void matchTwoUsers(User usr1, User usr2, int threshold){
        userDB.matchTwoUsers(usr1, usr2, threshold);
    }

    public void matchUsers(String username){
        userDB.matchUsers(username);
    }
}
