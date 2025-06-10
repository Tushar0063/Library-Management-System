package ui;

import dao.AuditLogDAO;
import dao.BookDAO;
import dao.ReservationDAO;
import model.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class BorrowReturnUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public BorrowReturnUI() {
        setTitle("📘 Borrow/Return Books");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"ID", "User ID", "Book ID", "Reservation Date", "Due Date", "Returned"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton borrowButton = new JButton("➕ Borrow Book");
        JButton returnButton = new JButton("✅ Return Book");
        JPanel panel = new JPanel();
        panel.add(borrowButton);
        panel.add(returnButton);
        add(panel, BorderLayout.SOUTH);

        loadReservations();

        borrowButton.addActionListener(e -> borrowBook());
        returnButton.addActionListener(e -> returnBook());
    }

    private void loadReservations() {
        model.setRowCount(0);
        List<Reservation> reservations = ReservationDAO.getAllReservations();
        for (Reservation r : reservations) {
            model.addRow(new Object[]{
                    r.getId(),
                    r.getUserId(),
                    r.getBookId(),
                    r.getReservationDate(),
                    r.getDueDate(),
                    r.isReturned() ? "Yes" : "No"
            });
        }
    }

    private void borrowBook() {
        try {
            int userId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter User ID:"));
            int bookId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Book ID:"));

            if (!BookDAO.decreaseCopies(bookId)) {
                JOptionPane.showMessageDialog(this, "❌ Book is out of stock.");
                return;
            }

            LocalDate today = LocalDate.now();
            LocalDate due = today.plusDays(14);
            Reservation reservation = new Reservation(0, userId, bookId, Date.valueOf(today), Date.valueOf(due), false);

            if (ReservationDAO.addReservation(reservation)) {
                AuditLogDAO.logAction(userId, "Borrowed book ID: " + bookId);
                JOptionPane.showMessageDialog(this, "✅ Book borrowed successfully.");
                loadReservations();
            } else {
                BookDAO.increaseCopies(bookId);  // rollback
                JOptionPane.showMessageDialog(this, "❌ Failed to borrow book.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠️ Error: Invalid input.");
        }
    }


    private void returnBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Select a reservation to mark as returned.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        int userId = (int) model.getValueAt(row, 1);
        int bookId = (int) model.getValueAt(row, 2);

        if (ReservationDAO.markAsReturned(id)) {
            BookDAO.increaseCopies(bookId);
            AuditLogDAO.logAction(userId, "Returned book ID: " + bookId);
            JOptionPane.showMessageDialog(this, "✅ Book marked as returned.");
            loadReservations();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Failed to update reservation.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BorrowReturnUI().setVisible(true));
    }
}
