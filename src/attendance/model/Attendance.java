package src.attendance.model;
import src.attendance.model.Enums.State;

public class Attendance {
  private int enrollmentID;
  private State state;
  private String reason;

  public Attendance( int enrollmentID, State state, String reason) {
    this.enrollmentID = enrollmentID;
    this.state = state;
    this.reason = reason;
  }
  public int getEnrollmentID() {
    return enrollmentID;
  }
  public String getReason() {
    return reason;
  }
  public State getState() {
    return state;
  }
}
