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
        User updatedusr = userDB.get(userID.get(username));
        if (user.getUsername() != null)
            updatedusr.setUsername(user.getUsername());
        if (user.getPassword() != null)
            updatedusr.setPassword(user.getPassword());
        if(user.getProfile() != null) {
            Profile newProfile = user.getProfile();
            if(newProfile.getFirstName() != null)
                updatedusr.getProfile().setFirstName(newProfile.getFirstName());
            if(newProfile.getLastName() != null)
                updatedusr.getProfile().setLastName(newProfile.getLastName());
            if(newProfile.getGender() != 0)
                updatedusr.getProfile().setGender(newProfile.getGender());
            if(newProfile.getBio() != null)
                updatedusr.getProfile().setBio(newProfile.getBio());
            if(newProfile.getAge() != null)
                updatedusr.getProfile().setAge(newProfile.getAge());
            if(newProfile.getLocation() != null)
                updatedusr.getProfile().setLocation(newProfile.getLocation());
            if(newProfile.getMaxDistance() != 0)
                updatedusr.getProfile().setMaxDistance(newProfile.getMaxDistance());
            if(newProfile.getProfilePic() != null)
                updatedusr.getProfile().setProfilePic(newProfile.getProfilePic());
            if(newProfile.getSurveyResults() != null)
                updatedusr.getProfile().setSurveyResults(newProfile.getSurveyResults());
            if(newProfile.getSysmatchedUsers() != null)
                updatedusr.getProfile().setSysmatchedUsers(newProfile.getSysmatchedUsers());
        }
        updatedusr.setId(uid);
        if (userDB.replace(userID.get(username), updatedusr) != null)
            return 0;
        else return 1;
    }

    public int deleteUser(User user) {
        if (userID.remove(user.getUsername()) != null) {
            userDB.remove(user.getId());
            return 0;
        }
        else return 1;
    }

    public void setSurvey(String username, SurveyResults results) {
        User userUpdate = userDB.get(userID.get(username));
        userUpdate.setSurveyResults(results);
        userDB.replace(userID.get(username), userUpdate);
    }
}
