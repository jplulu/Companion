package com.lustermaniacs.companion.service;

import com.lustermaniacs.companion.models.Match;
import com.lustermaniacs.companion.models.MatchStatus;
import com.lustermaniacs.companion.repository.*;
import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    public List<User> getAllSysmatchUser(String username) {
        User mainUser = userRepository.findByUsername(username);
        if(mainUser == null)
            throw new EntityNotFoundException("User not found");
        List<User> matchedUsers = matchRepository.findMatchedUsersByUser1(mainUser);
        matchedUsers.addAll(matchRepository.findMatchedUsersByUser2(mainUser));
        return matchedUsers;
    }

    @Transactional
    @Modifying
    public List<User> matchUsers(String username) {
        int THRESHOLD = 5;
        User mainUser = userRepository.findByUsername(username);
        if(mainUser == null)
            throw new EntityNotFoundException("User not found");
        matchRepository.setInactive(mainUser);
        //  Go through a filtered user database and attempt to get 100 matches
        List<User> filteredUsers = matchingFilter(mainUser);
        List<User> matchedUsers = new ArrayList<>();
        int count = 0;
        for (User user : filteredUsers) {
            if(surveyResponseRepository.getNumMatches(mainUser.getId(), user.getId()) >= THRESHOLD) {
                Match match1 = matchRepository.findByUser1AndUser2(mainUser, user);
                Match match2 = matchRepository.findByUser1AndUser2(user, mainUser);
                if(match1 == null && match2 == null) {
                    Match newMatch = new Match(mainUser, user, MatchStatus.ACTIVE);
                    matchRepository.save(newMatch);
                    matchedUsers.add(user);
                    count++;
                }
                else if(match1 != null && match1.getMatchStatus() != MatchStatus.REFUSED) {
                    match1.setMatchStatus(MatchStatus.ACTIVE);
                    matchRepository.save(match1);
                    matchedUsers.add(user);
                    count++;
                }
                else if(match2 != null && match2.getMatchStatus() != MatchStatus.REFUSED) {
                    match2.setMatchStatus(MatchStatus.ACTIVE);
                    matchRepository.save(match2);
                    matchedUsers.add(user);
                    count++;
                }
                if(count > 99)
                    break;
            }
        }
        return matchedUsers;
    }

    public void refuseMatch(String username, Long idToRefuse) {
        User mainUser = userRepository.findByUsername(username);
        if(mainUser == null)
            throw new EntityNotFoundException("User not found");
        Optional<User> testUser = userRepository.findById(idToRefuse);
        if(testUser.isEmpty())
            throw new EntityNotFoundException("User not found");
        User refusedUser = testUser.get();
        Match match1 = matchRepository.findByUser1AndUser2(mainUser, refusedUser);
        Match match2 = matchRepository.findByUser1AndUser2(refusedUser, mainUser);
        if(match1 == null && match2 == null)
            throw new EntityNotFoundException("Match not found");
        else if(match1 != null) {
            match1.setMatchStatus(MatchStatus.REFUSED);
            matchRepository.save(match1);
        }
        else {
            match2.setMatchStatus(MatchStatus.REFUSED);
            matchRepository.save(match2);
        }
    }

    public void activateMatch(String username, Long idToActivate) {
        User mainUser = userRepository.findByUsername(username);
        if(mainUser == null)
            throw new EntityNotFoundException("User not found");
        Optional<User> testUser = userRepository.findById(idToActivate);
        if(testUser.isEmpty())
            throw new EntityNotFoundException("User not found");
        User refusedUser = testUser.get();
        Match match1 = matchRepository.findByUser1AndUser2(mainUser, refusedUser);
        Match match2 = matchRepository.findByUser1AndUser2(refusedUser, mainUser);
        if(match1 == null && match2 == null)
            throw new EntityNotFoundException("Match not found");
        else if(match1 != null) {
            match1.setMatchStatus(MatchStatus.ACTIVE);
            matchRepository.save(match1);
        }
        else {
            match2.setMatchStatus(MatchStatus.ACTIVE);
            matchRepository.save(match2);
        }
    }

    // Function to filter out users who do not meet target person's criteria from their "matching pool"
    public List<User> matchingFilter(User mainUser) {
        List<User> filteredUsersByGenderAndAge;
        if(mainUser.getGenderPref() == 3)
            filteredUsersByGenderAndAge = profileRepository.filterByGenderPref3AndAge(mainUser.getId(), mainUser.getProfile().getGender(), mainUser.getMinAge(), mainUser.getMaxAge(), mainUser.getProfile().getAge());
        else
            filteredUsersByGenderAndAge = profileRepository.filterByGenderPref1Or2AndAge(mainUser.getId(), mainUser.getGenderPref(), mainUser.getProfile().getGender(), mainUser.getMinAge(), mainUser.getMaxAge(), mainUser.getProfile().getAge());
        return filterByLocation(mainUser, filteredUsersByGenderAndAge);
    }

    private List<User> filterByLocation(User curUser, List<User> filteredUsers) {
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
