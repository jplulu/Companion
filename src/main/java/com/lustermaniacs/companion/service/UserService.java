package com.lustermaniacs.companion.service;

import com.lustermaniacs.companion.database.MatchedUsersDB;
import com.lustermaniacs.companion.database.UsrDB;
import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.SurveyResults;
import com.lustermaniacs.companion.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.io.IOException;


@Service
public class UserService {

    private final UsrDB userDB;
    private final MatchedUsersDB matchedUsersDB;

    @Autowired
    public UserService(UsrDB userDB, MatchedUsersDB matchedUsersDB) {
        this.userDB = userDB;
        this.matchedUsersDB = matchedUsersDB;
    }

    public int addUser(User user) {
        if(userDB.addUser(user) == 1)
            return 1;
        matchedUsersDB.addUser(user.getUsername());
        return 0;
    }

    public Optional<User> getUserByUsername(String username) {
        return userDB.getUserByUsername(username);
    }

    public int updateUserByUsername(String username, User newUser) {
        return userDB.updateUserByUsername(username, newUser);
    }

    public int updateUserProfile(String username, Profile profile) {
        return userDB.updateUserProfile(username, profile);
    }

    public void setSurvey(String username, SurveyResults results) throws IOException {
        userDB.setSurvey(username, results);
    }
}
