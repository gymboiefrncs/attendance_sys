package src.attendance.model;
import src.attendance.model.Enums.Role;

public class User {
  private String schoolID;
  private String fullName;
  private Role role;

  public User(String schoolID, String fullName, Role role) {
    this.schoolID = schoolID;
    this.fullName = fullName;
    this.role = role;
  }

  public String getFullName() {
    return fullName;
  }
  public Role getRole() {
    return role;
  }
  public String getSchoolID() {
    return schoolID;
  }
}
