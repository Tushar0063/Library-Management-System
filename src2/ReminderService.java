package service;

import dao.ReservationDAO;
import dao.UserDAO;
import model.Reservation;
import model.User;

import util.EmailUtil;

import java.util.List;

public class ReminderService {

    public static void sendDueDateReminders() {
        List<Reservation> dueSoon = ReservationDAO.getDueSoonReservations(3); // Due in 3 days
        for (Reservation res : dueSoon) {
            User user = UserDAO.getUserById(res.getUserId());
            if (user != null && user.getEmail() != null) {
                String subject = "📅 Book Due Reminder";
                String message = "Dear " + user.getName() + ",\n\nYour book (ID: " + res.getBookId() + ") is due on: " + res.getDueDate() + ".\nPlease return it on time.\n\nLibrary Team";
                EmailUtil.sendEmail(user.getEmail(), subject, message);
            }
        }
    }
}
