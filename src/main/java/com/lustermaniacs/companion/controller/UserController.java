package com.lustermaniacs.companion.controller;

import com.lustermaniacs.companion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        userService.addUser(user);
    }

    @GetMapping("/{username}")
    public User getUserByUserName(@PathVariable("username") String username) {
        return userService.getUserByUsername(username)
                .orElse(null);
    }

    @GetMapping("/{username}/sysmatchedusers")
    public List<User> getAllSysmatchUser(@PathVariable("username") String username) {
        return userService.getAllSysmatchUser(username);
    }

    @PutMapping("/{username}")
    public void updateUserByUsername(@PathVariable("username") String username, @RequestBody User user) {
        userService.updateUserByUsername(username, user);
    }

}