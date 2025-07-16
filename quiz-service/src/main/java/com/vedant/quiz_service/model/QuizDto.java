package com.vedant.quiz_service.model;

import lombok.Data;


public class QuizDto {
    String categoryName;
    Integer noOfQuestions;
    String title;

    public QuizDto(){ }

    public QuizDto(String categoryName, Integer noOfQuestions, String title){
        this.categoryName = categoryName;
        this.noOfQuestions = noOfQuestions;
        this.title = title;
    }

    public String getCategoryName(){ return  this.categoryName; }
    public Integer getNoOfQuestions(){ return  this.noOfQuestions; }
    public String getTitle(){ return  this.title; }

    public void setCategoryName(String categoryName){ this.categoryName = categoryName; }
    public void setNoOfQuestions(Integer noOfQuestions){ this.noOfQuestions = noOfQuestions; }
    public void setTitle(String title){ this.title = title; }

    @Override
    public String toString() {
        return "QuizDto{" +
                "category name =" + categoryName +
                ", number of Questions ='" + noOfQuestions + '\'' +
                ", title =" + title +
                '}';
    }

}
