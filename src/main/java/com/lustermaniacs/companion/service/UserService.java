package com.lustermaniacs.companion.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.lustermaniacs.companion.database.MatchedUsersDB;
import com.lustermaniacs.companion.database.UsrDB;
import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.SurveyResults;
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
import java.util.*;


@Service
public class UserService {

    private final UsrDB userDB;
    private final MatchedUsersDB matchedUsersDB;

    @Autowired
    public UserService(UsrDB userDB, MatchedUsersDB matchedUsersDB) {
        this.userDB = userDB;
        this.matchedUsersDB = matchedUsersDB;
    }

    public int addUser(User user) {
        if(userDB.addUser(user) == 1)
            return 1;
        matchedUsersDB.addUser(user.getUsername());
        return 0;
    }

    public Optional<User> getUserByUsername(String username) {
        return userDB.getUserByUsername(username);
    }

    public int updateUserByUsername(String username, User newUser) {
        return userDB.updateUserByUsername(username, newUser);
    }

    public int setUserProfile(String username, Profile profile) throws IOException {
        double[] coordinates = obtainCoordinates(profile.getLocation());
        profile.setLatitude(coordinates[0]);
        profile.setLongitude(coordinates[1]);
        return userDB.setUserProfile(username, profile);
    }

    public int setSurvey(String username, SurveyResults results) {
        return userDB.setSurvey(username, results);
    }

    private double[] obtainCoordinates(String location) throws IOException {
        double[] coordinates = new double[2];
        String baseURL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
        String apiKey = "AIzaSyAZ-dpSMRPBPF_wQwHUy0AziKmT2KRcpOs";
        String url = baseURL + URLEncoder.encode(location, StandardCharsets.UTF_8) + "&key=" + apiKey;
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
        ArrayNode result = (ArrayNode) jsonResponse.path("results");
        JsonNode coordinate = result.get(0).path("geometry").path("location");
        coordinates[0] = coordinate.path("lat").asDouble();
        coordinates[1] = coordinate.path("lng").asDouble();
        return coordinates;
    }
}
