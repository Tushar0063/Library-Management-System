package dao;

import model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterUI extends JFrame {

    public RegisterUI() {
        setTitle("User Registration");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel roleLabel = new JLabel("Role:");
        JTextField roleField = new JTextField(); // Or use a dropdown if needed

        JButton registerButton = new JButton("Register");

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(roleLabel);
        panel.add(roleField);
        panel.add(new JLabel());
        panel.add(registerButton);

        add(panel);

        registerButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String email = emailField.getText();
            String role = roleField.getText();

            User newUser = new User(0, username, password, role, email);

            if (UserDAO.register(newUser)) {
                JOptionPane.showMessageDialog(null, "✅ Registered successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "❌ Username already exists or error.");
            }
        });
    }
}
