package src.attendance.view.student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import src.attendance.controller.AttendanceController;
import src.attendance.model.StudentSummary;
import src.attendance.view.MainFrame;

import java.util.Date;

public class StudentAttendanceSummaryPanel extends JPanel {

    private static final int PANEL_WIDTH = 900;
    private static final int PANEL_HEIGHT = 600;

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton backButton;
    private AttendanceController attendanceController = new AttendanceController();

    private int classId;
    private String studentId;

    public StudentAttendanceSummaryPanel(String studentId, int classId) {
        this.studentId = studentId;
        this.classId = classId;

        setLayout(null);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        // --- Back Button ---
        backButton = new JButton("Back");
        backButton.setBounds(20, 20, 100, 30);
        backButton.addActionListener(e -> MainFrame.navigateTo("TeacherDashboardPanel"));
        add(backButton);

        // --- Title ---
        JLabel titleLabel = new JLabel("Attendance Summary");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(350, 20, 300, 30);
        add(titleLabel);

        // --- Table ---
        tableModel = new DefaultTableModel(
                new Object[] { "Date", "Status", "Reason" }, 0);

        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(editor);
        dateSpinner.setBounds(600, 20, 150, 30);
        add(dateSpinner);

        JButton filterButton = new JButton("Filter");
        filterButton.setBounds(750, 20, 100, 30);

        filterButton.addActionListener(e -> {
            Date selectedDate = (Date) dateSpinner.getValue();
            loadAttendanceByDate(selectedDate);
            System.out.println("Filtering attendance for date: " + selectedDate);
        });

        add(filterButton);

        table = new JTable(tableModel);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 80, 800, 450);
        add(scrollPane);
    }

    private void loadAttendanceByDate(Date date) {
        List<StudentSummary> records = attendanceController.getAttendanceByClassID(studentId, date, classId);

        tableModel.setRowCount(0);

        for (StudentSummary a : records) {
            tableModel.addRow(new Object[] {
                    a.date(),
                    a.state(),
                    a.reason()
            });
        }
    }
}
