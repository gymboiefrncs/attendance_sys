package src.attendance.controller;

import java.util.List;
import src.attendance.model.Class_;
import src.attendance.dao.ClassDAO;

public class ClassController {
  private ClassDAO classDao = new ClassDAO();

  public List<Class_> getAllClasses(String instructorID) {
    return classDao.getClasses(instructorID);
  }

  public String addClass(String className, String instructorID, int warningLimit, int dropoutLimit) {
    String trimmedClassName = className.trim();
    String trimmedInstructorID = instructorID.trim();
    
    if (trimmedInstructorID == null || trimmedInstructorID.isEmpty()) {
      return "Instructor not found";
    }

    boolean success = classDao.addClass(trimmedClassName, trimmedInstructorID, warningLimit, dropoutLimit);
    if (!success) {
      return "Failed to add class";
    }

    return null;
  }
}
