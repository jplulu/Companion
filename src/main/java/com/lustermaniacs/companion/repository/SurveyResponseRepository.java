package com.lustermaniacs.companion.repository;

import com.lustermaniacs.companion.models.Question;
import com.lustermaniacs.companion.models.SurveyResponse;
import com.lustermaniacs.companion.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Integer> {
    @Query(value = "select count(*) from (select * from (select * from survey_responses sr where sr.user_id=?1 OR sr.user_id=?2) p group by p.question, p.choice having count(*) > 1) c", nativeQuery = true)
    int getNumMatches(Long user1Id, Long user2Id);

    List<SurveyResponse> findByQuestionAndUser(Question question, User user);

    void deleteByQuestionAndUser(Question question, User user);
}
