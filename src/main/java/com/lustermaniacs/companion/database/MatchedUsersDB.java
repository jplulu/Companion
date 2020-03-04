package com.lustermaniacs.companion.database;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MatchedUsersDB {

    private static ConcurrentHashMap<String, List<String>> matchedUserDb= new ConcurrentHashMap<>();

    public boolean contains(String username) {
        return matchedUserDb.containsKey(username);
    }

    public void addUser(String username) {
        matchedUserDb.put(username, new ArrayList<>());
    }

    public void addMatchedUser(String username, String matchedUsername) {
        List<String> matcherUsers = matchedUserDb.get(username);
        matcherUsers.add(matchedUsername);
        matchedUserDb.replace(username, matcherUsers);
    }

    public void replaceMatchedUsers(String username, List<String> matchedUsers) {
        matchedUserDb.put(username, matchedUsers);
    }

    public List<String> getAllMatchedUsers(String username) {
        return matchedUserDb.get(username);
    }

    public void removeMatchedUser(String username, String usernameToRemove) {
        List<String> matchedUsers = matchedUserDb.get(username);
        matchedUsers.remove(usernameToRemove);
        matchedUserDb.replace(username, matchedUsers);
    }

    public void removeAllMatchedUsers(String username) {
        matchedUserDb.replace(username, new ArrayList<>());
    }
}
