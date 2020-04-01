package com.lustermaniacs.companion.service;

import com.lustermaniacs.companion.models.Match;
import com.lustermaniacs.companion.repository.*;
import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Transient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatchingService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private SurveyResponseRepository surveyResponseRepository;

    public List<Profile> getAllSysmatchUser(String username) {
        User mainUser = userRepository.findByUsername(username);
        if(mainUser == null)
            throw new EntityNotFoundException();
        List<User> matchedUsers = matchRepository.findMatchedUsersByUser1(mainUser);
        matchedUsers.addAll(matchRepository.findMatchedUsersByUser2(mainUser));
        List<Profile> matchedUserProfiles = new ArrayList<>();
        for(User u : matchedUsers)
            matchedUserProfiles.add(u.getProfile());
        return matchedUserProfiles;
    }

    @Transactional
    public List<Profile> matchUsers(String username) throws IOException {
        int THRESHOLD = 5;
        User mainUser = userRepository.findByUsername(username);
        if(mainUser == null)
            throw new EntityNotFoundException();
        // Check if already matched and remove from other peoples
        matchRepository.deleteByUser1OrUser2(mainUser, mainUser);
        //  Go through a filtered user database and attempt to get 100 matches
        List<User> filteredUsers = matchingFilter(mainUser);
        List<Profile> matchedUserProfiles = new ArrayList<>();
        for (User user : filteredUsers) {
            if(surveyResponseRepository.getNumMatches(mainUser.getId(), user.getId()) >= THRESHOLD) {
                Match newMatch = new Match(mainUser, user);
                matchRepository.save(newMatch);
                matchedUserProfiles.add(user.getProfile());
            }
            //once 100 matches made, stop
            if (matchRepository.countByUser1OrUser2(mainUser, mainUser) > 99)
                break;
        }
        return matchedUserProfiles;
    }

//    // Function to match two given users based on a # of shared interest (threshold)
//    private boolean matchTwoUsers(User usr1, User usr2, int threshold){
//        // Initialize a variable to keep track of matches
//        int numMatches = 0;
//        //  Compare profile parameters and count how many same results
//        List<String> usr1Sport = new ArrayList<>(usr1.getSurveyResults().getSportsAnswers());
//        usr1Sport.retainAll(usr2.getSurveyResults().getSportsAnswers());
//        List<String> usr1Food = new ArrayList<>(usr1.getSurveyResults().getFoodAnswers());
//        usr1Food.retainAll(usr2.getSurveyResults().getFoodAnswers());
//        List<String> usr1Music = new ArrayList<>(usr1.getSurveyResults().getMusicAnswers());
//        usr1Music.retainAll(usr2.getSurveyResults().getMusicAnswers());
//        List<String> usr1Hobby = new ArrayList<>(usr1.getSurveyResults().getHobbyAnswers());
//        usr1Hobby.retainAll(usr2.getSurveyResults().getHobbyAnswers());
//
//        numMatches += usr1Sport.size();
//        numMatches += usr1Food.size();
//        numMatches += usr1Music.size();
//        numMatches += usr1Hobby.size();
//
//        if (usr1.getSurveyResults().getPersonalityType() == usr2.getSurveyResults().getPersonalityType())
//            numMatches++;
//        if (usr1.getSurveyResults().getLikesAnimals() == usr2.getSurveyResults().getLikesAnimals())
//            numMatches++;
//
//        if (numMatches >= threshold) {
//            matchedUsersDB.addMatchedUser(usr2.getUsername(), usr1.getUsername());
//            return true;
//        }
//        else
//            return false;
//    }

    // Function to filter out users who do not meet target person's criteria from their "matching pool"
    public List<User> matchingFilter(User mainUser) throws IOException {
        List<User> filteredUsersByGenderAndAge;
        if(mainUser.getGenderPref() == 3)
            filteredUsersByGenderAndAge = profileRepository.filterByGenderPref3AndAge(mainUser.getId(), mainUser.getProfile().getGender(), mainUser.getMinAge(), mainUser.getMaxAge(), mainUser.getProfile().getAge());
        else
            filteredUsersByGenderAndAge = profileRepository.filterByGenderPref1Or2AndAge(mainUser.getId(), mainUser.getGenderPref(), mainUser.getProfile().getGender(), mainUser.getMinAge(), mainUser.getMaxAge(), mainUser.getProfile().getAge());
        return filterByLocation(mainUser, filteredUsersByGenderAndAge);
    }

    private List<User> filterByLocation(User curUser, List<User> filteredUsers) throws IOException {
        List<User> newFilteredUsers = new ArrayList<>();
        int curMaxDist = curUser.getMaxDistance();

        for(User user : filteredUsers) {
            int distance = (int) distance(curUser.getProfile().getLatitude(), curUser.getProfile().getLongitude(), user.getProfile().getLatitude(), user.getProfile().getLongitude());
            if(distance <= Math.min(curMaxDist, user.getMaxDistance())) {
                newFilteredUsers.add(user);
            }
        }
        return newFilteredUsers;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            return dist;
        }
    }
}
