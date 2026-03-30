package src.attendance.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.attendance.model.ClassInfo;
import src.attendance.model.Class_;
import src.attendance.model.StudentClasses;
import src.attendance.util.DBConnection;

public class ClassDAO {
    public List<Class_> getClasses(String instructorID) {

        List<Class_> classes = new ArrayList<>();

        String query = "SELECT * FROM classes WHERE instructor_id = ?";

        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query);
            stmt.setString(1, instructorID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                classes.add(new Class_(rs.getInt("class_id"),
                        rs.getString("class_name"),
                        rs.getString("instructor_id"),
                        rs.getInt("warning_limit"),
                        rs.getInt("dropout_limit")));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching classes: " + e.getMessage());
            e.printStackTrace();
        }
        return classes;
    }

   public List<ClassInfo> getClassNames() {
    List<ClassInfo> classList = new ArrayList<>();

    String query = "SELECT class_name, class_id FROM classes";

    try {
        PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String className = rs.getString("class_name");
            int classId = rs.getInt("class_id");
            classList.add(new ClassInfo(classId, className));
        }

    } catch (SQLException e) {
        System.err.println("Error fetching class names: " + e.getMessage());
        e.printStackTrace();
    }

    return classList;
}

    public List<StudentClasses> getClassesForStudent(String studentID) {

        List<StudentClasses> studentClasses = new ArrayList<>();

        String query = """
            SELECT e.student_id, e.enrollment_id, c.class_id, c.class_name, c.warning_limit, c.dropout_limit
            FROM enrollments e 
            JOIN classes c 
            ON e.class_id = c.class_id 
            WHERE e.student_id = ?
        """;

        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query);
            stmt.setString(1, studentID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                studentClasses.add(new StudentClasses(rs.getInt("class_id"),
                        rs.getString("class_name"),
                        rs.getString("student_id"),
                        rs.getInt("enrollment_id"),
                        rs.getInt("warning_limit"),
                        rs.getInt("dropout_limit")));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching classes: " + e.getMessage());
            e.printStackTrace();
        }
        return studentClasses;
    }

    public boolean addClass(String className, String instructorID, int warningLimit, int dropoutLimit) {
        String query = "INSERT INTO classes(class_name, instructor_id, warning_limit, dropout_limit) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query);
            stmt.setString(1, className);
            stmt.setString(2, instructorID);
            stmt.setInt(3, warningLimit);
            stmt.setInt(4, dropoutLimit);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding class: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateClass(int classID, String className, String instructorID, int warningLimit, int dropoutLimit) {
        String query = "UPDATE classes SET class_name=?, instructor_id=?, warning_limit=?, dropout_limit=? WHERE class_id = ?";
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query);
            stmt.setString(1, className);
            stmt.setString(2, instructorID);
            stmt.setInt(3, warningLimit);
            stmt.setInt(4, dropoutLimit);
            stmt.setInt(5, classID);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating class: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleetClass(int classID) {
        String query = "DELETE FROM classes WHERE class_id = ?";
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query);
            stmt.setInt(1, classID);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting class: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
