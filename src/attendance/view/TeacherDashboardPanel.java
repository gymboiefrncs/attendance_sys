package src.attendance.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import java.awt.*;
import src.attendance.controller.ClassController;
import src.attendance.model.Class_;
import src.attendance.model.User;

public class TeacherDashboardPanel extends JPanel {
  private User instructor;
  private ClassController classController = new ClassController();
  private JTextField classNameField, warningLimitField, dropoutLimitField;
  private JLabel errorLabel;

  public TeacherDashboardPanel(User instructor) {
    this.instructor = instructor;

    setLayout(new BorderLayout());
    setBackground(new Color(245, 245, 245));
    add(buildTopBar(), BorderLayout.NORTH);
    add(buildContentArea(), BorderLayout.CENTER);
  }

  private JPanel buildTopBar() {
    JPanel topBar = new JPanel(new BorderLayout());
    topBar.setBackground(Color.WHITE);
    topBar.setBorder(BorderFactory.createCompoundBorder(
                  BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
            new EmptyBorder(10, 16, 10, 16)
    ));

    JLabel welcomeLabel = new JLabel("Welcome, " + instructor.getFullName() + "!");
    welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

    JLabel roleLabel = new JLabel("Role: " + instructor.getRole());
    roleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
    roleLabel.setForeground(Color.GRAY);

    JPanel leftSide = new JPanel(new FlowLayout(FlowLayout.LEFT, 0,0));
    leftSide.setBackground(Color.WHITE);
    leftSide.add(welcomeLabel);
    leftSide.add(Box.createRigidArea(new Dimension(10, 0)));
    leftSide.add(roleLabel);

    JButton logoutButton = new JButton("Logout");
    logoutButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
    logoutButton.setFocusPainted(false);
    logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    logoutButton.addActionListener(e -> {
        MainFrame.navigateTo("LoginPanel");
    });
    topBar.add(leftSide, BorderLayout.WEST);
    topBar.add(logoutButton, BorderLayout.EAST);
    return topBar;
  }


