package com.learnify.absence.dao;

import com.learnify.absence.model.Absence;
import java.util.List;

public interface AbsenceDao {
    void createAbsence(Absence absence);
    List<Absence> getAllAbsences();
    List<Absence> getAbsencesByStudentId(int studentId);
}
