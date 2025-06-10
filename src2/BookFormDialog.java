package ui;

import model.Book;
import dao.BookDAO;

import javax.swing.*;
import java.awt.*;

public class BookFormDialog extends JDialog {
    private JTextField titleField, authorField, isbnField, categoryField, copiesField;
    private boolean submitted = false;
    private Book existingBook;

    public BookFormDialog(Frame owner, Book book) {
        super(owner, book == null ? "Add Book" : "Edit Book", true);
        this.existingBook = book;

        setLayout(new GridLayout(7, 2, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(owner);

        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Author:"));
        authorField = new JTextField();
        add(authorField);

        add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        add(isbnField);

        add(new JLabel("Category:"));
        categoryField = new JTextField();
        add(categoryField);

        add(new JLabel("Copies:"));
        copiesField = new JTextField();
        add(copiesField);

        JButton submitButton = new JButton("✅ Submit");
        JButton cancelButton = new JButton("❌ Cancel");
        add(submitButton);
        add(cancelButton);

        // Pre-fill form if editing
        if (book != null) {
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            isbnField.setText(book.getIsbn());
            categoryField.setText(book.getCategory());
            copiesField.setText(String.valueOf(book.getCopies()));
        }

        submitButton.addActionListener(e -> {
            try {
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String isbn = isbnField.getText().trim();
                String category = categoryField.getText().trim();
                int copies = Integer.parseInt(copiesField.getText().trim());

                if (title.isEmpty() || isbn.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Title and ISBN are required.");
                    return;
                }

                Book newBook = new Book(
                        existingBook == null ? 0 : existingBook.getId(),
                        title, author, isbn, category, copies
                );

                boolean success;
                if (existingBook == null) {
                    success = BookDAO.addBook(newBook);
                } else {
                    success = BookDAO.updateBook(newBook);
                }

                if (success) {
                    submitted = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Operation failed.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for copies.");
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    public boolean isSubmitted() {
        return submitted;
    }
}
