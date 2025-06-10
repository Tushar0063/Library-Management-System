package ui;

import dao.RegisterUI;
import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    public LoginUI() {
        setTitle("Library Login");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel userLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        JLabel registerLabel = new JLabel("<HTML><U>Register</U></HTML>");
        registerLabel.setForeground(Color.BLUE);
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel forgotLabel = new JLabel("<HTML><U>Forgot Password?</U></HTML>");
        forgotLabel.setForeground(Color.BLUE);
        forgotLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(new JLabel());  // empty
        panel.add(loginButton);
        panel.add(registerLabel);
        panel.add(forgotLabel);

        add(panel);

        // 🔐 Login button action
        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            User loggedInUser = UserDAO.login(user, pass); // ✅ now returns User
            if (loggedInUser != null) {
                JOptionPane.showMessageDialog(null, "✅ Login Successful!");

                // Pass the logged-in user to Dashboard if needed
                new Dashboard(loggedInUser).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "❌ Invalid username or password");
            }
        });

        // 📝 Register click action
        registerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new RegisterUI().setVisible(true);
            }
        });

        // 🔒 Forgot password action
        forgotLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, "🔐 Contact admin to reset password.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginUI().setVisible(true));
    }
}
