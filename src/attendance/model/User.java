package src.attendance.model;

import src.attendance.model.Enums.Role;

/**
 * User
 */
public record User(String schoolID, String fullName, Role role) {
}