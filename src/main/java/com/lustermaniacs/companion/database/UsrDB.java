package com.lustermaniacs.companion.database;

import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.SurveyResults;
import com.lustermaniacs.companion.models.User;

import java.util.*;

public interface UsrDB{
    int addUser(User user);
    int updateUserByUsername(String username, User user);
    void updateUserProfile(String username, Profile profile);
    List<User> getMatchedUsers(String username);
    Optional<User> getUserByUsername(String username);
    List<User> getAllUsers();
    void setSurvey(String username, SurveyResults results);
}
