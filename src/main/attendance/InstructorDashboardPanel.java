package src.main.attendance;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import src.main.auth.User;
import src.main.classes.ClassController;
import src.main.classes.Class_;
import src.main.shared.MainFrame;

public class InstructorDashboardPanel extends JPanel {

    private static final int PANEL_WIDTH = 900;
    private static final int PANEL_HEIGHT = 600;
    private static final int TOP_BAR_H = 50;
    private static final int HEADER_H = 45;

    // Card grid constants
    private static final int CARD_W = 270;
    private static final int CARD_H = 150;
    private static final int CARD_GAP = 14;
    private static final int GRID_PAD = 10;

    private User instructor;
    private ClassController classController = new ClassController();
    private JTextField classNameField, warningLimitField, dropoutLimitField;
    private JLabel errorLabel;

    public InstructorDashboardPanel(User instructor) {
        this.instructor = instructor;
        setLayout(null);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        buildTopBar();
        buildContentArea();
    }

    private void buildTopBar() {
        JPanel topBar = new JPanel(null);
        topBar.setBounds(0, 0, PANEL_WIDTH, TOP_BAR_H);

        JLabel welcomeLabel = new JLabel("Welcome, " + instructor.fullName() + "!");
        welcomeLabel.setBounds(10, 8, 350, 18);
        topBar.add(welcomeLabel);

        JLabel roleLabel = new JLabel("Role: " + instructor.role());
        roleLabel.setBounds(10, 26, 350, 18);
        topBar.add(roleLabel);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(PANEL_WIDTH - 85, 10, 80, 30);
        logoutButton.addActionListener(e -> MainFrame.navigateTo("LoginPanel"));
        topBar.add(logoutButton);

        add(topBar);
    }

    private void buildContentArea() {
        // --- Header row ---
        JPanel header = new JPanel(null);
        header.setBounds(0, TOP_BAR_H, PANEL_WIDTH, HEADER_H);

        JLabel titleLabel = new JLabel("My classes");
        titleLabel.setBounds(10, 10, 150, 25);
        header.add(titleLabel);

        JButton addClassButton = new JButton("+ New Class");
        addClassButton.setBounds(PANEL_WIDTH - 130, 8, 120, 30);
        addClassButton.addActionListener(e -> showNewClassDialog());
        header.add(addClassButton);

        add(header);

        // --- Scrollable class grid ---
        JPanel classGridPanel = buildClassList();
        JScrollPane scrollPane = new JScrollPane(classGridPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBounds(0, TOP_BAR_H + HEADER_H, PANEL_WIDTH, PANEL_HEIGHT - TOP_BAR_H - HEADER_H);
        add(scrollPane);
    }

    private JPanel buildClassList() {
        List<Class_> classes = classController.getAllClasses(instructor.schoolID());
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

    private JPanel buildClassCard(Class_ cls) {
        JPanel card = new JPanel(null);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        int innerW = CARD_W - 24;

        // --- Class name ---
        JLabel classNameLabel = new JLabel(cls.className());
        classNameLabel.setFont(classNameLabel.getFont().deriveFont(Font.BOLD, 14f));
        classNameLabel.setBounds(12, 12, 95, 20);
        card.add(classNameLabel);

        // --- Summary button ---
        JButton summaryButton = new JButton("Summary");
        summaryButton.setBounds(CARD_W - 112, 10, 100, 24);
        summaryButton.addActionListener(e -> MainFrame.navigateTo("TeacherAttendanceSummaryPanel",
                new InstructorAttendanceSummaryPanel(cls.classID())));
        card.add(summaryButton);

        // --- Warning / Dropout labels ---
        JLabel warningLabel = new JLabel("Warning limit: " + cls.warningLimit());
        warningLabel.setBounds(12, 38, innerW, 18);
        card.add(warningLabel);

        JLabel dropoutLabel = new JLabel("Dropout limit: " + cls.dropoutLimit());
        dropoutLabel.setBounds(12, 60, innerW, 18);
        card.add(dropoutLabel);

        int btnW = (innerW - 16) / 3;
        int btnY = CARD_H - 42;

        JButton markButton = new JButton("Mark");
        markButton.setBounds(12, btnY, btnW, 30);
        markButton.addActionListener(e -> MainFrame.navigateTo("MarkAttendance", new MarkAttendance(cls.classID())));
        card.add(markButton);

        JButton editButton = new JButton("Edit");
        editButton.setBounds(12 + (btnW + 8), btnY, btnW, 30);
        editButton.addActionListener(e -> showUpdateClassDialog(
                cls.classID(), cls.className(), cls.dropoutLimit(), cls.warningLimit()));
        card.add(editButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(12 + 2 * (btnW + 8), btnY, btnW, 30);
        deleteButton.addActionListener(e -> handleDeleteClass(cls.classID()));
        card.add(deleteButton);

        return card;
    }

    private void showNewClassDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "New Class", true);
        dialog.setSize(360, 340);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setLayout(null);

        int x = 40, w = 280;

        JLabel nameLabel = new JLabel("Class Name");
        nameLabel.setBounds(x, 20, w, 20);
        dialog.add(nameLabel);

        classNameField = new JTextField();
        classNameField.setBounds(x, 44, w, 35);
        dialog.add(classNameField);

        JLabel warningLabel = new JLabel("Absences before warning");
        warningLabel.setBounds(x, 89, w, 20);
        dialog.add(warningLabel);

        warningLimitField = new JTextField();
        warningLimitField.setBounds(x, 113, w, 35);
        dialog.add(warningLimitField);

        JLabel dropoutLabel = new JLabel("Absences before dropout");
        dropoutLabel.setBounds(x, 158, w, 20);
        dialog.add(dropoutLabel);

        dropoutLimitField = new JTextField();
        dropoutLimitField.setBounds(x, 182, w, 35);
        dialog.add(dropoutLimitField);

        errorLabel = new JLabel(" ", SwingConstants.CENTER);
        errorLabel.setBounds(x, 225, w, 20);
        dialog.add(errorLabel);

        JButton createButton = new JButton("Create");
        createButton.setBounds(x, 249, w, 38);
        createButton.addActionListener(e -> handleCreateClass(dialog));
        dialog.add(createButton);

        dialog.setVisible(true);
    }

    private void showUpdateClassDialog(int classID, String className, int dropout_limit, int warning_limit) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Update Class", true);
        dialog.setSize(360, 340);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setLayout(null);

        int x = 40, w = 280;

        JLabel nameLabel = new JLabel("Class Name");
        nameLabel.setBounds(x, 20, w, 20);
        dialog.add(nameLabel);

        classNameField = new JTextField(className);
        classNameField.setBounds(x, 44, w, 35);
        dialog.add(classNameField);

        JLabel warningLabel = new JLabel("Absences before warning");
        warningLabel.setBounds(x, 89, w, 20);
        dialog.add(warningLabel);

        warningLimitField = new JTextField(Integer.toString(warning_limit));
        warningLimitField.setBounds(x, 113, w, 35);
        dialog.add(warningLimitField);

        JLabel dropoutLabel = new JLabel("Absences before dropout");
        dropoutLabel.setBounds(x, 158, w, 20);
        dialog.add(dropoutLabel);

        dropoutLimitField = new JTextField(Integer.toString(dropout_limit));
        dropoutLimitField.setBounds(x, 182, w, 35);
        dialog.add(dropoutLimitField);

        errorLabel = new JLabel(" ", SwingConstants.CENTER);
        errorLabel.setBounds(x, 225, w, 20);
        dialog.add(errorLabel);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(x, 249, w, 38);
        updateButton.addActionListener(e -> handleUpdateClass(dialog, classID));
        dialog.add(updateButton);

        dialog.setVisible(true);
    }

