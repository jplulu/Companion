package com.lustermaniacs.companion.repository;

import com.lustermaniacs.companion.models.Match;
import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    int countByUser1OrUser2(User user1, User user2);
    void deleteByUser1OrUser2(User user1, User user2);

    @Query(value = "select m.user2 from Match m where m.user1=?1")
    List<User> findMatchedUsersByUser1(User user1);

    @Query(value = "select m.user1 from Match m where m.user2=?1")
    List<User> findMatchedUsersByUser2(User user2);
}
