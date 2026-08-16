package src.attendance.model;

import java.util.Date;

import src.attendance.model.Enums.State;

/**
 * Instructor Summary
 */
public record InstructorSummary(int classID, int enrollmentID, String fullName, Date date, String reason, State state) {
}
