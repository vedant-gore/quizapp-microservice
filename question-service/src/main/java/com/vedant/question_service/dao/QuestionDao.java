package com.vedant.question_service.dao;


import com.vedant.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {

    List<Question> findByCategory(String category);

    @Query(value="SELECT q.id FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :noOfQuestions", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, Integer noOfQuestions);


    @Query(value="SELECT * FROM question q WHERE q.id=:q.questionId", nativeQuery = true)
    Question findQuestionById(Integer questionId);


}
