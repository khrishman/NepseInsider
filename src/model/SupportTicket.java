package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * SupportTicket Model - Represents a support ticket from users
 */
public class SupportTicket implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum TicketStatus {
        OPEN, IN_PROGRESS, RESOLVED, CLOSED
    }
    
    public enum TicketPriority {
        LOW, MEDIUM, HIGH, URGENT
    }
    
    private int id;
    private int userId;
    private String username;
    private String subject;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
    private String category;
    private List<TicketReply> replies;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;
    private String assignedTo;
    
    public SupportTicket(int id, int userId, String username, String subject, String description) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.subject = subject;
        this.description = description;
        this.status = TicketStatus.OPEN;
        this.priority = TicketPriority.MEDIUM;
        this.replies = new ArrayList<>();
        this.createdDate = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public TicketStatus getStatus() { return status; }
    public void setStatus(TicketStatus status) {
        this.status = status;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public TicketPriority getPriority() { return priority; }
    public void setPriority(TicketPriority priority) { this.priority = priority; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public List<TicketReply> getReplies() { return replies; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    
    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
    
    public void addReply(TicketReply reply) {
        replies.add(reply);
        this.lastUpdated = LocalDateTime.now();
    }
    
    public void close() {
        this.status = TicketStatus.CLOSED;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public void resolve() {
        this.status = TicketStatus.RESOLVED;
        this.lastUpdated = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return String.format("Ticket #%d: %s [%s]", id, subject, status);
    }
}

/**
 * TicketReply Model - Represents a reply to a support ticket
 */
class TicketReply implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private int ticketId;
    private String repliedBy;
    private boolean isAdmin;
    private String message;
    private LocalDateTime createdDate;
    
    public TicketReply(int id, int ticketId, String repliedBy, boolean isAdmin, String message) {
        this.id = id;
        this.ticketId = ticketId;
        this.repliedBy = repliedBy;
        this.isAdmin = isAdmin;
        this.message = message;
        this.createdDate = LocalDateTime.now();
    }
    
    // Getters
    public int getId() { return id; }
    public int getTicketId() { return ticketId; }
    public String getRepliedBy() { return repliedBy; }
    public boolean isAdmin() { return isAdmin; }
    public String getMessage() { return message; }
    public LocalDateTime getCreatedDate() { return createdDate; }
}

/**
 * Comment Model - Represents user comments on stocks/market
 */
class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum CommentStatus {
        PENDING, APPROVED, REJECTED
    }
    
    private int id;
    private int userId;
    private String username;
    private String stockSymbol;
    private String content;
    private CommentStatus status;
    private int likes;
    private LocalDateTime createdDate;
    
    public Comment(int id, int userId, String username, String stockSymbol, String content) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.stockSymbol = stockSymbol;
        this.content = content;
        this.status = CommentStatus.PENDING;
        this.likes = 0;
        this.createdDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    
    public String getStockSymbol() { return stockSymbol; }
    public void setStockSymbol(String stockSymbol) { this.stockSymbol = stockSymbol; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public CommentStatus getStatus() { return status; }
    public void setStatus(CommentStatus status) { this.status = status; }
    
    public int getLikes() { return likes; }
    public void addLike() { this.likes++; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    
    public void approve() {
        this.status = CommentStatus.APPROVED;
    }
    
    public void reject() {
        this.status = CommentStatus.REJECTED;
    }
    
    @Override
    public String toString() {
        return String.format("%s on %s: %s", username, stockSymbol, 
                content.length() > 50 ? content.substring(0, 47) + "..." : content);
    }
}

/**
 * Subscriber Model - Represents newsletter subscribers
 */
class Subscriber implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String email;
    private boolean active;
    private LocalDateTime subscribedDate;
    private LocalDateTime unsubscribedDate;
    
    public Subscriber(int id, String email) {
        this.id = id;
        this.email = email;
        this.active = true;
        this.subscribedDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public boolean isActive() { return active; }
    
    public LocalDateTime getSubscribedDate() { return subscribedDate; }
    public LocalDateTime getUnsubscribedDate() { return unsubscribedDate; }
    
    public void unsubscribe() {
        this.active = false;
        this.unsubscribedDate = LocalDateTime.now();
    }
    
    public void resubscribe() {
        this.active = true;
        this.unsubscribedDate = null;
    }
    
    @Override
    public String toString() {
        return String.format("%s [%s]", email, active ? "Active" : "Inactive");
    }
}
