package src.main.attendance;

import java.util.Date;

/**
 * StudentSummary
 */
public record StudentSummary(int classID, int enrollmentID, Date date, String reason, State state) {
}