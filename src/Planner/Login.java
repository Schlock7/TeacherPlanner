package Planner;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Login {
    String password = "password";
    String username = "thompson";

    public boolean verifyPassword(String passwordInput, String userInput) {
        return password.equals(passwordInput) && username.equals(userInput);
    }

    Login() {
        JFrame loginFrame = new JFrame("Login Frame");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel loginPanel = new JPanel(new GridLayout(0, 2, 20, 20));

        JLabel userLabel = new JLabel("Username: ");
        JLabel passLabel = new JLabel("Password: ");
        JTextField userTextField = new JTextField();
        JPasswordField passTextField = new JPasswordField();
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

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String passwordInput, userInput;
                passwordInput = passTextField.getText();
                userInput = userTextField.getText();
                if (verifyPassword(passwordInput, userInput)) {
                    new MainMenu();
                    loginFrame.setVisible(false);
                    loginFrame.dispose();
                } else {
                    System.out.println("password is incorrect");
                }
            }
        });
        passTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String passwordInput, userInput;
                passwordInput = passTextField.getText();
                userInput = userTextField.getText();
                if (verifyPassword(passwordInput, userInput)) {
                    new MainMenu();
                    loginFrame.setVisible(false);
                    loginFrame.dispose();
                } else {
                    System.out.println("password is incorrect");
                }
            }
        });
    }
}