package com.learnify.quiz.service;
import com.learnify.quiz.dao.ResultDao;
import com.learnify.quiz.dao.ResultDaoImpl;
import com.learnify.quiz.model.Result;
import java.util.List;

public class ResultServiceImpl implements ResultService {
    private final ResultDao resultDao = new ResultDaoImpl();

    @Override
    public void createResult(Result result) {
        resultDao.createResult(result);
    }

    @Override
    public List<Result> getAllResults() {
        return resultDao.getAllResults();
    }

    @Override
    public void updateResult(Result result) {
        resultDao.updateResult(result);
    }
    @Override
    public void deleteResult(int resultId) {
        resultDao.deleteResult(resultId);
    }
}