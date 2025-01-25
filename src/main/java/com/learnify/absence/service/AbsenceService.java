package com.learnify.absence.service;

import com.learnify.absence.model.Absence;
import java.util.List;

public interface AbsenceService {
    void createAbsence(Absence absence);
    List<Absence> getAllAbsences();
    List<Absence> getAbsencesByStudentId(int studentId);
}
