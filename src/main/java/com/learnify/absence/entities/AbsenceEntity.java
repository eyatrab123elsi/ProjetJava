package com.learnify.absence.entities;

public class AbsenceEntity {
    private int absenceId;
    private int studentId;
    private int courseId;
    private String absenceDate;
    private String reason;


    private String studentName;
    private String studentClasse;
    private String courseName;

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

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentClasse() { return studentClasse; }
    public void setStudentClasse(String studentClasse) { this.studentClasse = studentClasse; }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
