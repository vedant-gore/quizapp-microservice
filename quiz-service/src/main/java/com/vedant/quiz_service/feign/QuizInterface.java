package com.vedant.quiz_service.feign;

import com.vedant.quiz_service.model.QuestionWrapper;
import com.vedant.quiz_service.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//mention which service you want to connect it to
//mention which methods you want to access of that service
//it automatically searches for QUESTION-SERVICE and whichever instance of it has less load it will send the requests there.
//this LOAD BALANCING happens automatically and its jar files/dependencies come with EUREKA CLIENT
@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    //Earlier, quiz was generating questions from questionDB. Now we want questionService to generate questions.
    //REQUIREMENT: category of questions, number of questions required for the quiz
    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer noOfQuestions);

    //Both services will have their own DB. QuizDB will have its own data about the quiz, but it will not have data about the questions. For questions, it has to go to the questionService. QuizDB has ONLY QUESTION IDS ASSOCIATED W/ A QUIZ.
    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

    //Earlier, we calculated score in QuizService. But now, the QuizService doesn't contain the right answer. So the questionService has to calculate the score of the quiz. QuizService will send the responses got from the client to the questionService
    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
