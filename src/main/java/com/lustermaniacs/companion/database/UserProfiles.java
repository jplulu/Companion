package com.lustermaniacs.companion.database;


import com.lustermaniacs.companion.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

class UserProfiles implements UsrDB{
    HashMap<String, UUID>userID;
    HashMap<UUID,User>userDB;

    UserProfiles() {
        userID = new HashMap<String,UUID>();
        userDB = new HashMap<UUID,User>();
    }

    @Override
    public int addUser(User user){
        if (userID.put(user.getUsername(),user.getId()) != null)
            return 1;
        return 0;
    }
    @Override
    public int updateUserByUsername(String username) {
        User user = getUserByUsername(username);
        if (userDB.replace(userID.get(username), user) != null)
            return 0;
        else return 1;
    }
    public int deleteUser(User user){
        if (userID.remove(user.getUsername()) != null) {
            userDB.remove(user.getId());
            return 0;
        }
        else return 1;
    }
    @Override
    public List<User> getMatchedUsers(String username) {
        List<User> sysmatchuser = new ArrayList<User>();
        User usr = userDB.get(userID.get(username));
        List<UUID> matchlist = usr.getProfile().getSysmatchedUsers();
        for (int i = 0; i < matchlist.size(); i++){
            sysmatchuser.add(userDB.get(matchlist.get(i)));
        }
        return sysmatchuser;
    }
    public User getUserByUsername(String username){
        return userDB.get(userID.get(username));
    }
}
