package com.lustermaniacs.companion.controller;

import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.SurveyResults;
import com.lustermaniacs.companion.models.User;
import com.lustermaniacs.companion.service.MatchingService;
import com.lustermaniacs.companion.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;
    private final MatchingService matchingService;

    @Autowired
    public UserController(UserService userService, MatchingService matchingService) {
        this.userService = userService;
        this.matchingService = matchingService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        JSONObject resp = new JSONObject();
        if(userService.addUser(user) == 1) {
            resp.put("msg", "Username already exists!");
            return new ResponseEntity<>(resp.toString(), HttpStatus.CONFLICT);
        }
        else {
            resp.put("msg", "Sign up successful");
            return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUserName(@PathVariable("username") String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        else {
            JSONObject resp = new JSONObject();
            resp.put("msg", "User does not exist");
            return new ResponseEntity<>(resp.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{username}/matches")
    public ResponseEntity<?> getAllSysmatchUser(@PathVariable("username") String username) {
        List<Profile> matchedUsers = matchingService.getAllSysmatchUser(username);
        JSONObject resp = new JSONObject();
        if(matchedUsers == null) {
            resp.put("msg", "User does not exist");
            return new ResponseEntity<>(resp.toString(), HttpStatus.NOT_FOUND);
        }
        else if(matchedUsers.isEmpty()) {
            resp.put("msg", "No matched users found");
            return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(matchedUsers, HttpStatus.OK);
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserByUsername(@PathVariable("username") String username, @RequestBody User user) {
        JSONObject resp = new JSONObject();
        switch (userService.updateUserByUsername(username, user)) {
            default:
                resp.put("msg","User was successfully updated.");
                return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
            case 1:
                resp.put("msg","A user with the given name does not exists.");
                return new ResponseEntity<>(resp.toString(), HttpStatus.NOT_FOUND);
            case 2:
                resp.put("msg","Username already exists.");
                return new ResponseEntity<>(resp.toString(), HttpStatus.IM_USED);
        }
    }

    @PutMapping("/{username}/profile")
    public ResponseEntity<?> setUserProfile(@PathVariable("username") String username, @RequestBody Profile profile) throws IOException {
        int errCode = userService.setUserProfile(username, profile);
        JSONObject resp = new JSONObject();
        if(errCode == 0) {
            resp.put("msg", "Update successful");
            return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
        }
        else {
            resp.put("msg", "User does not exist");
            return new ResponseEntity<>(resp.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{username}/survey")
    public ResponseEntity<?> setSurvey(@PathVariable("username") String username, @RequestBody SurveyResults results) throws IOException {
        int errCode = userService.setSurvey(username, results);
        JSONObject resp = new JSONObject();
        if(errCode == 1) {
            resp.put("msg", "User does not exist");
            return new ResponseEntity<>(resp.toString(), HttpStatus.NOT_FOUND);
        }
        matchingService.matchUsers(username);
        resp.put("msg", "You have been matched");
        return new ResponseEntity<>(resp.toString(), HttpStatus.OK);
    }
}
