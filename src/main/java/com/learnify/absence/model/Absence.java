package com.learnify.absence.model;

public class Absence {
    private int absenceId;
    private int studentId;
    private int courseId;
    private String absenceDate;
    private String reason;

    public int getAbsenceId() { return absenceId; }
    public void setAbsenceId(int absenceId) { this.absenceId = absenceId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public String getAbsenceDate() { return absenceDate; }
    public void setAbsenceDate(String absenceDate) { this.absenceDate = absenceDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
