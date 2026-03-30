package src.attendance.model;

public class ClassInfo {
  private int classId;
  private String className;

  public ClassInfo(int classId, String className) {
      this.classId = classId;
      this.className = className;
  }

  public int getClassId() {
      return classId;
  }

  public String getClassName() {
      return className;
  }

  @Override
  public String toString() {
      return className;
  }
}