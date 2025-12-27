package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User Model - Represents a user in the NepseInsider system
 * Handles user authentication and profile data
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum UserRole {
        ADMIN, USER
    }
    
    public enum UserStatus {
        ACTIVE, INACTIVE, SUSPENDED, PENDING
    }
    
    private int id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private UserRole role;
    private UserStatus status;
    private double balance;
    private LocalDateTime createdDate;
    private LocalDateTime lastLogin;
    
    // Constructor for new user
    public User(int id, String username, String password, String email, String fullName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.role = UserRole.USER;
        this.status = UserStatus.ACTIVE;
        this.balance = 0.0;
        this.createdDate = LocalDateTime.now();
    }
    
    // Constructor for admin
    public User(String username, String password, UserRole role) {
        this.id = 0;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = UserStatus.ACTIVE;
        this.createdDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    
    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }
    
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
    
    // Authentication method
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
    
    // Check if user is admin
    public boolean isAdmin() {
        return this.role == UserRole.ADMIN;
    }
    
    // Check if user is active
    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }
    
    // Deposit balance
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }
    
    // Withdraw balance
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return String.format("User[%s, %s, %s]", username, email, role);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return username.equalsIgnoreCase(user.username);
    }
    
    @Override
    public int hashCode() {
        return username.toLowerCase().hashCode();
    }
}
