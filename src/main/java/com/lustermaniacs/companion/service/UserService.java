package com.lustermaniacs.companion.service;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
import java.util.UUID;

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

    public void updateUserByUsername(String username, User newUser) {
        userDB.updateUserByUsername(username, newUser);
    }

    public List<User> matchingFiltering(String username) throws IOException {
        List<User> filteredUsers = new ArrayList<>();
        String baseURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=";
        String apiKey = "AIzaSyC0h0VwJX1pL1Atg1FervFfw4hQDovtNYY";
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
                curUser.getProfile().getSysmatchedUsers().add(user.getUsername());
                user.getProfile().getSysmatchedUsers().add(curUser.getUsername());
            }
        }

        //String origin = "New York, NY, USA";
        // String dest = "Washington, DC, USA";

        return filteredUsers;
    }
}
