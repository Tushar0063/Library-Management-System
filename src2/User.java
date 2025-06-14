package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private String email;

    public User(int id, String username, String password, String role, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public int getId() { return id; }

    public String getUsername() { return username; }

    // ✅ Added to support ReminderService
    public String getName() {
        return username;
    }

    public String getPassword() { return password; }

    public String getRole() { return role; }

    public String getEmail() { return email; }
}
