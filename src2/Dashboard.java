package ui;

import model.User;
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard(User user) {
        setTitle("📚 Library Dashboard");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.decode("#2E86C1"));
        topPanel.setPreferredSize(new Dimension(800, 60));

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(welcomeLabel);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 📖 Manage Books Button
        JButton manageBooksBtn = new JButton("📖 Manage Books");
        manageBooksBtn.addActionListener(e -> {
            new ManageBooksUI().setVisible(true);
        });

        // 👤 Manage Users Button
        JButton manageUsersBtn = new JButton("👤 Manage Users");

        // 📊 View Stats Button
        JButton viewStatsBtn = new JButton("📊 View Stats");

        // 🔍 Search Books Button
        JButton searchBooksBtn = new JButton("🔍 Search Books");

        // 💸 View Fines Button
        JButton viewFinesBtn = new JButton("💸 View Fines");
        viewFinesBtn.addActionListener(e -> {
            new FinePanel(user).setVisible(true); // Pass current user
        });

        // 🤖 Chatbot Help Button
        JButton chatbotBtn = new JButton("🤖 Chatbot Help");
        chatbotBtn.addActionListener(e -> {
            JFrame chatFrame = new JFrame("Library Chatbot");
            chatFrame.setSize(400, 500);
            chatFrame.setLocationRelativeTo(null);
            chatFrame.add(new ChatbotPanel());
            chatFrame.setVisible(true);
        });

        // Add buttons to center panel
        centerPanel.add(manageBooksBtn);
        centerPanel.add(manageUsersBtn);
        centerPanel.add(viewStatsBtn);
        centerPanel.add(searchBooksBtn);
        centerPanel.add(viewFinesBtn);
        centerPanel.add(chatbotBtn);  // Add chatbot button here

        // Add panels to layout
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    // For testing standalone
    public static void main(String[] args) {
        User testUser = new User(1, "admin", "admin123", "Admin", "admin@example.com");
        SwingUtilities.invokeLater(() -> new Dashboard(testUser).setVisible(true));
    }
}
