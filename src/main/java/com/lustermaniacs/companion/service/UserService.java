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
    public boolean matchTwoUsers(User usr1, User usr2, int threshold){
        int usr1Length = usr1.getProfile().getSurveyResults().length;
        // Convert array into arraylist objects in order to use retainAll which only preserves duplicates in both arrays
        ArrayList<String> usr1List = new ArrayList<>(Arrays.asList(usr1.getProfile().getSurveyResults()));
        ArrayList<String> usr2List = new ArrayList<>(Arrays.asList(usr2.getProfile().getSurveyResults()));
        usr1List.retainAll(usr2List);
        if (usr1List.size() >= threshold)
            return true;
        else
            return false;
    }

    public void matchUsers(String username){
        User mainUser = getUserByUsername(username).get();
        List<User> filteredDB = matchingFiltering(username);
        List<UUID> matchedUsers = new ArrayList<>();
        for(int i = 0; i < filteredDB.size() ; i++) {
            //match main user with all filtered users and create match if threshold of 4 matches reached
            if (matchTwoUsers(mainUser, filteredDB.get(i), 4))
                matchedUsers.add(filteredDB.get(i).getId());
            //once 100 matches made, stop
            if (matchedUsers.size() > 99)
                break;
        }
    }
}
