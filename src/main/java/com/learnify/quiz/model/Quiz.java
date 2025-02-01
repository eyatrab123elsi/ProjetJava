package com.learnify.quiz.model;

public class Quiz {
    private int quizId;
    private String quizName;

    public int getQuizId() { return quizId; }
    public void setQuizId(int quizId) { this.quizId = quizId; }

    public String getQuizName() { return quizName; }
    public void setQuizName(String quizName) { this.quizName = quizName; }
     @Override
    public String toString() {
        return quizName;
    }
}