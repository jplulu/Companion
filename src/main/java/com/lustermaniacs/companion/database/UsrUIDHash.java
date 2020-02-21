package com.lustermaniacs.companion.database;


import java.util.HashMap;
import java.util.UUID;

class UserProfiles implements UsrDB{
    HashMap<Integer, UUID>userID;
    HashMap<UUID,User>userDB;

    UserProfiles() {
        userID = new HashMap<Integer,UUID>;
        userDB = new HashMap<Integer,Integer>;
    }

    @Override
    public int addUser(User user){
        int namehash = user.username.hashCode();
        if (userID.put(namehash,user.uid) != null)
            return 1;
        return 0;
    }
    @Override
    public int userUpdate(String username){
        int namehash = username.hashCode();
        if(userDB.replace(userID.get(namehash), user) != null)
            return 0;
        else return 1;
    }
    @Override
    public int deleteUser(User user){
        if (userID.remove(user.username) != null) {
            userDB.remove(user.uid);
            return 0;
        }
        else return 1;
    }
    @Override
    public User getMatchedUsers(User user) {
        return null;
    }
}
