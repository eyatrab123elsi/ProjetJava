package com.learnify.absence.service;

import com.learnify.absence.entities.AbsenceEntity;
import com.learnify.absence.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AbsenceServiceImpl implements AbsenceService {

    @Override
    public void createAbsence(AbsenceEntity absence) {
        String sql = "INSERT INTO absence (student_id, course_id, absence_date, reason) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, absence.getStudentId());
            stmt.setInt(2, absence.getCourseId());
            stmt.setDate(3, Date.valueOf(absence.getAbsenceDate()));
            stmt.setString(4, absence.getReason());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AbsenceEntity> getAllAbsences() {
        List<AbsenceEntity> absences = new ArrayList<>();
        // Requête avec jointure pour récupérer le nom et la classe de l'étudiant
        String sql = "SELECT a.absence_id, a.student_id, a.course_id, a.absence_date, a.reason, " +
                "CONCAT(u.nom, ' ', u.prenom) AS studentName, u.classe AS studentClasse, " +
                "c.titre AS courseName " +
                "FROM absence a " +
                "JOIN utilisateurs u ON a.student_id = u.id " +
                "JOIN courses c ON a.course_id = c.course_id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                AbsenceEntity absence = new AbsenceEntity();
                absence.setAbsenceId(rs.getInt("absence_id"));
                absence.setStudentId(rs.getInt("student_id"));
                absence.setCourseId(rs.getInt("course_id"));
                absence.setAbsenceDate(rs.getDate("absence_date").toString());
                absence.setReason(rs.getString("reason"));
                absence.setStudentName(rs.getString("studentName"));
                absence.setStudentClasse(rs.getString("studentClasse"));
                absence.setCourseName(rs.getString("courseName"));
                absences.add(absence);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return absences;
    }

    @Override
    public List<AbsenceEntity> getAbsencesByStudentId(int studentId) {
        List<AbsenceEntity> absences = new ArrayList<>();
        String sql = "SELECT a.absence_id, a.student_id, a.course_id, a.absence_date, a.reason, " +
                "CONCAT(u.nom, ' ', u.prenom) AS studentName, u.classe AS studentClasse " +
                "FROM absence a JOIN utilisateurs u ON a.student_id = u.id " +
                "WHERE a.student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AbsenceEntity absence = new AbsenceEntity();
                    absence.setAbsenceId(rs.getInt("absence_id"));
                    absence.setStudentId(rs.getInt("student_id"));
                    absence.setCourseId(rs.getInt("course_id"));
                    absence.setAbsenceDate(rs.getDate("absence_date").toString());
                    absence.setReason(rs.getString("reason"));
                    absence.setStudentName(rs.getString("studentName"));
                    absence.setStudentClasse(rs.getString("studentClasse"));
                    absences.add(absence);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return absences;
    }

    @Override
    public List<AbsenceEntity> getAbsencesByFilters(String classe, LocalDate startDate, LocalDate endDate) {
        List<AbsenceEntity> absences = new ArrayList<>();
        String sql = "SELECT a.absence_id, a.student_id, a.course_id, a.absence_date, a.reason, " +
                "CONCAT(u.nom, ' ', u.prenom) AS studentName, u.classe AS studentClasse " +
                "FROM absence a JOIN utilisateurs u ON a.student_id = u.id " +
                "WHERE u.classe = ? AND a.absence_date BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, classe);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AbsenceEntity absence = new AbsenceEntity();
                    absence.setAbsenceId(rs.getInt("absence_id"));
                    absence.setStudentId(rs.getInt("student_id"));
                    absence.setCourseId(rs.getInt("course_id"));
                    absence.setAbsenceDate(rs.getDate("absence_date").toString());
                    absence.setReason(rs.getString("reason"));
                    absence.setStudentName(rs.getString("studentName"));
                    absence.setStudentClasse(rs.getString("studentClasse"));
                    absences.add(absence);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return absences;
    }
    @Override
    public void deleteAbsence(int absenceId) {
        String sql = "DELETE FROM absence WHERE absence_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, absenceId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAbsence(AbsenceEntity absence) {
        String sql = "UPDATE absence SET absence_date = ?, reason = ? WHERE absence_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(absence.getAbsenceDate()));
            stmt.setString(2, absence.getReason());
            stmt.setInt(3, absence.getAbsenceId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
