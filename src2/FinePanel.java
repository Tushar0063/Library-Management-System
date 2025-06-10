package ui;

import dao.ReservationDAO;
import model.Reservation;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FinePanel extends JFrame {

    public FinePanel(User user) {
        setTitle("💸 Fine Details");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Book ID", "Due Date", "Returned", "Days Late", "Fine (₹)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        List<Reservation> reservations = ReservationDAO.getAllReservations();

        for (Reservation r : reservations) {
            if (user.getRole().equalsIgnoreCase("Admin") || r.getUserId() == user.getId()) {
                boolean returned = r.isReturned();
                LocalDate due = r.getDueDate().toLocalDate();
                long daysLate = 0;
                int fine = 0;

                if (!returned) {
                    LocalDate today = LocalDate.now();
                    if (today.isAfter(due)) {
                        daysLate = ChronoUnit.DAYS.between(due, today);
                        fine = (int) daysLate * 2;
                    }
                }

                model.addRow(new Object[]{
                        r.getBookId(),
                        due,
                        returned ? "Yes" : "No",
                        daysLate > 0 ? daysLate : "-",
                        fine > 0 ? "₹" + fine : "-"
                });
            }
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
