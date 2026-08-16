package src.attendance.view.auth;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import src.attendance.controller.AuthController;
import src.attendance.model.User;
import src.attendance.model.Enums.Role;
import src.attendance.view.MainFrame;
import src.attendance.view.instructor.InstructorDashboardPanel;
import src.attendance.view.student.StudentDashboardPanel;

public class LoginPanel extends JPanel {

    private static final int PANEL_WIDTH = 900;
    private static final int PANEL_HEIGHT = 600;

    private static final int FORM_W = 380;
    private static final int FORM_H = 341;

    private JTextField schoolIdField;
    private JLabel errorLabel;
    private AuthController authController = new AuthController();

    public LoginPanel() {
        setLayout(null);
        setPreferredSize(new java.awt.Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        int formX = (PANEL_WIDTH - FORM_W) / 2;
        int formY = (PANEL_HEIGHT - FORM_H) / 2;

        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(formX, formY, FORM_W, FORM_H);
        formPanel.setBorder(BorderFactory.createEmptyBorder());

        int x = 50, w = 280;

        // --- Title ---
        JLabel titleLabel = new JLabel("Attendance System - Login", SwingConstants.CENTER);
        titleLabel.setBounds(x, 40, w, 25);
        formPanel.add(titleLabel);

        // --- Subtitle ---
        JLabel subtitleLabel = new JLabel("Log in to continue", SwingConstants.CENTER);
        subtitleLabel.setBounds(x, 69, w, 25);
        formPanel.add(subtitleLabel);

        // --- School ID Label ---
        JLabel idLabel = new JLabel("School ID");
        idLabel.setBounds(x, 122, w, 20);
        formPanel.add(idLabel);

        // --- School ID Field ---
        schoolIdField = new JTextField();
        schoolIdField.setBounds(x, 148, w, 35);
        schoolIdField.addActionListener(e -> handleLogin());
        formPanel.add(schoolIdField);

        // --- Error Label ---
        errorLabel = new JLabel(" ", SwingConstants.CENTER);
        errorLabel.setBounds(x, 189, w, 20);
        formPanel.add(errorLabel);

        // --- Login Button ---
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(x, 219, w, 38);
        loginButton.addActionListener(e -> handleLogin());
        formPanel.add(loginButton);

        // --- Sign Up Link ---
        JButton signUpButton = new JButton("New student? Sign up here");
        signUpButton.setBorderPainted(false);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setBounds(x, 271, w, 30);
        signUpButton.addActionListener(e -> MainFrame.navigateTo("RegistrationPanel"));
        formPanel.add(signUpButton);

        add(formPanel);
    }

    private void handleLogin() {
        errorLabel.setText(" ");

        String schoolID = schoolIdField.getText().trim();

        if (schoolID.isEmpty()) {
            errorLabel.setText("Please enter your School ID.");
            return;
        }

        User user = authController.login(schoolID);
        if (user == null) {
            errorLabel.setText("School ID not found. Please try again.");
            schoolIdField.selectAll();
            return;
        }

        if (user.role() == Role.instructor) {
            MainFrame.navigateTo("TeacherDashboardPanel", new InstructorDashboardPanel(user));
        } else {
            MainFrame.navigateTo("StudentDashboardPanel", new StudentDashboardPanel(user));
        }
    }
}