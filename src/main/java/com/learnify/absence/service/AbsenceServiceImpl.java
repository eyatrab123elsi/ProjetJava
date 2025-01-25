package com.learnify.absence.service;

import com.learnify.absence.dao.AbsenceDao;
import com.learnify.absence.dao.AbsenceDaoImpl;
import com.learnify.absence.model.Absence;

import java.util.List;

public class AbsenceServiceImpl implements AbsenceService {
    private final AbsenceDao absenceDao = new AbsenceDaoImpl();

    @Override
    public void createAbsence(Absence absence) {
        absenceDao.createAbsence(absence);
    }

    @Override
    public List<Absence> getAllAbsences() {
        return absenceDao.getAllAbsences();
    }

    @Override
    public List<Absence> getAbsencesByStudentId(int studentId) {
        return absenceDao.getAbsencesByStudentId(studentId);
    }
}
