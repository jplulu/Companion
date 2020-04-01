package com.lustermaniacs.companion.repository;

import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query(value = "select u from Profile p inner join p.user u on u.id<>?1 AND p.user=u AND (u.genderPref=?2 OR u.genderPref=3) AND (p.age BETWEEN ?3 AND ?4) AND (?5 BETWEEN u.minAge AND u.maxAge)")
    List<User> filterByGenderPref3AndAge(long id, int gender, int minAge, int maxAge, int age);

    @Query(value = "select u from Profile p inner join p.user u on u.id<>?1 AND p.user=u AND p.gender=?2 AND (u.genderPref=?3 OR u.genderPref=3) AND (p.age BETWEEN ?4 AND ?5) AND (?6 BETWEEN u.minAge AND u.maxAge)")
    List<User> filterByGenderPref1Or2AndAge(Long id, int genderPref, int gender, int minAge, int maxAge, int age);
}
