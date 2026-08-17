package src.main.attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import src.main.auth.User;
import src.main.enrollment.EnrollmentController;
import src.main.shared.MainFrame;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MarkAttendance extends JPanel {

    private static final int PANEL_WIDTH = 900;
    private static final int PANEL_HEIGHT = 600;
    private static final int TABLE_WIDTH = 800;
    private static final int TABLE_HEIGHT = 470;

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton backButton;
    private List<User> students;
    private List<Integer> enrollmentIds = new ArrayList<>();
    private int classId;
    private EnrollmentController enrollmentController = new EnrollmentController();
    private AttendanceController attendanceController = new AttendanceController();

    public MarkAttendance(int classId) {
        this.classId = classId;

        setLayout(null);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        // for centering
        int tableX = (PANEL_WIDTH - TABLE_WIDTH) / 2;
        int tableY = (PANEL_HEIGHT - TABLE_HEIGHT) / 2;

        // --- back BUtton ---
        backButton = new JButton("Back");
        backButton.setBounds(20, 20, 100, 30);
        backButton.addActionListener(e -> MainFrame.navigateTo("TeacherDashboardPanel"));
        add(backButton);

        // --- save Button ---
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(PANEL_WIDTH - 135, 20, 100, 30);
        saveButton.addActionListener(e -> saveAttendance());
        add(saveButton);

        // --- date Label ---
        JLabel dateLabel = new JLabel();
        dateLabel.setBounds(PANEL_WIDTH - 270, 20, 300, 30);

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        String currentDate = sdf.format(new Date());
        dateLabel.setText("Date: " + currentDate);
        add(dateLabel);

        tableModel = new DefaultTableModel(new Object[] { "Student Name", "Mark", "Reason (excused/late only)" }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(tableX, tableY, TABLE_WIDTH, TABLE_HEIGHT);
        add(scrollPane);

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        TableColumnModel columnModel = table.getColumnModel();
        TableColumn markColumn = columnModel.getColumn(1);
        JComboBox<String> comboBox = new JComboBox<>(new String[] { "Present", "Absent", "Late", "Excused" });
        markColumn.setCellEditor(new DefaultCellEditor(comboBox));

        loadStudents();
    }

    private void loadStudents() {
        students = enrollmentController.getStudents(classId);

        for (User student : students) {
            tableModel.addRow(new Object[] { student.fullName(), "--- Mark attendance ---", "" });
            System.out.println(student.fullName());

            // get enrollment id for each student whihc will be used to mark the attendance
            // later
            int enrollment = enrollmentController.getEnrollmentID(student.schoolID(), classId);
            enrollmentIds.add(enrollment);
        }
    }

    private void saveAttendance() {
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }
        List<Attendance> attendanceList = new ArrayList<>();

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            String state = (String) tableModel.getValueAt(row, 1);
            String reason = (String) tableModel.getValueAt(row, 2);

            int enrollment = enrollmentIds.get(row); // enrollment id of the student in the current row
            Attendance attendance = new Attendance(enrollment, State.valueOf(state.toLowerCase()), reason);
            attendanceList.add(attendance);
        }

        attendanceController.batchRecordAttendance(attendanceList);
    }
}