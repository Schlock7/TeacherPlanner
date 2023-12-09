package Planner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainMenu {
    MainMenu() {
        JFrame loginFrame = new JFrame("Login Frame");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel loginPanel = new JPanel(new GridLayout(0, 2, 20, 20));

        JLabel userLabel = new JLabel("Username: ");
        JLabel passLabel = new JLabel("Password: ");
        JTextField userTextField = new JTextField("This is the correct version");
        JTextField passTextField = new JTextField();
        JButton button = new JButton("Login");

        loginPanel.add(userLabel);
        loginPanel.add(userTextField);
        loginPanel.add(passLabel);
        loginPanel.add(passTextField);
        loginPanel.add(new JLabel());
        loginPanel.add(button);

        loginPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        loginFrame.add(loginPanel);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);
        loginFrame.setVisible(true);
    }
}
