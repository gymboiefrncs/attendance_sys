package src.attendance.model;

import java.util.Date;

import src.attendance.model.Enums.State;

public class StudentSummary {
  private int classID;
  private int enrollmentID;
  private Date date;
  private String reason;
  private State state;

  public StudentSummary(int classID, int enrollmentID, Date date, String reason, State state) {
    this.classID = classID;
    this.enrollmentID = enrollmentID;
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
