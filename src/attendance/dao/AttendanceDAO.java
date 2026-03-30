package src.attendance.dao;

import java.sql.*;
import java.util.List;
import src.attendance.util.DBConnection;
import src.attendance.model.Attendance;
import src.attendance.model.Enums.State;
import src.attendance.model.Summary;
import java.util.ArrayList;
import java.util.Date;

public class AttendanceDAO {

    public List<Summary> getAttendance(int classId, Date dateQuery) {
        String sql = """
                    SELECT 
                        a.enrollment_id,
                        a.state, 
                        a.reason, 
                        a.session_datetime AS date, 
                        e.class_id,
                        u.full_name
                    FROM attendance a
                    JOIN enrollments e ON a.enrollment_id = e.enrollment_id
                    JOIN users u ON e.student_id = u.school_id
                    WHERE e.class_id = ?
                    AND DATE(a.session_datetime) = ?
                    """;
        List<Summary> attendanceList = new ArrayList<>();

        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, classId);

            java.sql.Date sqlDate = new java.sql.Date(dateQuery.getTime());

            stmt.setDate(2, sqlDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int classID = rs.getInt("class_id");
                String fullName = rs.getString("full_name");
                int enrollId = rs.getInt("enrollment_id");
                State state = State.valueOf(rs.getString("state"));
                String reason = rs.getString("reason");
                Date date = rs.getDate("date");

                attendanceList.add(new Summary(classID, enrollId, fullName, date, reason, state));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendanceList;
    }


    public boolean batchRecordAttendance(List<Attendance> attendanceList) {
        String query = """
            INSERT INTO attendance (enrollment_id, state, reason)
            VALUES (?, ?, ?)
        """;
    
        Connection conn = null;
    
        try {
            conn = DBConnection.getConnection();

            // start transaction since we finna do batch insert
            conn.setAutoCommit(false);
    
           PreparedStatement stmt = conn.prepareStatement(query);
            for (Attendance attendance : attendanceList) {
                stmt.setInt(1, attendance.getEnrollmentID());
                stmt.setString(2, attendance.getState().toString());
                stmt.setString(3, attendance.getReason());
                stmt.addBatch();
            }
    
            stmt.executeBatch();
            conn.commit();
            return true;
    
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    // rollback if any error occured during the batch insert
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
    
        }
    }
    
}