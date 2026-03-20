package src.attendance.dao;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import src.attendance.model.Class_;
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
          rs.getInt("dropout_limit")
        ));
      }

    } catch (SQLException e) {
      System.err.println("Error fetching classes: " + e.getMessage());
      e.printStackTrace();
    }
    return classes;
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
}
