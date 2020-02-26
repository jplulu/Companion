package com.lustermaniacs.companion.database;


import com.lustermaniacs.companion.models.Profile;
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
        UUID id = UUID.randomUUID();
        User newUser = new User(user.getUsername(), user.getPassword(), id, newProfile);
        if (!userID.containsKey(newUser.getUsername())) {
            userID.put(newUser.getUsername(), id);
            userDB.put(id, newUser);
            return 0;
        }
        else
            return 1;
    }

    public Optional<User> getUserByUsername(String username){
        UUID uid = userID.get(username);
        return Optional.of(userDB.get(uid));
    }

    @Override
    public List<User> getMatchedUsers(String username) {
        List<User> sysmatchuser = new ArrayList<>();
        User usr = userDB.get(userID.get(username));
        List<UUID> matchlist = usr.getProfile().getSysmatchedUsers();
        for (UUID uuid : matchlist) {
            sysmatchuser.add(userDB.get(uuid));
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

    public int deleteUser(User user){
        if (userID.remove(user.getUsername()) != null) {
            userDB.remove(user.getId());
            return 0;
        }
        else return 1;
    }

    public void setSurvey(String username, String[] results){
        User userUpdate = userDB.get(userID.get(username));
        userUpdate.getProfile().setSurveyResults(results);
        userDB.replace(userID.get(username), userUpdate);
    }
    // Function to match two given users based on a # of shared interest (threshold)
    private boolean matchTwoUsers(User usr1, User usr2, int threshold){
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

    private void matchUsers(String username){
        User mainUser = userDB.get(userID.get(username));
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
