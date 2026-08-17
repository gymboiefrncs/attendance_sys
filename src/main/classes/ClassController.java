package src.main.classes;

import java.util.List;

public class ClassController {
    private ClassDAO classDao = new ClassDAO();

    public List<Class_> getAllClasses(String instructorID) {
        return classDao.getClasses(instructorID);
    }

    public List<StudentClasses> getAllClassesForStudent(String studentID) {
        return classDao.getClassesForStudent(studentID);
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

    public String updateClass(int classID, String className, String instructorID, int warningLimit, int dropoutLimit) {
        String trimmedClassName = className.trim();
        String trimmedInstrutorID = instructorID.trim();

        if (trimmedInstrutorID == null || trimmedInstrutorID.isEmpty()) {
            return "Instructor not found";
        }
        boolean success = classDao.updateClass(classID, trimmedClassName, instructorID, warningLimit, dropoutLimit);
        if (!success) {
            return "Failed to update class";
        }
        return null;
    }

    public String deleteClass(int classID) {
        boolean success = classDao.deleetClass(classID);
        if (!success) {
            return "Failed to delete class";
        }
        return null;
    }
}
