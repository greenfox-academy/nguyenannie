package com.greenfoxacademy.annie.lagopusspringexam.DTO;

public class QuestionDTO {
    private long id;
    private String question;

    public QuestionDTO() {

    }

    public QuestionDTO(long id, String question) {
        this.id = id;
        this.question = question;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}