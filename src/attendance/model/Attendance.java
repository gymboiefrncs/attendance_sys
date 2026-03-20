package src.attendance.model;
import java.time.LocalDateTime;

enum AttendanceStatus {
  present, absent, late
}

public class Attendance {
  private int attendanceID;
  private int enrollmentID;
  private LocalDateTime sesssionDateTime;
  private AttendanceStatus state;
  private String reason;

  public Attendance(int attendanceID, int enrollmentID, LocalDateTime sesssionDateTime, AttendanceStatus state, String reason) {
    this.attendanceID = attendanceID;
    this.enrollmentID = enrollmentID;
    this.sesssionDateTime = sesssionDateTime;
    this.state = state;
    this.reason = reason;
  }

  public int getAttendanceID() {
    return attendanceID;
  }
  public int getEnrollmentID() {
    return enrollmentID;
  }
  public String getReason() {
    return reason;
  }
  public LocalDateTime getSesssionDateTime() {
    return sesssionDateTime;
  }
  public AttendanceStatus getState() {
    return state;
  }
}
