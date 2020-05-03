package com.lustermaniacs.companion.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.lustermaniacs.companion.models.*;
import com.lustermaniacs.companion.repository.SurveyResponseRepository;
import com.lustermaniacs.companion.repository.UserRepository;
import com.lustermaniacs.companion.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SurveyResponseRepository surveyResponseRepository;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    public AuthenticationResponse addUser(User user) {
        if(userRepository.findByUsername(user.getUsername()) != null)
            throw new EntityExistsException();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()));
        Profile newProfile = new Profile();
        newUser.setProfile(newProfile);
        newProfile.setUser(newUser);
        userRepository.save(newUser);
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return new AuthenticationResponse(jwt);
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null)
            return user;
        else
            throw new EntityNotFoundException("User not found");
    }

    public User updateUserByUsername(String username, User newUser) {
        User oldUser = userRepository.findByUsername(username);
        User updatedUser;
        if (oldUser != null)
            updatedUser = oldUser;
        else
            throw new EntityNotFoundException("User not found");
        if((newUser.getUsername() != null) && (userRepository.findByUsername(newUser.getUsername()) == null))
            updatedUser.setUsername(newUser.getUsername());
        else
            throw new EntityExistsException();
        if(newUser.getPassword() != null) {
            updatedUser.setPassword(newUser.getPassword());
        }
        return userRepository.save(updatedUser);
    }

    public Profile setUserProfile(String username, Profile profile) throws IOException {
        User u = userRepository.findByUsername(username);
        if(u == null)
            throw new EntityNotFoundException("User not found");
        Profile newProfile = u.getProfile();
        if(profile.getFirstName() != null && !profile.getFirstName().isEmpty())
            newProfile.setFirstName(profile.getFirstName());
        if(profile.getLastName() != null && !profile.getLastName().isEmpty())
            newProfile.setLastName(profile.getLastName());
        if(profile.getGender() != 0)
            newProfile.setGender(profile.getGender());
        if(profile.getBio() != null)
            newProfile.setBio(profile.getBio());
        if(profile.getAge() != 0)
            newProfile.setAge(profile.getAge());
        if(profile.getLocation() != null && !profile.getLocation().isEmpty()) {
            newProfile.setLocation(profile.getLocation());
            double[] coordinates = obtainCoordinates(profile.getLocation());
            newProfile.setLatitude(coordinates[0]);
            newProfile.setLongitude(coordinates[1]);
        }
        u.setProfile(newProfile);
        userRepository.save(u);
        return newProfile;
    }

    @Transactional
    public void setSurvey(String username, SurveyResultsDTO results) {
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new EntityNotFoundException("User not found");
        Set<Integer> sport = results.getSport();
        if(sport != null && !sport.isEmpty()) {
            surveyResponseRepository.deleteByQuestionAndUser(Question.SPORT, user);
            for(Integer choice : sport) {
                SurveyResponse surveyResponse = new SurveyResponse(Question.SPORT, choice, user);
                surveyResponseRepository.save(surveyResponse);
            }
        }
        Set<Integer> food = results.getFood();
        if(food != null && !food.isEmpty()) {
            surveyResponseRepository.deleteByQuestionAndUser(Question.FOOD, user);
            for(Integer choice : food) {
                SurveyResponse surveyResponse = new SurveyResponse(Question.FOOD, choice, user);
                surveyResponseRepository.save(surveyResponse);
            }
        }
        Set<Integer> music = results.getMusic();
        if(music != null && !music.isEmpty()) {
            surveyResponseRepository.deleteByQuestionAndUser(Question.MUSIC, user);
            for(Integer choice : music) {
                SurveyResponse surveyResponse = new SurveyResponse(Question.MUSIC, choice, user);
                surveyResponseRepository.save(surveyResponse);
            }
        }
        Set<Integer> hobby = results.getHobby();
        if(hobby != null && !hobby.isEmpty()) {
            surveyResponseRepository.deleteByQuestionAndUser(Question.HOBBY, user);
            for(Integer choice : hobby) {
                SurveyResponse surveyResponse = new SurveyResponse(Question.HOBBY, choice, user);
                surveyResponseRepository.save(surveyResponse);
            }
        }
        if(results.getPersonalityType() != 0) {
            surveyResponseRepository.deleteByQuestionAndUser(Question.PERSONALITY, user);
            SurveyResponse surveyResponse = new SurveyResponse(Question.PERSONALITY, results.getPersonalityType(), user);
            surveyResponseRepository.save(surveyResponse);
        }
        if(results.getLikesAnimals() != 0) {
            surveyResponseRepository.deleteByQuestionAndUser(Question.ANIMALS, user);
            SurveyResponse surveyResponse = new SurveyResponse(Question.ANIMALS, results.getLikesAnimals(), user);
            surveyResponseRepository.save(surveyResponse);
        }
        if(results.getGenderPreference() != 0) {
            user.setGenderPref(results.getGenderPreference());
        }
        if(results.getMaxAge() != 0) {
            user.setMaxAge(results.getMaxAge());
        }
        if(results.getMinAge() != 0) {
            user.setMinAge(results.getMinAge());
        }
        if(results.getMaxDistance() != 0) {
            user.setMaxDistance(results.getMaxDistance());
        }
        userRepository.save(user);
    }

    public SurveyResultsDTO getSurvey(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null)
            throw new EntityNotFoundException("User not found");
        Set<Integer> sport = new HashSet<>();
        Set<Integer> food = new HashSet<>();
        Set<Integer> music = new HashSet<>();
        Set<Integer> hobby = new HashSet<>();
        List<SurveyResponse> sportTemp = surveyResponseRepository.findByQuestionAndUser(Question.SPORT, user);
        for(SurveyResponse surveyResponse : sportTemp)
            sport.add(surveyResponse.getChoice());
        List<SurveyResponse> foodTemp = surveyResponseRepository.findByQuestionAndUser(Question.FOOD, user);
        for(SurveyResponse surveyResponse : foodTemp)
            food.add(surveyResponse.getChoice());
        List<SurveyResponse> musicTemp = surveyResponseRepository.findByQuestionAndUser(Question.MUSIC, user);
        for(SurveyResponse surveyResponse : musicTemp)
            music.add(surveyResponse.getChoice());
        List<SurveyResponse> hobbyTemp = surveyResponseRepository.findByQuestionAndUser(Question.HOBBY, user);
        for(SurveyResponse surveyResponse : hobbyTemp)
            hobby.add(surveyResponse.getChoice());
        int personalityType = surveyResponseRepository.findByQuestionAndUser(Question.PERSONALITY, user).get(0).getChoice();
        int likesAnimals = surveyResponseRepository.findByQuestionAndUser(Question.ANIMALS, user).get(0).getChoice();
        int genderPreference = user.getGenderPref();
        int maxAge = user.getMaxAge();
        int minAge = user.getMinAge();
        int maxDistance = user.getMaxDistance();

        return new SurveyResultsDTO(sport, food, music, hobby, personalityType, likesAnimals, genderPreference, maxAge, minAge, maxDistance);
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
