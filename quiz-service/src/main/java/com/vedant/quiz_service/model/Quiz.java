package com.vedant.quiz_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Integer id;

    private String title;

//    @ElementCollection
//    private List<Integer> questionIds;
    @ElementCollection
    @CollectionTable(
            name = "quiz_question_ids",
            joinColumns = @JoinColumn(name = "quiz_id")
    )
    @Column(name = "question_ids")
    private List<Integer> questionIds;


    //getters
    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public List<Integer> getQuestionIds() { return questionIds; }

    //setters
    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setQuestionIds(List<Integer> questionIds) { this.questionIds = questionIds; }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", questionIds=" + questionIds +
                '}';
    }
}
