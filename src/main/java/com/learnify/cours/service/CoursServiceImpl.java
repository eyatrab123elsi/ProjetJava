package com.learnify.cours.service;

import com.learnify.cours.dao.CoursDAO;
import com.learnify.cours.dao.CoursDAOImpl;
import com.learnify.cours.model.Cours;

import java.util.List;

public class CoursServiceImpl implements CoursService {
    private final CoursDAO coursDAO = new CoursDAOImpl();

    @Override
    public Cours getCourseById(Long id) {
        return coursDAO.getCoursById(id);
    }

    @Override
    public List<Cours> getAllCourses() {
        return coursDAO.getAllCourses();
    }

    @Override
    public void addCourse(Cours cours) {
        coursDAO.saveCours(cours);
    }

    @Override
    public void removeCourse(Long id) {
        coursDAO.deleteCours(id);
    }
}
