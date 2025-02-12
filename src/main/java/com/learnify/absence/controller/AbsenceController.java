package com.learnify.absence.controller;

import com.learnify.absence.entities.AbsenceEntity;
import com.learnify.absence.service.AbsenceService;
import com.learnify.absence.service.AbsenceServiceImpl;

public class AbsenceController {
    private final AbsenceService absenceService = new AbsenceServiceImpl();

    public void saveAbsence(int studentId, int courseId, String absenceDate, String reason) {
        AbsenceEntity absence = new AbsenceEntity();
        absence.setStudentId(studentId);
        absence.setCourseId(courseId);
        absence.setAbsenceDate(absenceDate);
        absence.setReason(reason);
        absenceService.createAbsence(absence);
        System.out.println("Absence saved successfully!");
    }
}
