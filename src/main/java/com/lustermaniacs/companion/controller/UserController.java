package com.lustermaniacs.companion.controller;

import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.SurveyResults;
import com.lustermaniacs.companion.models.User;
import com.lustermaniacs.companion.repository.UserRepository;
import com.lustermaniacs.companion.service.MatchingService;
import com.lustermaniacs.companion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MatchingService matchingService;
    @Autowired
    private UserRepository userRepository;

    @Component("ValidUserCheck")
    public class ValidUserCheck{
        public boolean hasPermission(String username, String principal) {
            Profile profile = userRepository.findByUsername(principal).getProfile();
            if (username.equals(principal) || matchingService.getAllSysmatchUser(username).contains(profile))
                return true;
            else
                return false;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) throws EntityExistsException {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }
    @PreAuthorize("@ValidUserCheck.hasPermission(#username,authentication.principal.username)")
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUserName(@PathVariable("username") String username) throws EntityNotFoundException {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @GetMapping("/{username}/matches")
    public ResponseEntity<?> getAllSysmatchUser(@PathVariable("username") String username) throws EntityNotFoundException{
        List<Profile> matchedUserProfiles = matchingService.getAllSysmatchUser(username);
        if(matchedUserProfiles == null || matchedUserProfiles.isEmpty())
            return new ResponseEntity<>("No matches found :(", HttpStatus.OK);
        else
            return new ResponseEntity<>(matchedUserProfiles, HttpStatus.OK);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserByUsername(@PathVariable("username") String username, @RequestBody User user) throws EntityNotFoundException, EntityExistsException{
        return new ResponseEntity<>(userService.updateUserByUsername(username, user), HttpStatus.OK);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @PutMapping("/{username}/profile")
    public ResponseEntity<?> setUserProfile(@PathVariable("username") String username, @RequestBody Profile profile) throws IOException, EntityNotFoundException {
        return new ResponseEntity<>(userService.setUserProfile(username, profile), HttpStatus.OK);
    }

    @PreAuthorize("#username == authentication.principal.username")
    @PutMapping("/{username}/survey")
    public ResponseEntity<?> setSurvey(@PathVariable("username") String username, @RequestBody SurveyResults results) throws IOException, EntityNotFoundException {
        userService.setSurvey(username, results);
        List<Profile> matchedUserProfiles = matchingService.matchUsers(username);
        if(matchedUserProfiles == null || matchedUserProfiles.isEmpty())
            return new ResponseEntity<>("No matches found :(", HttpStatus.OK);
        else
            return new ResponseEntity<>(matchedUserProfiles, HttpStatus.OK);
    }
}
