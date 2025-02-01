package com.learnify.cours.service;

import com.learnify.cours.entities.Cours;

import java.util.List;

public interface CoursService {
    Cours getCoursById(Long id);
    List<Cours> getAllCours();
    void addCours(Cours cours);
    void deleteCours(Long id);
}
