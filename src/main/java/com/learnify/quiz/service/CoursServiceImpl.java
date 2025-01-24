package com.learnify.quiz.service;

import com.learnify.quiz.dao.CoursDAO;
import com.learnify.quiz.dao.CoursDAOImpl;
import com.learnify.quiz.model.Cours;

import java.util.List;

public class CoursServiceImpl implements CoursService {
    private CoursDAO courseDAO = new CoursDAOImpl();

    @Override
    public Cours getCourseById(Long id) {
        return courseDAO.getCoursById(id);
    }

    @Override
    public List<Cours> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    @Override
    public void addCourse(Cours cours) {
        courseDAO.saveCours(cours);
    }

    @Override
    public void removeCourse(Long id) {
        courseDAO.deleteCours(id);
    }
}