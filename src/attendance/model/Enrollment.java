package src.attendance.model;

public class Enrollment {
  private int enrollmentID;
  private String studentID;
  private int classID;

  public Enrollment(int enrollmentID, String studentID, int classID) {
    this.enrollmentID = enrollmentID;
    this.studentID = studentID;
    this.classID = classID;
  }

  public int getClassID() {
    return classID;
  }
  public int getEnrollmentID() {
    return enrollmentID;
  }
  public String getStudentID() {
    return studentID;
  }
}
