package src.attendance.model;

import src.attendance.model.Enums.State;

/**
 * Attendance
 */
public record Attendance(int enrollmentID, State state,
    String reason) {
}
