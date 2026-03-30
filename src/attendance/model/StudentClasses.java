package src.attendance.model;

public class StudentClasses {
  private int classID;
  private String className;
  private String studentID;
  private int enrollmentID;
  private int warningLimit;
  private int dropoutLimit;


  public StudentClasses(int classID, String  className, String studentID, int enrollmentID, int warningLimit, int dropoutLimit) {
    this.classID = classID;
    this.className = className;
    this.studentID = studentID;
    this.enrollmentID = enrollmentID;
    this.warningLimit = warningLimit;
    this.dropoutLimit = dropoutLimit;
  }

  public int getClassID() {
    return classID;
  }
  public String getClassName() {
    return className;
  }
  public int getDropoutLimit() {
    return dropoutLimit;
  }
  public String getStudentID() {
    return studentID;
  }
  public int getEnrollmentID() {
    return enrollmentID;
  }
  public int getWarningLimit() {
    return warningLimit;
  }
}
