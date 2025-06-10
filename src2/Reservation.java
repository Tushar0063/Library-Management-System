package model;

import java.sql.Date;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private int userId;
    private int bookId;
    private Date reservationDate;
    private Date dueDate;
    private boolean returned;

    public Reservation() {}

    public Reservation(int id, int userId, int bookId, Date reservationDate, Date dueDate, boolean returned) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.reservationDate = reservationDate;
        this.dueDate = dueDate;
        this.returned = returned;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public Date getReservationDate() { return reservationDate; }
    public void setReservationDate(Date reservationDate) { this.reservationDate = reservationDate; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public boolean isReturned() { return returned; }
    public void setReturned(boolean returned) { this.returned = returned; }

    // ✅ New method to check if the book is due soon or overdue
    public boolean isDueSoon(int daysBefore) {
        if (returned || dueDate == null) return false;
        LocalDate due = dueDate.toLocalDate();
        LocalDate today = LocalDate.now();
        LocalDate reminderDate = today.plusDays(daysBefore);
        return !returned && (due.equals(reminderDate) || due.isBefore(today));
    }

    public long calculateFine() {
        if (!returned && dueDate != null) {
            long daysLate = (System.currentTimeMillis() - dueDate.getTime()) / (1000 * 60 * 60 * 24);
            return daysLate > 0 ? daysLate * 2 : 0; // ₹2 per day late
        }
        return 0;
    }
}
