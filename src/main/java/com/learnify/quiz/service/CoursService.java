package com.learnify.quiz.service;

import com.learnify.quiz.model.Cours;

import java.util.List;

public interface CoursService {
    Cours getCourseById(Long id);
    List<Cours> getAllCourses();
    void addCourse(Cours cours);
    void removeCourse(Long id);
}
