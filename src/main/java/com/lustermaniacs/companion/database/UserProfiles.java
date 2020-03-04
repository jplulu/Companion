package com.lustermaniacs.companion.database;


import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.SurveyResults;
import com.lustermaniacs.companion.models.User;
import org.springframework.stereotype.Repository;

import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserProfiles implements UsrDB {
    private static ConcurrentHashMap<String, UUID> userID = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<UUID,User>userDB = new ConcurrentHashMap<>();

    @Override
    public int addUser(User user){
        Profile newProfile = new Profile();
        SurveyResults newResults = new SurveyResults();
        UUID id = UUID.randomUUID();
        User newUser = new User(user.getUsername(), user.getPassword(), id, newProfile, newResults);
        //  putIfAbsent to ensure atomicity for ID hashmap
        //  if null returned, success
        //  otherwise, something already exists there
        if (userID.putIfAbsent(newUser.getUsername(), id) == null) {
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
        if(uid == null)
            return Optional.empty();
        return Optional.of(userDB.get(uid));
    }

    @Override
    public int updateUserByUsername(String username, User user) {
        // Return Code
        // 0 -- The user was successfully updated
        // 1 -- A user with the given username does not exist.
        // 2 -- This username already exists
        User updatedUser = null;
        if (!getUserByUsername(username).isEmpty()) {
            updatedUser = getUserByUsername(username).get();
        }
        else
            return 1;
        // if username stated for change then change otherwise, it will be read as null and not changed
        if(user.getUsername() != null) {
            //  If name being updated is same name, then just dont do anything
            //  Check if usernames are the same
            if(updatedUser.getUsername().equals(username)) {
                if (userID.putIfAbsent(user.getUsername(), updatedUser.getId()) != null) {
                    return 2;
                }
                userID.remove(username);
                updatedUser.setUsername(user.getUsername());
            }
        }
        if(user.getPassword() != null) {
            updatedUser.setPassword(user.getPassword());
        }
        userDB.replace(updatedUser.getId(), updatedUser);
        return 0;
    }

    public int updateUserProfile(String username, Profile profile) {
        Optional<User> testUser = getUserByUsername(username);
        if(testUser.isEmpty())
            return 1;
        User userUpdate = testUser.get();
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
        if(profile.getProfilePic() != null)
            newProfile.setProfilePic(profile.getProfilePic());
        userUpdate.setProfile(newProfile);
        //  Check to see if the profile changed before replacing it
        userDB.replace(userID.get(username), userUpdate);
        return 0;
    }

    public int setSurvey(String username, SurveyResults results) {
        Optional<User> testUser = getUserByUsername(username);
        if(testUser.isEmpty())
            return 1;
        User userUpdate = testUser.get();

        SurveyResults newSurveyResults = userUpdate.getSurveyResults();
        if (results.getSportsAnswers() != null)
            newSurveyResults.setSportsAnswers(results.getSportsAnswers());
        if (results.getFoodAnswers() != null)
            newSurveyResults.setFoodAnswers(results.getFoodAnswers());
        if (results.getMusicAnswers() != null)
            newSurveyResults.setMusicAnswers(results.getMusicAnswers());
        if (results.getHobbyAnswers() != null)
            newSurveyResults.setHobbyAnswers(results.getHobbyAnswers());
        if (results.getPersonalityType() != 0)
            newSurveyResults.setPersonalityType(results.getPersonalityType());
        if (results.getLikesAnimals() != 0)
            newSurveyResults.setLikesAnimals(results.getLikesAnimals());
        if (results.getGenderPreference() != 0)
            newSurveyResults.setGenderPreference(results.getGenderPreference());
        if (results.getMinAge() != 0)
            newSurveyResults.setMinAge(results.getMinAge());
        if (results.getMaxAge() != 0)
            newSurveyResults.setMaxAge(results.getMaxAge());

        userUpdate.setSurveyResults(results);
        userDB.replace(userID.get(username), userUpdate);
        return 0;
    }

//    public int deleteUser(User user) {
//        // Check if mapping exists for delete in the first place
//        if (userID.remove(user.getUsername()) != null) {
//            userDB.remove(user.getId());
//            return 0;
//        }
//        else return 1;
//    }

}
