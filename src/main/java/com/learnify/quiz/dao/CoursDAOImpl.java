package com.learnify.quiz.dao;


import com.learnify.quiz.model.Cours;

import java.util.ArrayList;
import java.util.List;

public class CoursDAOImpl implements CoursDAO {
    private List<Cours> courses = new ArrayList<>();

    @Override
    public Cours getCourseById(Long id) {
        return courses.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Cours> getAllCourses() {
        return courses;
    }

    @Override
    public void saveCourse(Cours cours) {
        courses.add(cours);
    }

    @Override
    public void deleteCourse(Long id) {
        courses.removeIf(c -> c.getId().equals(id));
    }

    @Override
    public Cours getCoursById(Long id) {
        return null;
    }

    @Override
    public void saveCours(Cours cours) {

    }
}