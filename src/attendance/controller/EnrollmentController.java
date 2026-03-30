package src.attendance.controller;

import java.util.List;

import src.attendance.dao.EnrollmentDAO;
import src.attendance.dao.ClassDAO;
import src.attendance.model.User;
import src.attendance.model.ClassInfo;

public class EnrollmentController {
    private EnrollmentDAO enrollmentDao = new EnrollmentDAO();
    private ClassDAO classDao = new ClassDAO();

    public List<User> getStudents(int classId) {
        return enrollmentDao.getStudentsByClass(classId);
    }

    public List<ClassInfo> getClassNames() {
        return classDao.getClassNames();
    }

    public boolean enrollStudent(String studentID, int classID) {
        return enrollmentDao.insertStudentToClass(studentID, classID);
    }

    public int getEnrollmentID(String studentID, int classID) {
        return enrollmentDao.getEnrollmentID(studentID, classID);
    }
}

