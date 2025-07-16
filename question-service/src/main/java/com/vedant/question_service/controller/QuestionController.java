package com.vedant.question_service.controller;

import com.vedant.question_service.model.Question;
import com.vedant.question_service.model.QuestionWrapper;
import com.vedant.question_service.model.Response;
import com.vedant.question_service.service.QuestionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    private Environment environment;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @DeleteMapping("/deleteQuestionNo/{quesNo}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer quesNo){
        boolean isDeleted = questionService.deleteQuestion(quesNo);
        if(isDeleted){
            return ResponseEntity.ok("Deleted Successfully");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
    }

    @PutMapping("/update/{questionId}")
    public ResponseEntity<String> updateQuestion(@PathVariable Integer questionId, @RequestBody Question updatedQuestion){
        boolean isUpdated = questionService.updateQuestion(questionId, updatedQuestion);
        if(isUpdated){
            return ResponseEntity
                    .accepted()
                    .body("Updated the question successfully");
            /*
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body("Updated the question successfully");
            */
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
    }


    /*~~~~~~MICROSERVICE~~~~~*/

    //Earlier, quiz was generating questions from questionDB. Now we want questionService to generate questions.
    //REQUIREMENT: category of questions, number of questions required for the quiz
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer noOfQuestions){
        return questionService.getQuestionsForQuiz(categoryName, noOfQuestions);
    }

    //Both services will have their own DB. QuizDB will have its own data about the quiz, but it will not have data about the questions. For questions, it has to go to the questionService. QuizDB has ONLY QUESTION IDS ASSOCIATED W/ A QUIZ.
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){

        //check on which service instance is the request going
        String port = environment.getProperty("local.server.port");
        System.out.println("Running on port: " + port);

        return questionService.getQuestionsFromIds(questionIds);
    }

    //Earlier, we calculated score in QuizService. But now, the QuizService doesn't contain the right answer. So the questionService has to calculate the score of the quiz. QuizService will send the responses got from the client to the questionService
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }

}
