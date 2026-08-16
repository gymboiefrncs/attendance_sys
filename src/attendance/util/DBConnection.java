package src.attendance.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection con = null;

    private DBConnection() {
    }

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                Properties props = new Properties();
                try (FileInputStream fis = new FileInputStream("config.properties")) {
                    props.load(fis);
                }

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                Class.forName("org.postgresql.Driver");

                con = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to PostgreSQL successfully!");
            }
        } catch (IOException e) {
            System.err.println("Failed to load config.properties file: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
}