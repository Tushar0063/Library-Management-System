package ui;

import dao.BookDAO;
import model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageBooksUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;

    public ManageBooksUI() {
        setTitle("📚 Manage Books");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Search bar
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("🔍 Search");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Author", "ISBN", "Category", "Copies"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("➕ Add Book");
        JButton editButton = new JButton("✏️ Edit Book");
        JButton deleteButton = new JButton("❌ Delete Book");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load all books initially
        loadBooks("");

        // 🔍 Search button
        searchButton.addActionListener(e -> loadBooks(searchField.getText()));

        // ➕ Add Book
        addButton.addActionListener(e -> {
            BookFormDialog form = new BookFormDialog(this, null);
            if (form.isSubmitted()) loadBooks("");
        });

        // ✏️ Edit Book
        editButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Select a book to edit.");
                return;
            }
            try {
                int id = (int) tableModel.getValueAt(row, 0);
                Book book = BookDAO.getBookById(id);
                if (book == null) {
                    JOptionPane.showMessageDialog(this, "❌ Book not found in database.");
                    return;
                }
                BookFormDialog form = new BookFormDialog(this, book);
                if (form.isSubmitted()) loadBooks("");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "⚠️ Error while editing the book.");
            }
        });

        // ❌ Delete Book
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Select a book to delete.");
                return;
            }
            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (BookDAO.deleteBook(id)) {
                    loadBooks("");
                    JOptionPane.showMessageDialog(this, "✅ Book deleted.");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Failed to delete book.");
                }
            }
        });
    }

    private void loadBooks(String keyword) {
        tableModel.setRowCount(0);
        List<Book> books = keyword.isEmpty() ? BookDAO.getAllBooks() : BookDAO.searchBooks(keyword);
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getCategory(),
                    book.getCopies()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManageBooksUI().setVisible(true));
    }
}