package com.learnify.quiz.dao;



import com.learnify.quiz.model.Cours;

import java.util.List;

public interface CoursDAO {
    Cours getCourseById(Long id);
    List<Cours> getAllCourses();
    void saveCourse(Cours course);
    void deleteCourse(Long id);
}