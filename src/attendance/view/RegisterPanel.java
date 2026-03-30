package src.attendance.view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import src.attendance.controller.AuthController;

public class RegisterPanel extends JPanel {

    private static final int PANEL_WIDTH  = 900;
    private static final int PANEL_HEIGHT = 600;

    private static final int FORM_W = 380;
    private static final int FORM_H = 413;

    private JTextField schoolIDField, fullNameField;
    private JLabel errorLabel;
    private AuthController authController = new AuthController();

    public RegisterPanel() {
        setLayout(null);
        setPreferredSize(new java.awt.Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        int formX = (PANEL_WIDTH  - FORM_W) / 2;
        int formY = (PANEL_HEIGHT - FORM_H) / 2;

        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(formX, formY, FORM_W, FORM_H);
        formPanel.setBorder(BorderFactory.createEmptyBorder());

        int x = 50, w = 280;

        // --- Title ---
        JLabel titleLabel = new JLabel("Attendance System - Registration", SwingConstants.CENTER);
        titleLabel.setBounds(x, 40, w, 25);
        formPanel.add(titleLabel);

        // --- Subtitle ---
        JLabel subtitleLabel = new JLabel("Create a new account", SwingConstants.CENTER);
        subtitleLabel.setBounds(x, 69, w, 25);
        formPanel.add(subtitleLabel);

        // --- School ID Label ---
        JLabel idLabel = new JLabel("School ID");
        idLabel.setBounds(x, 122, w, 20);
        formPanel.add(idLabel);

        // --- School ID Field ---
        schoolIDField = new JTextField();
        schoolIDField.setBounds(x, 148, w, 35);
        formPanel.add(schoolIDField);

        // --- Full Name Label ---
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setBounds(x, 189, w, 20);
        formPanel.add(nameLabel);

        // --- Full Name Field ---
        fullNameField = new JTextField();
        fullNameField.setBounds(x, 215, w, 35);
        formPanel.add(fullNameField);

        // --- Error Label ---
        errorLabel = new JLabel(" ", SwingConstants.CENTER);
        errorLabel.setBounds(x, 256, w, 20);
        formPanel.add(errorLabel);

        // --- Register Button ---
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(x, 286, w, 38);
        registerButton.addActionListener(e -> handleRegister());
        formPanel.add(registerButton);

        // ---Login Button ---
        JButton loginButton = new JButton("Already have an account? Log in here");
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBounds(x, 338, w, 30);
        loginButton.addActionListener(e -> MainFrame.navigateTo("LoginPanel"));
        formPanel.add(loginButton);

        add(formPanel);
    }

    private void handleRegister() {
        errorLabel.setText(" ");

        String schoolID = schoolIDField.getText().trim();
        String fullName = fullNameField.getText().trim();

        String error = authController.register(schoolID, fullName);

        if (error != null) {
            errorLabel.setText(error);
            return;
        }

        JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.", "Success",
                JOptionPane.INFORMATION_MESSAGE);
        MainFrame.navigateTo("LoginPanel");
    }
}