package src.attendance.model;

/**
 * StudentClasses
 */
public record StudentClasses(int classID, String className, String studentID, int enrollmentID, int warningLimit,
    int dropoutLimit) {
}