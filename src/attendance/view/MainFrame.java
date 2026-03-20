package src.attendance.view;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
  private static CardLayout cardLayout = new CardLayout();

  // container for all panels. this holds all the panels stacked together.
  private static JPanel mainPanel = new JPanel(cardLayout);


  public MainFrame() {
    setTitle("Attendance Management System");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    mainPanel.setBackground(new Color(245, 1, 1));
    mainPanel.add(new LoginPanel(), "LoginPanel");
    mainPanel.add(new RegisterPanel(), "RegistrationPanel");

    add(mainPanel);

    cardLayout.show(mainPanel, "LoginPanel");
  }

  public static void navigateTo(String panelName) {
    cardLayout.show(mainPanel, panelName);
  }

  public static void navigateTo(String panelName, JPanel newPanel) {
    mainPanel.add(newPanel, panelName);
    cardLayout.show(mainPanel, panelName);
  } 

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        new MainFrame().setVisible(true);
    });
}
}
