package com.learnify.absence.service;

import com.learnify.absence.entities.AbsenceEntity;
import java.util.List;

public interface AbsenceService {
    void createAbsence(AbsenceEntity absence);
    List<AbsenceEntity> getAllAbsences();
    List<AbsenceEntity> getAbsencesByStudentId(int studentId);
}
