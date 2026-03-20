package src.attendance.model;

public class Class_ {
  private int classID;
  private String className;
  private String instructorID;
  private int warningLimit;
  private int dropoutLimit;


  public Class_(int classID, String  className, String instructorID, int warningLimit, int dropoutLimit) {
    this.classID = classID;
    this.className = className;
    this.instructorID = instructorID;
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
  public String getInstructorID() {
    return instructorID;
  }
  public int getWarningLimit() {
    return warningLimit;
  }
}
