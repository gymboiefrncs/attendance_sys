package src.attendance.model;

/**
 * ClassInfo
 */
public record ClassInfo(int classId, String className) {
  @Override
  public String toString() {
    return className;
  }
}