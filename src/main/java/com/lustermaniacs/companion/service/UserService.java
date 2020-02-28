package com.lustermaniacs.companion.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.lustermaniacs.companion.database.UsrDB;
import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.SurveyResults;
import com.lustermaniacs.companion.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Service
public class UserService {

    private final UsrDB userDB;

    @Autowired
    public UserService(UsrDB userDB) {
        this.userDB = userDB;
    }

    public int addUser(User user) {
        return userDB.addUser(user);
    }

    public Optional<User> getUserByUsername(String username) {
        return userDB.getUserByUsername(username);
    }

    public List<User> getAllSysmatchUser(String username) {
        return userDB.getMatchedUsers(username);
    }

    public int updateUserByUsername(String username, User newUser) {
        return userDB.updateUserByUsername(username, newUser);
    }

    public int updateUserProfile(String username, Profile profile) {
        return userDB.updateUserProfile(username, profile);
    }

    public void setSurvey(String username, SurveyResults results) throws IOException {
        userDB.setSurvey(username, results);
        matchUsers(username);
    }

    public List<User> filterByGender(User curUser) {
        List<User> filteredUsers = new ArrayList<>();
        List<User> allUsers = userDB.getAllUsers();
        byte curUserPreference = curUser.getSurveyResults().getGenderPreference();
        char curUserGender = curUser.getProfile().getGender();
        if(curUserPreference == 3) {
            for(User user : allUsers) {
                if(user.getId() == curUser.getId())
                    continue;
                byte userPreference = user.getSurveyResults().getGenderPreference();
                if(userPreference == 3)
                    filteredUsers.add(user);
                else if((userPreference == 1) && (curUserGender == 1))
                    filteredUsers.add(user);
                else if((userPreference == 2) && (curUserGender == 2))
                    filteredUsers.add(user);
            }
        }
        else if(curUserPreference == 1) {
            for(User user : allUsers) {
                if(user.getId() == curUser.getId())
                    continue;
                if(user.getProfile().getGender() == 1) {
                    byte userPreference = user.getSurveyResults().getGenderPreference();
                    if(userPreference == 3)
                        filteredUsers.add(user);
                    else if((userPreference == 1) && (curUserGender == 1))
                        filteredUsers.add(user);
                    else if((userPreference == 2) && (curUserGender == 2))
                        filteredUsers.add(user);
                }
            }
        }
        else if(curUserPreference == 2) {
            for(User user : allUsers) {
                if(user.getId() == curUser.getId())
                    continue;
                if(user.getProfile().getGender() == 2) {
                    byte userPreference = user.getSurveyResults().getGenderPreference();
                    if(userPreference == 3)
                        filteredUsers.add(user);
                    else if((userPreference == 1) && (curUserGender == 1))
                        filteredUsers.add(user);
                    else if((userPreference == 2) && (curUserGender == 2))
                        filteredUsers.add(user);
                }
            }
        }

        return filteredUsers;
    }

    public List<User> filterByAge(User curUser, List<User> filteredUsers) {
        List<User> newFilteredUsers = new ArrayList<>();
        int curUserAge = curUser.getProfile().getAge();
        int curUserMinAge = curUser.getSurveyResults().getMinAge();
        int curUserMaxAge = curUser.getSurveyResults().getMinAge();
        for(User user : filteredUsers) {
            if(user.getId() == curUser.getId())
                continue;
            int userAge = user.getProfile().getAge();
            if(userAge >= curUserMinAge && userAge <= curUserMaxAge) {
                if(curUserAge >= user.getSurveyResults().getMinAge() && curUserAge <= user.getSurveyResults().getMaxAge())
                    newFilteredUsers.add(user);
            }
        }
        return newFilteredUsers;
    }

