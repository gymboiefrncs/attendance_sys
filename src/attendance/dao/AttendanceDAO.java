package src.attendance.dao;

import java.sql.*;
import java.util.List;
import src.attendance.util.DBConnection;
import src.attendance.model.Attendance;

public class AttendanceDAO {
    public boolean batchRecordAttendance(List<Attendance> attendanceList) {
        String query = """
            INSERT INTO attendance (enrollment_id, state, reason)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            conn.setAutoCommit(false);

            for (Attendance attendance : attendanceList) {
                stmt.setInt(1, attendance.getEnrollmentID());
                stmt.setString(2, attendance.getState().toString());
                stmt.setString(3, attendance.getReason());
                stmt.addBatch();
            }
            int[] rowsAffected = stmt.executeBatch();

            conn.commit();

            // Check if any rows were affected
            for (int i : rowsAffected) {
                if (i == Statement.EXECUTE_FAILED) {
                    return false;  // If any insert fails
                }
            }

            // If all inserts are successful, return true
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}