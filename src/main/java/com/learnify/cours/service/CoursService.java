package com.learnify.cours.service;

import com.learnify.cours.model.Cours;

import java.util.List;

public interface CoursService {
    Cours getCourseById(Long id);
    List<Cours> getAllCourses();
    void addCourse(Cours cours);
    void removeCourse(Long id);
}
