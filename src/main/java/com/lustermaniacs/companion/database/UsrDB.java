package com.lustermaniacs.companion.database;

import com.lustermaniacs.companion.models.User;

import java.util.List;

interface UsrDB{
    int addUser(User user);
    int updateUserByUsername(String username);
    List<User> getMatchedUsers(String username);
    User getUserByUsername(String username);
}
