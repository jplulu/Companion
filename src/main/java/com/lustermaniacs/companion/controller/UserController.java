package com.lustermaniacs.companion.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
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
        if(userService.addUser(user) == 1) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }
        else
            return new ResponseEntity<>("Sign up successful", HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUserName(@PathVariable("username") String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        else {
            JSONObject error = new JSONObject();
            error.put("msg", "User does not exist");
            return new ResponseEntity<>(error.toString(), HttpStatus.NOT_FOUND);
        }

//        return userService.getUserByUsername(username)
//                .orElse(null);
    }

    @GetMapping("/{username}/matches")
    public List<User> getAllSysmatchUser(@PathVariable("username") String username) {
        return matchingService.getAllSysmatchUser(username);
    }

    @PutMapping("/{username}")
    public void updateUserByUsername(@PathVariable("username") String username, @RequestBody User user) {
        if (userService.updateUserByUsername(username, user) == 1)
            System.out.println("Username already exists, changes not made.");
    }

    @PutMapping("/{username}/profile")
    public void updateUserProfile(@PathVariable("username") String username, @RequestBody Profile profile) {
        int errCode = userService.updateUserProfile(username, profile);
        switch(errCode) {
            case 0:
                //System.out.println("Worked");
                break;
            case 1:
                //System.out.println("Profile was modified between changes ");
        }
    }

    @PutMapping("/{username}/survey")
    public void setSurvey(@PathVariable("username") String username, @RequestBody SurveyResults results) throws IOException {
        userService.setSurvey(username, results);
        matchingService.matchUsers(username);
    }
}
