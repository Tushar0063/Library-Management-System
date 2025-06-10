package ui;

import dao.BookDAO;
import model.Book;

import javax.swing.*;
import java.awt.*;

public class AddBookUI extends JFrame {

    public AddBookUI() {
        setTitle("➕ Add Book");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField isbnField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField copiesField = new JTextField("1");

        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("Copies:"));
        panel.add(copiesField);

        JButton submitBtn = new JButton("Add Book");
        panel.add(new JLabel());
        panel.add(submitBtn);

        add(panel);

        submitBtn.addActionListener(e -> {
            try {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String isbn = isbnField.getText().trim();
                String category = categoryField.getText().trim();
                int copies = Integer.parseInt(copiesField.getText().trim());

                if (title.isEmpty() || isbn.isEmpty() || category.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "❌ Title, ISBN, and Category are required.");
                    return;
                }

                Book book = new Book(title, author, isbn, category, copies);
                if (BookDAO.addBook(book)) {
                    JOptionPane.showMessageDialog(this, "✅ Book added successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error: Could not add book.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Invalid input: " + ex.getMessage());
            }
        });
    }
}
