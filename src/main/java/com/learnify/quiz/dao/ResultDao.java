package com.learnify.quiz.dao;

import com.learnify.quiz.model.Result;
import java.util.List;

public interface ResultDao {
    void createResult(Result result);
    List<Result> getAllResults();
    void updateResult(Result result);
    void deleteResult(int resultId);
}