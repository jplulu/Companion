package com.lustermaniacs.companion.repository;

import com.lustermaniacs.companion.models.Match;
import com.lustermaniacs.companion.models.Profile;
import com.lustermaniacs.companion.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    int countByUser1OrUser2(User user1, User user2);

    @Modifying
    @Query(value = "update Match set matchStatus='INACTIVE' where matchStatus<>'REFUSED' and (user1=?1 or user2=?1)" )
    void setInactive(User user);

    Match findByUser1AndUser2(User user1, User user2);

    @Query(value = "select m.user2 from Match m where m.user1=?1 and m.matchStatus='ACTIVE'")
    List<User> findMatchedUsersByUser1(User user1);

    @Query(value = "select m.user1 from Match m where m.user2=?1 and m.matchStatus='ACTIVE'")
    List<User> findMatchedUsersByUser2(User user2);
}
