package src.attendance.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
  private static final String URL = "jdbc:mysql://localhost:3306/attendance_system";
  private static final String USER = "root";
  private static final String PASSWORD = "";

  private static Connection con = null;

  private DBConnection() {}

  public static Connection getConnection() {
    try {
      if (con == null || con.isClosed()) {
        con = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connection.");
      }
    } catch (SQLException e) {
      System.err.println("Failed to connect to database: " + e.getMessage());
      e.printStackTrace();
    }
    return con;
  }
}
