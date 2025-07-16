package com.vedant.question_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//@Table(name="question") //Specifies the name of the table in the database that the entity maps to. If you don’t use it, JPA assumes the table name is the same as the class name (case-sensitive depending on the database).
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
// Lombok uses Jackson
@Entity //Marks a Java class as a JPA entity, i.e., a class mapped to a database table.
public class Question{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
    private String difficultylevel;
    private String category;

    public Question() {} // No-args constructor

    // Getters
    public Integer getId() { return id; }
    public String getQuestionTitle() { return questionTitle; }
    public String getOption1() { return option1; }
    public String getOption2() { return option2; }
    public String getOption3() { return option3; }
    public String getOption4() { return option4; }
    public String getRightAnswer() { return rightAnswer; }
    public String getDifficultylevel() { return difficultylevel; }
    public String getCategory() { return category; }

    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setQuestionTitle(String questionTitle) { this.questionTitle = questionTitle; }
    public void setOption1(String option1) { this.option1 = option1; }
    public void setOption2(String option2) { this.option2 = option2; }
    public void setOption3(String option3) { this.option3 = option3; }
    public void setOption4(String option4) { this.option4 = option4; }
    public void setRightAnswer(String rightAnswer) { this.rightAnswer = rightAnswer; }
    public void setDifficultylevel(String difficultylevel) { this.difficultylevel = difficultylevel; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionTitle='" + questionTitle + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", option3='" + option3 + '\'' +
                ", option4='" + option4 + '\'' +
                ", rightAnswer='" + rightAnswer + '\'' +
                ", difficultylevel='" + difficultylevel + '\'' +
                ", category='" + category + '\'' +
                '}';
    }


}
