package com.learnify.quiz.service;
import com.learnify.quiz.model.Result;
import java.util.List;
public interface ResultService {
    void createResult(Result result);
    List<Result> getAllResults();
    void updateResult(Result result);
    void deleteResult(int resultId);
}