    private void handleCreateClass(JDialog dialog) {
        errorLabel.setText(" ");
        String className = classNameField.getText().trim();
        String warningLimitStr = warningLimitField.getText().trim();
        String dropoutLimitStr = dropoutLimitField.getText().trim();
        if (className.isEmpty() || warningLimitStr.isEmpty() || dropoutLimitStr.isEmpty()) {
            errorLabel.setText("Please fill all fields.");
            return;
        }
        int warningLimit, dropoutLimit;
        try {
            warningLimit = Integer.parseInt(warningLimitStr);
            dropoutLimit = Integer.parseInt(dropoutLimitStr);
        } catch (NumberFormatException ex) {
            errorLabel.setText("Limits must be integers.");
            return;
        }
        classController.addClass(className, instructor.schoolID(), warningLimit, dropoutLimit);
        dialog.dispose();
        MainFrame.navigateTo("TeacherDashboardPanel", new InstructorDashboardPanel(instructor));
    }

    private void handleUpdateClass(JDialog dialog, int classID) {
        errorLabel.setText(" ");
        String className = classNameField.getText().trim();
        String warningLimitStr = warningLimitField.getText().trim();
        String dropoutLimitStr = dropoutLimitField.getText().trim();
        if (className.isEmpty() || warningLimitStr.isEmpty() || dropoutLimitStr.isEmpty()) {
            errorLabel.setText("Please fill all fields.");
            return;
        }
        int warningLimit, dropoutLimit;
        try {
            warningLimit = Integer.parseInt(warningLimitStr);
            dropoutLimit = Integer.parseInt(dropoutLimitStr);
        } catch (NumberFormatException ex) {
            errorLabel.setText("Limits must be integers.");
            return;
        }
        classController.updateClass(classID, className, instructor.schoolID(), warningLimit, dropoutLimit);
        dialog.dispose();
        MainFrame.navigateTo("TeacherDashboardPanel", new InstructorDashboardPanel(instructor));
    }

    private void handleDeleteClass(int classID) {
        int result = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this class?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            classController.deleteClass(classID);
            MainFrame.navigateTo("TeacherDashboardPanel", new InstructorDashboardPanel(instructor));
        }
    }
}