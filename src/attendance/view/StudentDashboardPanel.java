package src.attendance.view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import src.attendance.controller.EnrollmentController;
import src.attendance.controller.ClassController;
import src.attendance.model.ClassInfo;
import src.attendance.model.StudentClasses;
import src.attendance.model.User;

public class StudentDashboardPanel extends JPanel {

    private static final int PANEL_WIDTH  = 900;
    private static final int PANEL_HEIGHT = 600;
    private static final int TOP_BAR_H   = 50;
    private static final int HEADER_H    = 45;

    // Card grid constants
    private static final int CARD_W   = 270;
    private static final int CARD_H   = 150;
    private static final int CARD_GAP = 14;
    private static final int GRID_PAD = 10;

    private User student;
    private ClassController classController = new ClassController();
    private EnrollmentController enrollmentController = new EnrollmentController();
    private JComboBox<ClassInfo> classDropdown;

    public StudentDashboardPanel(User student) {
        this.student = student;
        setLayout(null);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        buildTopBar();
        buildContentArea();
    }

    private void buildTopBar() {
        JPanel topBar = new JPanel(null);
        topBar.setBounds(0, 0, PANEL_WIDTH, TOP_BAR_H);

        JLabel welcomeLabel = new JLabel("Welcome, " + student.getFullName() + "!");
        welcomeLabel.setBounds(10, 8, 350, 18);
        topBar.add(welcomeLabel);

        JLabel roleLabel = new JLabel("Role: " + student.getRole());
        roleLabel.setBounds(10, 26, 350, 18);
        topBar.add(roleLabel);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(PANEL_WIDTH - 85, 10, 80, 30);
        logoutButton.addActionListener(e -> MainFrame.navigateTo("LoginPanel"));
        topBar.add(logoutButton);

        // --- Class selection dropdown ---
        JLabel selectClassLabel = new JLabel("Select class:");
        selectClassLabel.setBounds(PANEL_WIDTH - 340, 10, 80, 30);
        List<ClassInfo> classInfoList = enrollmentController.getClassNames();
        classDropdown = new JComboBox<ClassInfo>(classInfoList.toArray(new ClassInfo[0]));
        classDropdown.setBounds(PANEL_WIDTH - 260, 10, 150, 30);
        classDropdown.addActionListener(e -> {
            ClassInfo selectedClass = (ClassInfo) classDropdown.getSelectedItem();
            if (selectedClass != null) {
                enrollmentController.enrollStudent(student.getSchoolID(), selectedClass.getClassId());
                MainFrame.navigateTo("StudentDashboardPanel", new StudentDashboardPanel(student));
            }
        });
        topBar.add(selectClassLabel);
        topBar.add(classDropdown);

        add(topBar);
    }

    private void buildContentArea() {
        // --- Header row ---
        JPanel header = new JPanel(null);
        header.setBounds(0, TOP_BAR_H, PANEL_WIDTH, HEADER_H);

        JLabel titleLabel = new JLabel("My classes");
        titleLabel.setBounds(10, 10, 150, 25);
        header.add(titleLabel);
        add(header);

        // --- Scrollable class grid ---
        JPanel classGridPanel = buildClassList();
        JScrollPane scrollPane = new JScrollPane(classGridPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBounds(0, TOP_BAR_H + HEADER_H, PANEL_WIDTH, PANEL_HEIGHT - TOP_BAR_H - HEADER_H);
        add(scrollPane);
    }

    private JPanel buildClassList() {
        List<StudentClasses> classes = classController.getAllClassesForStudent(student.getSchoolID());
        JPanel listPanel = new JPanel(null);

        if (classes.isEmpty()) {
            JLabel emptyLabel = new JLabel("You haven't created any classes yet.", SwingConstants.CENTER);
            emptyLabel.setBounds(0, 20, PANEL_WIDTH, 25);
            listPanel.add(emptyLabel);
            listPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 70));
            return listPanel;
        }

        int cols = 3;
        int rows = (int) Math.ceil((double) classes.size() / cols);
        int totalH = GRID_PAD + rows * (CARD_H + CARD_GAP) - CARD_GAP + GRID_PAD;
        listPanel.setPreferredSize(new Dimension(PANEL_WIDTH, totalH));

        for (int i = 0; i < classes.size(); i++) {
            int col = i % cols;
            int row = i / cols;
            int cx = GRID_PAD + col * (CARD_W + CARD_GAP);
            int cy = GRID_PAD + row * (CARD_H + CARD_GAP);
            JPanel card = buildClassCard(classes.get(i));
            card.setBounds(cx, cy, CARD_W, CARD_H);
            listPanel.add(card);
        }

        return listPanel;
    }

    private JPanel buildClassCard(StudentClasses cls) {
        JPanel card = new JPanel(null);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        int innerW = CARD_W - 24;

        // --- Class name ---
        JLabel classNameLabel = new JLabel(cls.getClassName());
        classNameLabel.setFont(classNameLabel.getFont().deriveFont(Font.BOLD, 14f));
        classNameLabel.setBounds(12, 12, 95, 20);
        card.add(classNameLabel);

        // --- Summary button ---
        JButton summaryButton = new JButton("Summary");
        summaryButton.setBounds(CARD_W - 112, 10, 100, 24);
        summaryButton.addActionListener(e -> {
            MainFrame.navigateTo("StudentAttendanceSummaryPanel", new StudentAttendanceSummaryPanel(student.getSchoolID(), cls.getClassID()));
        });
        card.add(summaryButton);

        // --- Warning / Dropout labels ---
        JLabel warningLabel = new JLabel("Warning limit: " + cls.getWarningLimit());
        warningLabel.setBounds(12, 38, innerW, 18);
        card.add(warningLabel);

        JLabel dropoutLabel = new JLabel("Dropout limit: " + cls.getDropoutLimit());
        dropoutLabel.setBounds(12, 60, innerW, 18);
        card.add(dropoutLabel);

       
        return card;
    }
}