package com.learnify.absence.service;

import com.learnify.absence.entities.AbsenceEntity;
import java.time.LocalDate;
import java.util.List;

public interface AbsenceService {
    void createAbsence(AbsenceEntity absence);
    List<AbsenceEntity> getAllAbsences();
    List<AbsenceEntity> getAbsencesByStudentId(int studentId);
    List<AbsenceEntity> getAbsencesByFilters(String classe, LocalDate startDate, LocalDate endDate);
    void deleteAbsence(int absenceId);
    void updateAbsence(AbsenceEntity absence);
}
