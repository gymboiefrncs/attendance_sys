package src.attendance.dao;
import src.attendance.model.User;
import src.attendance.util.DBConnection;
import src.attendance.model.Enums.Role;
import java.sql.*;


public class UserDAO {
  public User findBySchoolID(String schoolID) {
    String query = "SELECT school_id, full_name, role FROM users WHERE school_id = ?";

    try {
      PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query);
      stmt.setString(1, schoolID);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        Role userRole = Role.valueOf(rs.getString("role").toLowerCase());
        return new User(
          rs.getString("school_id"), 
          rs.getString("full_name"), 
          userRole
        );
      }
      System.out.println(rs);
    } catch (SQLException e) {
      System.err.println("Error fetching user: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  public boolean existsBySchoolID(String schoolID) {
    String query = "SELECT 1 FROM users WHERE school_id = ?";
    try {
      PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query);
      stmt.setString(1, schoolID);
      ResultSet rs = stmt.executeQuery();
      return rs.next();
    } catch (SQLException e) {
      System.err.println("Error checking user existence: " + e.getMessage());
      e.printStackTrace();
    }
    return false;
  }

  public boolean insertStudent(String schoolID, String fullName) {
    String  query = "INSERT INTO users (school_id, full_name) VALUES (?, ?)";

    try {
      PreparedStatement stmt = DBConnection.getConnection().prepareStatement(query);
      stmt.setString(1, schoolID);
      stmt.setString(2, fullName);
      int rowsAffected = stmt.executeUpdate();
      return rowsAffected > 0;

    } catch (SQLException e) {
      System.err.println("Error inserting student: " + e.getMessage());
      e.printStackTrace();
    }
    return false;
  }
  }
