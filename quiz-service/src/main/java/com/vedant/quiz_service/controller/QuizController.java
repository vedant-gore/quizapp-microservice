package com.vedant.quiz_service.controller;

import com.vedant.quiz_service.model.QuizDto;
import com.vedant.quiz_service.model.Response;
import com.vedant.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNoOfQuestions(), quizDto.getTitle());
    }

    @GetMapping("get/{quizId}")
    public ResponseEntity<?> getQuizQuestions(@PathVariable Integer quizId){
        return quizService.getQuizQuestions(quizId);
    }

    @PostMapping("submit/{quizId}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer quizId, @RequestBody List<Response> responses){
        return quizService.calculateResult(quizId, responses);
    }

//    @DeleteMapping("delete/{quizId}")
//    public ResponseEntity<String> deleteQuiz(@PathVariable Integer quizId){
//        boolean isDeleted = quizService.deleteQuiz(quizId);
//        if(isDeleted){
//            return new ResponseEntity<>("Quiz Deleted Successfully", HttpStatus.NO_CONTENT);
//        }else{
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
//        }
//    }
    @DeleteMapping("delete/{quizId}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Integer quizId) {
        try {
            quizService.deleteQuiz(quizId);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Quiz Deleted Successfully");
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            // This is expected if quiz doesn't exist
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            // Unexpected errors
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting quiz");
        }
    }
}
