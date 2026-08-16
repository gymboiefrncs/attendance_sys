package src.attendance.model;

import java.util.Date;

import src.attendance.model.Enums.State;

/**
 * StudentSummary
 */
public record StudentSummary(int classID, int enrollmentID, Date date, String reason, State state) {
}