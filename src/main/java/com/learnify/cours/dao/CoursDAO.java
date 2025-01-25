package com.learnify.cours.dao;

import com.learnify.cours.model.Cours;

import java.util.List;

public interface CoursDAO {
    Cours getCoursById(Long id);
    List<Cours> getAllCourses();
    void saveCours(Cours cours);
    void deleteCours(Long id);
}
