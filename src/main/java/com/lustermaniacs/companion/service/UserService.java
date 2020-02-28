package com.lustermaniacs.companion.service;

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

    public Optional<User> getUserByID(String username) {
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

    // Function to filter out users who do not meet target person's criteria from their "matching pool"
    public List<User> matchingFiltering(String username) throws IOException {
        List<User> filteredUsers = new ArrayList<>();
        String baseURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=";
        String apiKey = "";
        User curUser = userDB.getUserByUsername(username).get();
        String origin = curUser.getProfile().getLocation();
        int curMaxDist = curUser.getProfile().getMaxDistance();
        List<User> allUsers = userDB.getAllUsers();

        for(User user: allUsers) {
            String dest = user.getProfile().getLocation();
            int maxDist = Math.min(curMaxDist, user.getProfile().getMaxDistance());
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
            if(distance <= maxDist) {
                filteredUsers.add(user);
            }
        }

        //String origin = "New York, NY, USA";
        // String dest = "Washington, DC, USA";

        return filteredUsers;
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
            usr2.addSysMatchUser(usr1.getUsername());
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
                matchedUser.getProfile().delSysMatchUser(mainUser.getUsername());
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
