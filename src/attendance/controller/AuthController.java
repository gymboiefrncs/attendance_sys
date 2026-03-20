package src.attendance.controller;
import src.attendance.dao.UserDAO;
import src.attendance.model.User;

public class AuthController {
  private UserDAO userDao = new UserDAO();

  public User login(String schoolID) {
    String trimmed = schoolID.trim();
    if (schoolID == null || trimmed.isEmpty()) {
      return null;
    }
    return userDao.findBySchoolID(trimmed);
  }

  public String register(String schoolID, String fullName) {
    String trimmedID = schoolID.trim();
    String trimmedName = fullName.trim();

    if (trimmedID == null || trimmedID.isEmpty()) {
      return "School ID cannot be empty";
    }
    if (trimmedName == null || trimmedName.isEmpty()) {
      return "Name cannot be empty";
    }
    if (userDao.existsBySchoolID(trimmedID)) {
      return "This School ID is already registered";
    }

    boolean success = userDao.insertStudent(trimmedID, trimmedName);
    if (!success) {
      return "Registration failed";
    }

   return null;
  }

}
