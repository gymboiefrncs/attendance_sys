package src.attendance.view;

import src.attendance.controller.AuthController;
import src.attendance.model.User;

import src.attendance.model.Enums.Role;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private JTextField schoolIdField;
    private JLabel errorLabel;
    private AuthController authController = new AuthController();

    public LoginPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // --- Title ---
        JLabel titleLabel = new JLabel("Attendance System - Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 4, 0); // small gap below title
        formPanel.add(titleLabel, gbc);

        // --- Subtitle ---
        JLabel subtitleLabel = new JLabel("Log in to continue", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitleLabel.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 28, 0);
        formPanel.add(subtitleLabel, gbc);

        // --- School ID Label ---
        JLabel idLabel = new JLabel("School ID");
        idLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 6, 0);
        formPanel.add(idLabel, gbc);

        // --- School ID Field ---
        schoolIdField = new JTextField();
        schoolIdField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        schoolIdField.setPreferredSize(new Dimension(280, 35));
        schoolIdField.addActionListener(e -> handleLogin()); // Enter key triggers login
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 6, 0);
        formPanel.add(schoolIdField, gbc);

        // --- Error Label ---
        errorLabel = new JLabel(" ", SwingConstants.CENTER);
        errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        errorLabel.setForeground(new Color(200, 50, 50));
        gbc.gridy = 4; 
        gbc.insets = new Insets(0, 0, 10, 0);
        formPanel.add(errorLabel, gbc);

        // --- Login Button ---
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setBackground(new Color(50, 100, 200));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setPreferredSize(new Dimension(280, 38));
        loginButton.addActionListener(e -> handleLogin());
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 14, 0);
        formPanel.add(loginButton, gbc);

        // --- Sign Up Link ---
        JButton signUpButton = new JButton("New student? Sign up here");
        signUpButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        signUpButton.setForeground(new Color(50, 100, 200));
        signUpButton.setBorderPainted(false);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signUpButton.addActionListener(e -> MainFrame.navigateTo("RegistrationPanel"));
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 0, 0);
        formPanel.add(signUpButton, gbc);

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

        if (user.getRole() == Role.instructor) {
             MainFrame.navigateTo("TeacherDashboardPanel", new TeacherDashboardPanel(user));
        }
    }
}