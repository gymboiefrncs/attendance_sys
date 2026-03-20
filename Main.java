import src.attendance.util.DBConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection con = DBConnection.getConnection();
        if (con != null) {
            System.out.println("Successfully connected to the database.");
        } else {
            System.err.println("Failed to connect to the database.");
        }
    }
}