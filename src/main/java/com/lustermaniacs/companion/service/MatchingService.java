package com.lustermaniacs.companion.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.lustermaniacs.companion.database.MatchedUsersDB;
import com.lustermaniacs.companion.database.UsrDB;
import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchingService {
    private final MatchedUsersDB matchedUsersDB;
    private final UsrDB userDB;

    @Autowired
    public MatchingService(MatchedUsersDB matchedUsersDB, UsrDB userDB) {
        this.matchedUsersDB = matchedUsersDB;
        this.userDB = userDB;
    }

    public List<Profile> getAllSysmatchUser(String username) {
        if(!matchedUsersDB.contains(username))
            return null;
        List<Profile> matchedUsers = new ArrayList<>();
        List<String> matchedUsernames = matchedUsersDB.getAllMatchedUsers(username);
        for(String matchedUsername : matchedUsernames) {
            Optional<User> curUser = userDB.getUserByUsername(matchedUsername);
            if(curUser.isPresent())
                matchedUsers.add(curUser.get().getProfile());
            else
                matchedUsersDB.removeMatchedUser(username, matchedUsername);
        }
        return matchedUsers;
    }

    // Function to filter out users who do not meet target person's criteria from their "matching pool"
    public List<User> matchingFilter(User mainUser) throws IOException {
        List<User> filteredUsersByGender = filterByGender(mainUser);
        List<User> filteredUsersByAge = filterByAge(mainUser, filteredUsersByGender);
        return filterByLocation(mainUser, filteredUsersByAge);
    }


    public int matchUsers(String username) throws IOException {
        Optional<User> testUser = userDB.getUserByUsername(username);
        User mainUser;
        if(testUser.isPresent())
            mainUser = testUser.get();
        else
            return 1;
        // Check if already matched and remove from other peoples
        if (!matchedUsersDB.getAllMatchedUsers(username).isEmpty()) {
            List<String> userMatches = matchedUsersDB.getAllMatchedUsers(username);
            //Iterate through user matches and have matched users delete main user from their lists
            for (String userMatch : userMatches) {
                matchedUsersDB.removeMatchedUser(userMatch, username);
            }
        }
        //  Go through a filtered user database and attempt to get 100 matches
        List<User> filteredDB = matchingFilter(mainUser);
        List<String> matchedUsers = new ArrayList<>();
        for (User user : filteredDB) {
            //match main user with all filtered users and create match if threshold of 4 matches reached
            if (matchTwoUsers(mainUser, user, 4))
                matchedUsers.add(user.getUsername());
            //once 100 matches made, stop
            if (matchedUsers.size() > 99)
                break;
        }
        matchedUsersDB.replaceMatchedUsers(username, matchedUsers);
        return 0;
    }

    // Function to match two given users based on a # of shared interest (threshold)
    private boolean matchTwoUsers(User usr1, User usr2, int threshold){
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
            matchedUsersDB.addMatchedUser(usr2.getUsername(), usr1.getUsername());
            return true;
        }
        else
            return false;
    }

    private List<User> filterByGender(User curUser) {
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
                else if((userPreference == 1) && (curUserGender == '1'))
                    filteredUsers.add(user);
                else if((userPreference == 2) && (curUserGender == '2'))
                    filteredUsers.add(user);
            }
        }
        else if(curUserPreference == 1) {
            for(User user : allUsers) {
                if(user.getId() == curUser.getId())
                    continue;
                if(user.getProfile().getGender() == '1') {
                    byte userPreference = user.getSurveyResults().getGenderPreference();
                    if(userPreference == 3)
                        filteredUsers.add(user);
                    else if((userPreference == 1) && (curUserGender == '1'))
                        filteredUsers.add(user);
                    else if((userPreference == 2) && (curUserGender == '2'))
                        filteredUsers.add(user);
                }
            }
        }
        else if(curUserPreference == 2) {
            for(User user : allUsers) {
                if(user.getId() == curUser.getId())
                    continue;
                if(user.getProfile().getGender() == '2') {
                    byte userPreference = user.getSurveyResults().getGenderPreference();
                    if(userPreference == 3)
                        filteredUsers.add(user);
                    else if((userPreference == 1) && (curUserGender == '1'))
                        filteredUsers.add(user);
                    else if((userPreference == 2) && (curUserGender == '2'))
                        filteredUsers.add(user);
                }
            }
        }
        return filteredUsers;
    }

    private List<User> filterByAge(User curUser, List<User> filteredUsers) {
        List<User> newFilteredUsers = new ArrayList<>();
        int curUserAge = curUser.getProfile().getAge();
        int curUserMinAge = curUser.getSurveyResults().getMinAge();
        int curUserMaxAge = curUser.getSurveyResults().getMaxAge();
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

    private List<User> filterByLocation(User curUser, List<User> filteredUsers) throws IOException {
        List<User> newFilteredUsers = new ArrayList<>();

        String baseURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=";
        String apiKey = "AIzaSyAZ-dpSMRPBPF_wQwHUy0AziKmT2KRcpOs";
        String origin = curUser.getProfile().getLocation();
        int curMaxDist = curUser.getSurveyResults().getMaxDistance();

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
            if(distance <= Math.min(curMaxDist, user.getSurveyResults().getMaxDistance())) {
                newFilteredUsers.add(user);
            }
        }
        return newFilteredUsers;
    }


}
