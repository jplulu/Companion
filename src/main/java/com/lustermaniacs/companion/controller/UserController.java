package com.lustermaniacs.companion.controller;

import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.SurveyResultsDTO;
import com.lustermaniacs.companion.models.User;
import com.lustermaniacs.companion.service.MatchingService;
import com.lustermaniacs.companion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MatchingService matchingService;

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) throws EntityExistsException {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }

    @PreAuthorize("@ValidUserCheck.hasPermission(#username,authentication.principal.getUsername())")
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUserName(@PathVariable("username") String username) throws EntityNotFoundException {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserByUsername(@PathVariable("username") String username, @RequestBody User user) throws EntityNotFoundException, EntityExistsException {
        return new ResponseEntity<>(userService.updateUserByUsername(username, user), HttpStatus.OK);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @PutMapping("/{username}/profile")
    public ResponseEntity<?> setUserProfile(@PathVariable("username") String username, @RequestBody Profile profile) throws IOException, EntityNotFoundException {
        return new ResponseEntity<>(userService.setUserProfile(username, profile), HttpStatus.OK);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @PutMapping("/{username}/survey")
    public ResponseEntity<?> setSurvey(@PathVariable("username") String username, @RequestBody SurveyResultsDTO results) throws EntityNotFoundException {
        userService.setSurvey(username, results);
        List<User> matchedUsers = matchingService.matchUsers(username);
        if(matchedUsers == null || matchedUsers.isEmpty())
            return new ResponseEntity<>("No matches found :(", HttpStatus.OK);
        else
            return new ResponseEntity<>(matchedUsers, HttpStatus.OK);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @GetMapping("/{username}/survey")
    public ResponseEntity<?> getSurvey(@PathVariable("username") String username) throws EntityNotFoundException {
        return new ResponseEntity<>(userService.getSurvey(username), HttpStatus.OK);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @GetMapping("/{username}/matches")
    public ResponseEntity<?> getAllSysmatchUser(@PathVariable("username") String username) throws EntityNotFoundException {
        List<User> matchedUsers = matchingService.getAllSysmatchUser(username);
        return new ResponseEntity<>(matchedUsers, HttpStatus.OK);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @PutMapping("/{username}/matches")
    public ResponseEntity<?> modifyMatchStatus(@PathVariable("username") String username, @RequestParam("id") long id, @RequestParam("status") String status) throws EntityNotFoundException {
        if(status.equals("refuse")) {
            matchingService.refuseMatch(username, id);
            return ResponseEntity.ok()
                    .body("Match refused");
        }
        else if(status.equals("active")) {
            matchingService.activateMatch(username, id);
            return ResponseEntity.ok()
                    .body("Match activated");
        }
        else
            return ResponseEntity.badRequest().body("Invalid action");
    }

}