  private JPanel buildContentArea() {
    JPanel contentArea = new JPanel(new BorderLayout());
    contentArea.setBackground(Color.WHITE);
    contentArea.setBorder(new EmptyBorder(16, 16, 16, 16));

    JPanel header = new JPanel(new BorderLayout());
    header.setBackground(new Color(245, 245, 245));
    header.setBorder(new EmptyBorder(16, 16, 16, 16));

    JLabel titleLabel = new JLabel("My classes");
    titleLabel.setFont(new Font("sansSerif", Font.BOLD, 15));

    JButton addClassButton = new JButton("+ New Class");
    addClassButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
    addClassButton.setForeground(new Color(24, 95, 165));
    addClassButton.setBackground(new Color(230, 241, 251));
    addClassButton.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(24, 95, 165, 80)),
        new EmptyBorder(5, 12, 5, 12)
    ));
    addClassButton.addActionListener(e -> showNewClassDialog());
    header.add(titleLabel, BorderLayout.WEST);
    header.add(addClassButton, BorderLayout.EAST);

    JPanel classGridPanel = buildClassList();
    JScrollPane scrollPane = new JScrollPane(classGridPanel);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);

    contentArea.add(header, BorderLayout.NORTH);
    contentArea.add(scrollPane, BorderLayout.CENTER);
    return contentArea;
  }

  private JPanel buildClassList() {

    List<Class_> classes = classController.getAllClasses(instructor.getSchoolID());
    
    JPanel classListPanel = new JPanel(new BorderLayout());
    classListPanel.setBackground(Color.WHITE);
    if (classes.isEmpty()) {
      JLabel emptyLabel = new JLabel("You haven't created any classes yet.");
      emptyLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
      emptyLabel.setForeground(Color.GRAY);
      emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
      classListPanel.add(emptyLabel, BorderLayout.CENTER);
      return classListPanel;
    }
    JPanel gridPanel = new JPanel(new GridLayout(0, 2, 16, 16));
    gridPanel.setBackground(Color.WHITE);

    for (Class_ cls : classes) {
      gridPanel.add(buildClassCard(cls));
    }

    System.out.println(classes);
    classListPanel.add(gridPanel, BorderLayout.NORTH);
    return classListPanel;
  }

  private JPanel buildClassCard(Class_ cls) {
    JPanel card = new JPanel(new GridBagLayout());
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(220, 220, 220)),
        new EmptyBorder(12, 16, 12, 16)
    ));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0; 
    JLabel classNameLabel = new JLabel(cls.getClassName());
    classNameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
    gbc.gridy = 0;
    gbc.insets = new Insets(0, 0, 2, 0);
    card.add(classNameLabel, gbc);

    JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    actionRow.setBackground(Color.WHITE);

    JButton markButton = new JButton("Mark Attendance");
    markButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
    JButton summaryButton = new JButton("View Summary");
    summaryButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
    JButton deleteButton = new JButton("Delete");
    deleteButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
    JButton editButton = new JButton("Edit");
    editButton.setFont(new Font("SansSerif", Font.PLAIN, 12));

    actionRow.add(markButton);
    actionRow.add(summaryButton);
    actionRow.add(deleteButton);
    actionRow.add(editButton);

    gbc.gridy = 3;
    gbc.insets = new Insets(0, 0, 0, 0);
    card.add(actionRow, gbc);

    return card;
  }

  private void showNewClassDialog() {
    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "New Class", true);
    dialog.setSize(360, 460);
    dialog.setLocationRelativeTo(this);
    dialog.setResizable(false);

    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(Color.WHITE);
    formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    

        // --- Class Name ---
    JLabel nameLabel = new JLabel("Class Name");
    nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
    gbc.gridy = 0;
    gbc.insets = new Insets(0, 0, 6, 0);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;

    formPanel.add(nameLabel, gbc);

    classNameField = new JTextField();
    classNameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
    classNameField.setPreferredSize(new Dimension(280, 35));
    gbc.gridy = 1; // Moved to follow label
    gbc.insets = new Insets(0, 0, 12, 0); // Extra bottom space
    formPanel.add(classNameField, gbc);

    // --- Warning Limit ---
    JLabel warningLabel = new JLabel("Consecutive absences before warning limit");
    warningLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
    gbc.gridy = 2;
    gbc.insets = new Insets(0, 0, 6, 0);
    formPanel.add(warningLabel, gbc);

    warningLimitField = new JTextField(); // Added new field
    warningLimitField.setFont(new Font("SansSerif", Font.PLAIN, 14));
    warningLimitField.setPreferredSize(new Dimension(280, 35));
    gbc.gridy = 3;
    gbc.insets = new Insets(0, 0, 12, 0);
    formPanel.add(warningLimitField, gbc);

    // --- Dropout Limit ---
    JLabel dropoutLabel = new JLabel("Consecutive absences before dropout");
    dropoutLabel.setFont(new Font("sansSerif", Font.BOLD, 13));
    gbc.gridy = 4;
    gbc.insets = new Insets(0, 0, 6, 0);
    formPanel.add(dropoutLabel, gbc);

    dropoutLimitField = new JTextField(); // Added new field
    dropoutLimitField.setFont(new Font("SansSerif", Font.PLAIN, 14));
    dropoutLimitField.setPreferredSize(new Dimension(280, 35));
    gbc.gridy = 5;
    gbc.insets = new Insets(0, 0, 12, 0);
    formPanel.add(dropoutLimitField, gbc);

    // --- Error Label ---
    errorLabel = new JLabel(" ", SwingConstants.CENTER);
    errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
    errorLabel.setForeground(new Color(200, 50, 50));
    gbc.gridy = 6;
    gbc.insets = new Insets(0, 0, 10, 0);
    formPanel.add(errorLabel, gbc);

    JButton createButton = new JButton("Create");
    createButton.setFont(new Font("SansSerif", Font.BOLD, 14));
    createButton.setBackground(new Color(50, 100, 200));
    createButton.setForeground(Color.WHITE);
    createButton.setFocusPainted(false);
    createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    createButton.setPreferredSize(new Dimension(280, 38));
    formPanel.add(createButton, gbc);

    createButton.addActionListener(e ->handleCreateClass(dialog));
    dialog.add(formPanel);
    dialog.setVisible(true);
  }

  private void handleCreateClass(JDialog dialog) {
    errorLabel.setText(" ");
    String className = classNameField.getText().trim();
    String warningLimitStr = warningLimitField.getText().trim();
    String dropoutLimitStr = dropoutLimitField.getText().trim();
    if (className.isEmpty()) {
      errorLabel.setText("Class name cannot be empty");
      return;
    }
    if (warningLimitStr.isEmpty() || dropoutLimitStr.isEmpty()) {
      errorLabel.setText("Warning and dropout limits cannot be empty");
      return;
    }
    int warningLimit, dropoutLimit;
    try {
      warningLimit = Integer.parseInt(warningLimitStr);
      dropoutLimit = Integer.parseInt(dropoutLimitStr);
    } catch (NumberFormatException ex) {
      errorLabel.setText("Warning and dropout limits must be valid integers");
       return;
    }

    if (warningLimit <= 0 || dropoutLimit <= 0) {
      errorLabel.setText("Warning and dropout limits must be positive integers");
       return;
    }

    String error = classController.addClass(className, instructor.getSchoolID(), warningLimit, dropoutLimit);
    if (error != null) {
      errorLabel.setText(error);
      return;
    }

    dialog.dispose();
    MainFrame.navigateTo("TeacherDashboardPanel", new TeacherDashboardPanel(instructor));

  }
}
