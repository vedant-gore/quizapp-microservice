package com.vedant.question_service.service;


import com.vedant.question_service.dao.QuestionDao;
import com.vedant.question_service.model.Question;
import com.vedant.question_service.model.QuestionWrapper;
import com.vedant.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions(){
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category){
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionDao.save(question);
            new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("FAILED!", HttpStatus.BAD_REQUEST);
    }

    public boolean deleteQuestion(Integer quesNo) {
        if(questionDao.existsById(quesNo)){
            questionDao.deleteById(quesNo);
            return true;
        }
        else{
            return false;
        }

    }

    public boolean updateQuestion(Integer questionId, Question updatedQuestion) {
        if(questionDao.existsById(questionId)) {
            updatedQuestion.setId(questionId);
            questionDao.save(updatedQuestion);
            return true;
        }else{
            return false;
        }
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer noOfQuestions) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName, noOfQuestions);

        return ResponseEntity.status(HttpStatus.OK)
                            .body(questions);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(List<Integer> questionIds) {
        List<Question> questions = new ArrayList<>();

        for(Integer qId: questionIds){
            Question q = questionDao.findById(qId)
                    .orElseThrow(()->new NoSuchElementException("Question not found by ID: "+ qId));
            questions.add(q);
        }

        List<QuestionWrapper> questionsForUsers = new ArrayList<>();

        for(Question q: questions){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUsers.add(qw);
        }
//        for(Question q: questions){
//            QuestionWrapper wrapper = new QuestionWrapper();
//            wrapper.setId(q.getId());
//            wrapper.setQuestionTitle(q.getQuestionTitle());
//            wrapper.setOption1(q.getOption1());
//            wrapper.setOption2(q.getOption2());
//            wrapper.setOption3(q.getOption3());
//            wrapper.setOption4(q.getOption4());
//            questionsForUsers.add(wrapper);
//        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(questionsForUsers);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses){
        int right=0;

//        for (Response response : responses) {
//            Question question = questionDao.findById(response.getId()).get();
//            if (response.getResponse().equals(question.getRightAnswer())) {
//                right++;
//            }
//        }
        for (Response response : responses) {
            if (response.getQuestionId() == null) {
                throw new IllegalArgumentException("Question ID in response is null: " + response);
            }

            Question question = questionDao.findById(response.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found with id: " + response.getQuestionId()));

            if (question.getRightAnswer().equals(response.getResponse())) {
                right++;
            }
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
