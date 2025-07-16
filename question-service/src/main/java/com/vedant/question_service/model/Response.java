package com.vedant.question_service.model;

public class Response {

    private Integer questionId;
    private String response;

    //getters
    public Integer getQuestionId() { return questionId; }
    public String getResponse() { return response; }

    //setters
    public void setQuestionId(Integer questionId) { this.questionId = questionId; }
    public void setResponse(String response) { this.response = response; }

    public Response(Integer questionId, String response) {
        this.questionId = questionId;
        this.response = response;
    }
}
