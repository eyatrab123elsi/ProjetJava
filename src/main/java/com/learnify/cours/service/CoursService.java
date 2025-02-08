package com.learnify.cours.service;

import com.learnify.cours.entities.Cours;
import java.io.File;
import java.util.List;

public interface CoursService {
    void addCours(Cours cours, File pdfFile);
    void deleteCours(Long id);
    List<Cours> getAllCours();
}
