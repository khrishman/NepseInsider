package controller;

import model.User;
import model.User.UserRole;
import model.User.UserStatus;
import util.ValidationUtils;
import util.ValidationUtils.ValidationResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserController - Manages user authentication and operations
 */
public class UserController {
    
    private Map<String, User> users;
    private User currentUser;
    private int nextUserId;
    private static UserController instance;
    
    private UserController() {
        users = new HashMap<>();
        nextUserId = 1;
        initializeDefaultUsers();
    }
    
    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }
    
    private void initializeDefaultUsers() {
        // Create admin user (username: admin, password: admin)
        User admin = new User("admin", "admin", UserRole.ADMIN);
        admin.setId(nextUserId++);
        admin.setEmail("admin@nepseinsider.com");
        admin.setFullName("System Administrator");
        users.put("admin", admin);
        
        // Create user: khrishman / khadka
        User khrishman = new User(nextUserId++, "khrishman", "khadka", "khrishman@email.com", "Khrishman Khadka");
        khrishman.setBalance(100000);
        users.put("khrishman", khrishman);
        
        // Create sample regular users
        User john = new User(nextUserId++, "john", "john123", "john@email.com", "John Doe");
        john.setBalance(50000);
        users.put("john", john);
        
        User ram = new User(nextUserId++, "ram", "ram123", "ram@email.com", "Ram Sharma");
        ram.setBalance(75000);
        users.put("ram", ram);
    }
    
    public ValidationResult login(String username, String password) {
        if (ValidationUtils.isEmpty(username)) {
            return ValidationResult.error("Username cannot be empty");
        }
        if (ValidationUtils.isEmpty(password)) {
            return ValidationResult.error("Password cannot be empty");
        }
        
        User user = users.get(username.toLowerCase());
        
        if (user == null) {
            return ValidationResult.error("Invalid username or password");
        }
        
        if (!user.authenticate(password)) {
            return ValidationResult.error("Invalid username or password");
        }
        
        if (!user.isActive()) {
            return ValidationResult.error("Your account has been suspended");
        }
        
        currentUser = user;
        user.setLastLogin(LocalDateTime.now());
        
        return ValidationResult.success();
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public ValidationResult register(String username, String password, String email, String fullName) {
        ValidationResult usernameResult = ValidationUtils.validateUsername(username);
        if (!usernameResult.isValid()) return usernameResult;
        
        ValidationResult passwordResult = ValidationUtils.validatePassword(password);
        if (!passwordResult.isValid()) return passwordResult;
        
        ValidationResult emailResult = ValidationUtils.validateEmail(email);
        if (!emailResult.isValid()) return emailResult;
        
        if (ValidationUtils.isEmpty(fullName)) {
            return ValidationResult.error("Full name cannot be empty");
        }
        
        if (users.containsKey(username.toLowerCase())) {
            return ValidationResult.error("Username already exists");
        }
        
        for (User user : users.values()) {
            if (user.getEmail() != null && user.getEmail().equalsIgnoreCase(email)) {
                return ValidationResult.error("Email already registered");
            }
        }
        
        User newUser = new User(nextUserId++, username.toLowerCase(), password, email, fullName);
        users.put(username.toLowerCase(), newUser);
        
        return ValidationResult.success();
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    public User getUser(String username) {
        return users.get(username.toLowerCase());
    }
    
    public ValidationResult updateUserStatus(String username, UserStatus status) {
        User user = users.get(username.toLowerCase());
        if (user == null) {
            return ValidationResult.error("User not found");
        }
        
        if (currentUser != null && currentUser.getUsername().equalsIgnoreCase(username) 
                && status != UserStatus.ACTIVE) {
            return ValidationResult.error("Cannot change your own status");
        }
        
        user.setStatus(status);
        return ValidationResult.success();
    }
    
    public ValidationResult deleteUser(String username) {
        if (!users.containsKey(username.toLowerCase())) {
            return ValidationResult.error("User not found");
        }
        
        if (currentUser != null && currentUser.getUsername().equalsIgnoreCase(username)) {
            return ValidationResult.error("Cannot delete your own account");
        }
        
        if (username.equalsIgnoreCase("admin")) {
            return ValidationResult.error("Cannot delete admin account");
        }
        
        users.remove(username.toLowerCase());
        return ValidationResult.success();
    }
    
    public ValidationResult addUser(String username, String password, String email, String fullName, UserRole role) {
        if (users.containsKey(username.toLowerCase())) {
            return ValidationResult.error("Username already exists");
        }
        
        User newUser = new User(nextUserId++, username.toLowerCase(), password, email, fullName);
        newUser.setRole(role);
        users.put(username.toLowerCase(), newUser);
        
        return ValidationResult.success();
    }
    
    public int getTotalUsers() {
        return users.size();
    }
    
    public int getActiveUsers() {
        return (int) users.values().stream().filter(User::isActive).count();
    }
}
