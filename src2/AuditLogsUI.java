package ui;

import dao.AuditLogDAO;
import model.AuditLog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AuditLogsUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public AuditLogsUI() {
        setTitle("📋 Audit Logs");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Log ID", "User ID", "Action", "Action Time"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadLogs();
    }

    private void loadLogs() {
        model.setRowCount(0);
        List<AuditLog> logs = AuditLogDAO.getAllLogs();
        for (AuditLog log : logs) {
            model.addRow(new Object[]{
                    log.getId(),
                    log.getUserId(),
                    log.getAction(),
                    log.getActionTime()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AuditLogsUI().setVisible(true));
    }
}
