package src.main.attendance;

import java.util.Date;

/**
 * Instructor Summary
 */
public record InstructorSummary(int classID, int enrollmentID, String fullName, Date date, String reason, State state) {
}
