package com.learnify.absence.dao;

import com.learnify.absence.model.Absence;
import com.learnify.absence.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbsenceDaoImpl implements AbsenceDao {

    @Override
    public void createAbsence(Absence absence) {
        String sql = "INSERT INTO absence (student_id, course_id, absence_date, reason) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, absence.getStudentId());
            stmt.setInt(2, absence.getCourseId());
            stmt.setString(3, absence.getAbsenceDate());
            stmt.setString(4, absence.getReason());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Absence> getAllAbsences() {
        List<Absence> absences = new ArrayList<>();
        String sql = "SELECT * FROM absence";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Absence absence = new Absence();
                absence.setAbsenceId(rs.getInt("absence_id"));
                absence.setStudentId(rs.getInt("student_id"));
                absence.setCourseId(rs.getInt("course_id"));
                absence.setAbsenceDate(rs.getString("absence_date"));
                absence.setReason(rs.getString("reason"));
                absences.add(absence);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return absences;
    }

    @Override
    public List<Absence> getAbsencesByStudentId(int studentId) {
        List<Absence> absences = new ArrayList<>();
        String sql = "SELECT * FROM absence WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Absence absence = new Absence();
                    absence.setAbsenceId(rs.getInt("absence_id"));
                    absence.setStudentId(rs.getInt("student_id"));
                    absence.setCourseId(rs.getInt("course_id"));
                    absence.setAbsenceDate(rs.getString("absence_date"));
                    absence.setReason(rs.getString("reason"));
                    absences.add(absence);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return absences;
    }
}
