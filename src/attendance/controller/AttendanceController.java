package src.attendance.controller;

import java.util.List;
import src.attendance.dao.AttendanceDAO;
import src.attendance.model.Attendance;

public class AttendanceController {
  private AttendanceDAO attendanceDAO = new AttendanceDAO();

  public String markAttendance(int enrollmentID, String state, String reason) {
    if (!state.equals("Present") && !state.equals("Absent") && !state.equals("Late") && !state.equals("Excused")) {
      return "Invalid attendance state";
    }

    if ((state.equals("Absent") || state.equals("Late")) && (reason == null || reason.trim().isEmpty())) {
      return "Reason is required for Absent or Late attendance";
    }

    boolean success = attendanceDAO.batchRecordAttendance(List.of(new Attendance( enrollmentID, null, reason)));
    if (!success) {
      return "Failed to mark attendance";
    }

    return null;
  }

  public boolean batchRecordAttendance(List<Attendance> attendanceList) {
    return attendanceDAO.batchRecordAttendance(attendanceList);
  }
}
