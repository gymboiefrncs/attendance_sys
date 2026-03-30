package src.attendance.model;

import java.util.Date;

import src.attendance.model.Enums.State;

public class Summary {
  private int classID;
  private int enrollmentID;
  private String fullName;
  private Date date;
  private String reason;
  private State state;

  public Summary(int classID, int enrollmentID, String fullName, Date date, String reason, State state) {
    this.classID = classID;
    this.enrollmentID = enrollmentID;
    this.fullName = fullName;
    this.date = date;
    this.reason = reason;
    this.state = state;
  }

  public int getClassID() {
    return classID;
  }
  public int getEnrollmentID() {
    return enrollmentID;
  }
  public String getFullName() {
    return fullName;
  }
  public Date getDate() {
    return date;
  }
  public String getReason() {
    return reason;
  }
  public State getState() {
    return state;
  }
}