    public List<User> filterByLocation(User curUser, List<User> filteredUsers) throws IOException {
        List<User> newFilteredUsers = new ArrayList<>();

        String baseURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=";
        String apiKey = "";
        String origin = curUser.getProfile().getLocation();
        int curMaxDist = curUser.getProfile().getMaxDistance();

        for(User user : filteredUsers) {
            if(user.getId() == curUser.getId())
                continue;
            String dest = user.getProfile().getLocation();
            String url = baseURL + URLEncoder.encode(origin, StandardCharsets.UTF_8) +
                    "&destinations=" + URLEncoder.encode(dest, StandardCharsets.UTF_8) +
                    "&key=" + apiKey;
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(response.toString());
            ArrayNode rows = (ArrayNode) jsonResponse.path("rows");
            ArrayNode element = (ArrayNode) rows.get(0).get("elements");
            int distance = (int) ((element.get(0).path("distance").path("value").asInt()) * 0.00062137119);
            if(distance <= Math.min(curMaxDist, user.getProfile().getMaxDistance())) {
                newFilteredUsers.add(user);
            }
        }

        return newFilteredUsers;
    }

    // Function to filter out users who do not meet target person's criteria from their "matching pool"
    public List<User> matchingFilter(String username) throws IOException {
        User curUser = userDB.getUserByUsername(username).get();
        List<User> filteredUsersByGender = filterByGender(curUser);
        List<User> filteredUsersByAge = filterByAge(curUser, filteredUsersByGender);
        return filterByLocation(curUser, filteredUsersByAge);
    }

    // Function to match two given users based on a # of shared interest (threshold)
    public boolean matchTwoUsers(User usr1, User usr2, int threshold){
        // Initialize a variable to keep track of matches
        int numMatches = 0;
        //  Compare profile parameters and count how many same results
        List<String> usr1Sport = new ArrayList<>(usr1.getSurveyResults().getSportsAnswers());
        usr1Sport.retainAll(usr2.getSurveyResults().getSportsAnswers());
        List<String> usr1Food = new ArrayList<>(usr1.getSurveyResults().getFoodAnswers());
        usr1Food.retainAll(usr2.getSurveyResults().getFoodAnswers());
        List<String> usr1Music = new ArrayList<>(usr1.getSurveyResults().getMusicAnswers());
        usr1Music.retainAll(usr2.getSurveyResults().getMusicAnswers());
        List<String> usr1Hobby = new ArrayList<>(usr1.getSurveyResults().getHobbyAnswers());
        usr1Hobby.retainAll(usr2.getSurveyResults().getHobbyAnswers());

        numMatches += usr1Sport.size();
        numMatches += usr1Food.size();
        numMatches += usr1Music.size();
        numMatches += usr1Hobby.size();

        if (usr1.getSurveyResults().getPersonalityType() == usr2.getSurveyResults().getPersonalityType())
            numMatches++;
        if (usr1.getSurveyResults().getLikesAnimals() == usr2.getSurveyResults().getLikesAnimals())
            numMatches++;

        if (numMatches >= threshold) {
            usr2.addSysmatchedUser(usr1.getUsername());
            return true;
        }
        else
            return false;
    }

    public void matchUsers(String username) throws IOException {
        User mainUser = getUserByUsername(username).get();
        // Check if already matched and remove from other peoples
        if (mainUser.getProfile().getSysmatchedUsers().isEmpty()) {
            List<String> userMatches = mainUser.getProfile().getSysmatchedUsers();
            //Iterate through user matches and have matched users delete main user from their lists
            for (int i = 0; i > userMatches.size(); i++) {
                User matchedUser = getUserByUsername(userMatches.get(i)).get();
                matchedUser.removeSysmatchedUser(mainUser.getUsername());
            }
        }
        //  Go through a filtered user database and attempt to get 100 matches
        List<User> filteredDB = matchingFiltering(username);
        List<String> matchedUsers = new ArrayList<>();
        for(int i = 0; i < filteredDB.size() ; i++) {
            //match main user with all filtered users and create match if threshold of 4 matches reached
            if (matchTwoUsers(mainUser, filteredDB.get(i), 4))
                matchedUsers.add(filteredDB.get(i).getUsername());
            //once 100 matches made, stop
            if (matchedUsers.size() > 99)
                break;
        }
        mainUser.getProfile().setSysmatchedUsers(matchedUsers);
    }
}
