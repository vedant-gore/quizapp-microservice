package com.vedant.quiz_service.service;



import com.vedant.quiz_service.dao.QuizDao;
import com.vedant.quiz_service.feign.QuizInterface;
import com.vedant.quiz_service.model.QuestionWrapper;
import com.vedant.quiz_service.model.Quiz;
import com.vedant.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;


    /* **INTERACTION BETWEEN TWO DIFFERENT MICROSERVICES** */
    public ResponseEntity<String> createQuiz(String category, Integer noOfQuestions, String title){

        //call the "generate" url. Communication between two microservices - done using - RestTemplate class - http://localhost:8080/question/generate
        //we do not know where our question microservices instance is running - cloud? local-server? if cloud? need IP Address. Also we don't want to hardcode the port number in app.properties. We have to use FEIGN CLIENT. FEIGN? -> Provides an articulate way of communicating with a different service. SERVICE DISCOVERY.? -> quiz-service is searching for question-service. So question service needs to be discoverable. So we need some server to discover the other API/microservice. And that server is NETFLIX EUREKA SERVER. All the microservices need to register themselves to the Eureka server and then one microservice can search other microservice using EUREKA CLIENT. NEED --> RestTemplate class for Communication between two microservices, FEIGN CLIENT for communicating with different services, EUREKA SERVER (SERVER REGISTRY/SERVICE DISCOVERY) for finding different services, EUREKA CLIENT (Quiz service and Question Service are the Eureka clients) to register and find the microservice. You have to name the Eureka Server and all the other microservices.
        List<Integer> questionIds = quizInterface.getQuestionsForQuiz(category, noOfQuestions).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questionIds);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<?> getQuizQuestions(Integer quizId) {

        boolean quizExists = quizDao.existsById(quizId);
        if(!quizExists) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Quiz not found!!!");
        }else{
            Optional<Quiz> quiz = quizDao.findById(quizId);
            List<Integer> questionIds = quiz.get().getQuestionIds();
            ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
            return questions;
        }
    }

    public ResponseEntity<Integer> calculateResult(Integer quizId, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }

    public void deleteQuiz(Integer quizId) {
        try {
            if (quizDao.existsById(quizId)) {
                quizDao.deleteById(quizId);
            } else {
                throw new NoSuchElementException("Quiz not found with ID: " + quizId);
            }
        } catch (Exception e) {
            // Log it
            System.err.println("Error deleting quiz: " + e.getMessage());
            throw e; // Let controller handle the response
        }
    }
}
