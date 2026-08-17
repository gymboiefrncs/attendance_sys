package src.main.enrollment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.main.auth.Role;
import src.main.auth.User;
import src.main.shared.DBConnection;

public class EnrollmentDAO {
    public List<User> getStudentsByClass(int classId) {
        List<User> students = new ArrayList<>();

        String sql = """
                    SELECT u.school_id, u.full_name, u.role
                    FROM users u
                    JOIN enrollments e ON u.school_id = e.student_id
                    LEFT JOIN attendance a
                      ON a.enrollment_id = e.enrollment_id
                      AND a.session_datetime::date = CURRENT_DATE
                    WHERE e.class_id = ?
                      AND u.role = 'student'
                      AND a.enrollment_id IS NULL
                """;

        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String schoolID = rs.getString("school_id");
                String fullName = rs.getString("full_name");

                Role role = Role.valueOf(rs.getString("role").toLowerCase());

                students.add(new User(schoolID, fullName, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public boolean insertStudentToClass(String studentID, int classID) {
        String sql = "INSERT INTO enrollments (student_id, class_id) VALUES (?, ?)";

        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, studentID);
            stmt.setInt(2, classID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getEnrollmentID(String studentID, int classID) {
        String sql = "SELECT enrollment_id FROM enrollments WHERE student_id = ? AND class_id = ?";

        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, studentID);
            stmt.setInt(2, classID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("enrollment_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

}
