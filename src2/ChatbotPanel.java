package ui;

import ai.GPTService;
import javax.swing.*;
import java.awt.*;

public class ChatbotPanel extends JPanel {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public ChatbotPanel() {
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> handleInput());
        inputField.addActionListener(e -> handleInput());
    }

    private void handleInput() {
        String userInput = inputField.getText().trim();
        if (userInput.isEmpty()) return;

        chatArea.append("You: " + userInput + "\n");
        inputField.setText("");

        // Run GPT call in background
        new Thread(() -> {
            String response = GPTService.askGPT(userInput);
            SwingUtilities.invokeLater(() -> chatArea.append("Bot: " + response.trim() + "\n\n"));
        }).start();
    }
}
