package com.lustermaniacs.companion.database;

import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.SurveyResults;
import com.lustermaniacs.companion.models.User;

import java.util.*;

public interface UsrDB{
    int addUser(User user);
    int updateUserByUsername(String username, User user);
    int setUserProfile(String username, Profile profile);
    Optional<User> getUserByUsername(String username);
    List<User> getAllUsers();
    int setSurvey(String username, SurveyResults results);
}
