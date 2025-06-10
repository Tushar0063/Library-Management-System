package model;

import java.sql.Timestamp;

public class AuditLog {
    private int id;
    private int userId;
    private String action;
    private Timestamp actionTime;

    public AuditLog(int id, int userId, String action, Timestamp actionTime) {
        this.id = id;
        this.userId = userId;
        this.action = action;
        this.actionTime = actionTime;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getAction() {
        return action;
    }

    public Timestamp getActionTime() {
        return actionTime;
    }
}
