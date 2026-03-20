package src.attendance.view;

import javax.swing.*;
import java.awt.*;
import src.attendance.controller.AuthController;

public class RegisterPanel extends JPanel {
  private JTextField schoolIDField, fullNameField;
  private JLabel errorLabel;
  private AuthController authController = new AuthController();

  public RegisterPanel() {
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
    JLabel titleLabel = new JLabel("Attendance System - Registration", SwingConstants.CENTER);
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
    gbc.gridy = 0;
    gbc.insets = new Insets(0, 0, 4, 0);
    formPanel.add(titleLabel, gbc);

    // --- Subtitle ---
    JLabel subtitleLabel = new JLabel("Create a new account", SwingConstants.CENTER);
    subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
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
    schoolIDField = new JTextField();
    schoolIDField.setFont(new Font("SansSerif", Font.PLAIN, 14));
    schoolIDField.setPreferredSize(new Dimension(280, 35));
    gbc.gridy = 3;
    gbc.insets = new Insets(0, 0, 6, 0);
    formPanel.add(schoolIDField, gbc);

    // --- Full Name Label ---
    JLabel nameLabel = new JLabel("Full Name");
    nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
    gbc.gridy = 4;
    gbc.insets = new Insets(0, 0, 6, 0);
    formPanel.add(nameLabel, gbc);

    // --- Full Name Field ---
    fullNameField = new JTextField();
    fullNameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
    fullNameField.setPreferredSize(new Dimension(280, 35));
    gbc.gridy = 5;
    gbc.insets = new Insets(0, 0, 6, 0);
    formPanel.add(fullNameField, gbc);

    // --- Error Label ---
    errorLabel = new JLabel(" ", SwingConstants.CENTER);
    errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
    errorLabel.setForeground(new Color(200, 50, 50));
    gbc.gridy = 6;
    gbc.insets = new Insets(0, 0, 10, 0);
    formPanel.add(errorLabel, gbc);

    // --- Register Button ---
    JButton registerButton = new JButton("Register");
    registerButton.setFont(new Font("SansSerif", Font.BOLD, 14));
    registerButton.setBackground(new Color(50, 100, 200));
    registerButton.setForeground(Color.WHITE);
    registerButton.setFocusPainted(false);
    registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    registerButton.setPreferredSize(new Dimension(280, 38));
    gbc.gridy = 7;
    gbc.insets = new Insets(10, 0, 0, 0);
    formPanel.add(registerButton, gbc);
    registerButton.addActionListener(e -> handleRegister());

    // --- Back to Login Link ---
    JButton backButton = new JButton("Already have an account? Log in here");
    backButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
    backButton.setBackground(Color.BLUE);
    backButton.setBorderPainted(false);
    backButton.setFocusPainted(false);
    backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    backButton.addActionListener(e -> MainFrame.navigateTo("LoginPanel"));
        gbc.gridy = 8;
    gbc.insets = new Insets(10, 0, 0, 0);
    formPanel.add(backButton, gbc);

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
    
    // TODO: navigate to dashbaord after successful registration
    
  }
}
