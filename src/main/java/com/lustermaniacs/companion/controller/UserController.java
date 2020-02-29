package com.lustermaniacs.companion.controller;

import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.SurveyResults;
import com.lustermaniacs.companion.models.User;
import com.lustermaniacs.companion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void addUser(@RequestBody User user) {
        if(userService.addUser(user) == 1) {
            System.out.println("Username already exists");
        }
    }

    @GetMapping("/{username}")
    public Optional<User> getUserByUserName(@PathVariable("username") String username) {
        if (userService.getUserByUsername(username).isPresent())
            return userService.getUserByUsername(username);
        else {
            System.out.println("User does not exists");
            return Optional.empty();
        }

//        return userService.getUserByUsername(username)
//                .orElse(null);
    }

    @GetMapping("/{username}/sysmatchedusers")
    public List<User> getAllSysmatchUser(@PathVariable("username") String username) {
        return userService.getAllSysmatchUser(username);
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
    }

}
