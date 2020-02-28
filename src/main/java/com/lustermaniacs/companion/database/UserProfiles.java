package com.lustermaniacs.companion.database;


import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.SurveyResults;
import com.lustermaniacs.companion.models.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserProfiles implements UsrDB{
    private static HashMap<String, UUID> userID = new HashMap<>();
    private static HashMap<UUID,User>userDB = new HashMap<>();

    @Override
    public int addUser(User user){
        Profile newProfile = new Profile();
        SurveyResults newResults = new SurveyResults();
        UUID id = UUID.randomUUID();
        User newUser = new User(user.getUsername(), user.getPassword(), id, newProfile, newResults);
        if (!userID.containsKey(newUser.getUsername())) {
            userID.put(newUser.getUsername(), id);
            userDB.put(id, newUser);
            return 0;
        }
        else
            return 1;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userDB.values());
    }

    public Optional<User> getUserByUsername(String username){
        UUID uid = userID.get(username);
        return Optional.of(userDB.get(uid));
    }

    @Override
    public List<User> getMatchedUsers(String username) {
        List<User> sysmatchuser = new ArrayList<>();
        User usr = userDB.get(userID.get(username));
        List<String> matchlist = usr.getProfile().getSysmatchedUsers();
        for (String user : matchlist) {
            sysmatchuser.add(userDB.get(userID.get(user)));
        }
        return sysmatchuser;
    }

    @Override
    public int updateUserByUsername(String username, User user) {
        UUID uid = userID.get(username);
        User updatedUser = userDB.get(uid);
        if(user.getUsername() != null) {
            userID.remove(username);
            userID.put(user.getUsername(), uid);
            updatedUser.setUsername(user.getUsername());
        }
        if(user.getPassword() != null) {
            updatedUser.setPassword(user.getPassword());
        }
        userDB.replace(uid, updatedUser);
        return 0;
    }

    public void updateUserProfile(String username, Profile profile) {
        User userUpdate = userDB.get(userID.get(username));
        Profile newProfile = userUpdate.getProfile();
        if(profile.getFirstName() != null)
            newProfile.setFirstName(profile.getFirstName());
        if(profile.getLastName() != null)
            newProfile.setLastName(profile.getLastName());
        if(profile.getGender() != 0)
            newProfile.setGender(profile.getGender());
        if(profile.getBio() != null)
            newProfile.setBio(profile.getBio());
        if(profile.getAge() != null)
            newProfile.setAge(profile.getAge());
        if(profile.getLocation() != null)
            newProfile.setLocation(profile.getLocation());
        if(profile.getMaxDistance() != 0)
            newProfile.setMaxDistance(profile.getMaxDistance());
        if(profile.getProfilePic() != null)
            newProfile.setProfilePic(profile.getProfilePic());
       userUpdate.setProfile(newProfile);
        userDB.replace(userID.get(username), userUpdate);
    }

    public void setSurvey(String username, SurveyResults results) {
        User userUpdate = userDB.get(userID.get(username));
        userUpdate.setSurveyResults(results);
        userDB.replace(userID.get(username), userUpdate);
    }

    public int deleteUser(User user) {
        if (userID.remove(user.getUsername()) != null) {
            userDB.remove(user.getId());
            return 0;
        }
        else return 1;
    }

}
