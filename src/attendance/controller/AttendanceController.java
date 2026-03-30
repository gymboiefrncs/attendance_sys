package src.attendance.controller;

import java.util.Date;
import java.util.List;
import src.attendance.dao.AttendanceDAO;
import src.attendance.model.Attendance;
import src.attendance.model.Summary;
import src.attendance.model.StudentSummary;

public class AttendanceController {
  private AttendanceDAO attendanceDAO = new AttendanceDAO();

  public List<Summary> getAttendanceByClassID(int classID, Date date) {
    return attendanceDAO.getAttendance(classID,date);
  }

  public List<StudentSummary> getAttendanceByClassID(String studentID, Date date, int classID) {
    return attendanceDAO.getAttendanceByStudentID(studentID, date, classID);
  }

  public String markAttendance(int enrollmentID, String state, String reason) {
    if (!state.equals("Present") 
    && !state.equals("Absent") 
    && !state.equals("Late") 
    && !state.equals("Excused")) {
      return "Invalid attendance state";
    }

    if (
      (state.equals("Excused") || state.equals("Late")) 
      && 
      (reason == null || reason.trim().isEmpty())
    ) {
      return "Reason is required for Excused or Late attendance";
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
