package controller;

import model.*;
import util.ValidationUtils;
import util.ValidationUtils.ValidationResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * AdminController - Manages all admin-related operations
 */
public class AdminController {
    
    private static AdminController instance;
    
    private List<Category> categories;
    private List<SubCategory> subCategories;
    private List<MarketData> markets;
    private List<UpcomingMarket> upcomingMarkets;
    private List<DepositTransaction> deposits;
    private List<WithdrawalTransaction> withdrawals;
    private List<SupportTicket> supportTickets;
    private List<CommentData> comments;
    private List<Subscriber> subscribers;
    
    private int nextId = 1;
    private SystemSettings systemSettings;
    
    private AdminController() {
        categories = new ArrayList<>();
        subCategories = new ArrayList<>();
        markets = new ArrayList<>();
        upcomingMarkets = new ArrayList<>();
        deposits = new ArrayList<>();
        withdrawals = new ArrayList<>();
        supportTickets = new ArrayList<>();
        comments = new ArrayList<>();
        subscribers = new ArrayList<>();
        systemSettings = SystemSettings.getInstance();
        
        initializeSampleData();
    }
    
    public static AdminController getInstance() {
        if (instance == null) {
            instance = new AdminController();
        }
        return instance;
    }
    
    private void initializeSampleData() {
        // Categories
        categories.add(new Category(nextId++, "Commercial Banks", "A-class financial institutions", true));
        categories.add(new Category(nextId++, "Development Banks", "B-class financial institutions", true));
        categories.add(new Category(nextId++, "Finance Companies", "C-class financial institutions", true));
        categories.add(new Category(nextId++, "Hydropower", "Electricity generation", true));
        categories.add(new Category(nextId++, "Life Insurance", "Life insurance companies", true));
        categories.add(new Category(nextId++, "Non-Life Insurance", "General insurance", true));
        categories.add(new Category(nextId++, "Hotels", "Tourism sector", true));
        categories.add(new Category(nextId++, "Manufacturing", "Manufacturing companies", true));
        
        // SubCategories
        subCategories.add(new SubCategory(nextId++, 1, "Class A Banks", "Major commercial banks"));
        subCategories.add(new SubCategory(nextId++, 1, "Joint Venture Banks", "Foreign joint ventures"));
        subCategories.add(new SubCategory(nextId++, 4, "Major Hydro", "Large hydropower projects"));
        subCategories.add(new SubCategory(nextId++, 4, "Small Hydro", "Small hydropower projects"));
        
        // Markets
        MarketData nepse = new MarketData(nextId++, "NEPSE", "Nepal Stock Exchange Index");
        nepse.setIndexValue(2456.78);
        nepse.setChange(26.28);
        nepse.setChangePercent(1.08);
        nepse.setHigh(2480.00);
        nepse.setLow(2420.00);
        nepse.setVolume(5250000);
        nepse.setTurnover(250000000);
        nepse.setStatus("OPEN");
        markets.add(nepse);
        
        MarketData sensitive = new MarketData(nextId++, "SENSITIVE", "Sensitive Index");
        sensitive.setIndexValue(458.32);
        sensitive.setChange(6.22);
        sensitive.setChangePercent(1.38);
        sensitive.setStatus("OPEN");
        markets.add(sensitive);
        
        // Upcoming Markets (IPOs)
        upcomingMarkets.add(new UpcomingMarket(nextId++, "ABC Hydropower", "ABCH", "IPO", "2025-02-15", 100.00, 1000000));
        upcomingMarkets.add(new UpcomingMarket(nextId++, "XYZ Finance", "XYZF", "FPO", "2025-03-01", 150.00, 500000));
        
        // Deposits
        deposits.add(new DepositTransaction(nextId++, "john", 10000, "Bank Transfer", "PENDING", LocalDateTime.now().minusHours(2)));
        deposits.add(new DepositTransaction(nextId++, "ram", 25000, "eSewa", "APPROVED", LocalDateTime.now().minusDays(1)));
        deposits.add(new DepositTransaction(nextId++, "khrishman", 50000, "Khalti", "PENDING", LocalDateTime.now().minusMinutes(30)));
        
        // Withdrawals
        withdrawals.add(new WithdrawalTransaction(nextId++, "john", 5000, "Nabil Bank", "1234567890", "PENDING", LocalDateTime.now().minusHours(3)));
        withdrawals.add(new WithdrawalTransaction(nextId++, "ram", 8000, "NIC Asia", "0987654321", "APPROVED", LocalDateTime.now().minusDays(2)));
        
        // Support Tickets
        supportTickets.add(new SupportTicket(nextId++, "john", "Login Issue", "Cannot login to account", "OPEN", LocalDateTime.now().minusHours(1)));
        supportTickets.add(new SupportTicket(nextId++, "khrishman", "Deposit Not Credited", "My deposit is pending for 2 days", "IN_PROGRESS", LocalDateTime.now().minusDays(1)));
        
        // Comments
        comments.add(new CommentData(nextId++, "john", "NABIL", "Great stock for investment!", "APPROVED"));
        comments.add(new CommentData(nextId++, "ram", "NICA", "Good quarterly results", "PENDING"));
        comments.add(new CommentData(nextId++, "khrishman", "UPPER", "Hydro sector is booming", "PENDING"));
        
        // Subscribers
        subscribers.add(new Subscriber(nextId++, "subscriber1@email.com", true));
        subscribers.add(new Subscriber(nextId++, "subscriber2@email.com", true));
        subscribers.add(new Subscriber(nextId++, "unsubscribed@email.com", false));
    }
    
    // ==================== Categories ====================
    public List<Category> getAllCategories() { return new ArrayList<>(categories); }
    
    public ValidationResult addCategory(String name, String description) {
        if (ValidationUtils.isEmpty(name)) return ValidationResult.error("Name is required");
        categories.add(new Category(nextId++, name, description, true));
        return ValidationResult.success();
    }
    
    public ValidationResult updateCategory(int id, String name, String description, boolean active) {
        for (Category c : categories) {
            if (c.id == id) {
                c.name = name;
                c.description = description;
                c.active = active;
                return ValidationResult.success();
            }
        }
        return ValidationResult.error("Category not found");
    }
    
    public ValidationResult deleteCategory(int id) {
        return categories.removeIf(c -> c.id == id) ? ValidationResult.success() : ValidationResult.error("Not found");
    }
    
    // ==================== SubCategories ====================
    public List<SubCategory> getAllSubCategories() { return new ArrayList<>(subCategories); }
    
    public ValidationResult addSubCategory(int categoryId, String name, String description) {
        if (ValidationUtils.isEmpty(name)) return ValidationResult.error("Name is required");
        subCategories.add(new SubCategory(nextId++, categoryId, name, description));
        return ValidationResult.success();
    }
    
    public ValidationResult deleteSubCategory(int id) {
        return subCategories.removeIf(s -> s.id == id) ? ValidationResult.success() : ValidationResult.error("Not found");
    }
    
    // ==================== Markets ====================
    public List<MarketData> getAllMarkets() { return new ArrayList<>(markets); }
    public MarketData getPrimaryMarket() { return markets.isEmpty() ? null : markets.get(0); }
    
    public ValidationResult updateMarketIndex(int id, double value, double change) {
        for (MarketData m : markets) {
            if (m.id == id) {
                m.setIndexValue(value);
                m.setChange(change);
                m.setChangePercent((change / (value - change)) * 100);
                return ValidationResult.success();
            }
        }
        return ValidationResult.error("Market not found");
    }
    
    // ==================== Upcoming Markets ====================
    public List<UpcomingMarket> getAllUpcomingMarkets() { return new ArrayList<>(upcomingMarkets); }
    
    public ValidationResult addUpcomingMarket(String company, String symbol, String type, String date, double price, long units) {
        upcomingMarkets.add(new UpcomingMarket(nextId++, company, symbol, type, date, price, units));
        return ValidationResult.success();
    }
    
    public ValidationResult deleteUpcomingMarket(int id) {
        return upcomingMarkets.removeIf(u -> u.id == id) ? ValidationResult.success() : ValidationResult.error("Not found");
    }
    
    // ==================== Deposits ====================
    public List<DepositTransaction> getAllDeposits() { return new ArrayList<>(deposits); }
    public List<DepositTransaction> getPendingDeposits() {
        List<DepositTransaction> pending = new ArrayList<>();
        for (DepositTransaction d : deposits) if ("PENDING".equals(d.status)) pending.add(d);
        return pending;
    }
    
    public ValidationResult approveDeposit(int id) {
        for (DepositTransaction d : deposits) {
            if (d.id == id) { d.status = "APPROVED"; d.processedDate = LocalDateTime.now(); return ValidationResult.success(); }
        }
        return ValidationResult.error("Not found");
    }
    
    public ValidationResult rejectDeposit(int id, String reason) {
        for (DepositTransaction d : deposits) {
            if (d.id == id) { d.status = "REJECTED"; d.remarks = reason; d.processedDate = LocalDateTime.now(); return ValidationResult.success(); }
        }
        return ValidationResult.error("Not found");
    }
    
    // ==================== Withdrawals ====================
    public List<WithdrawalTransaction> getAllWithdrawals() { return new ArrayList<>(withdrawals); }
    public List<WithdrawalTransaction> getPendingWithdrawals() {
        List<WithdrawalTransaction> pending = new ArrayList<>();
        for (WithdrawalTransaction w : withdrawals) if ("PENDING".equals(w.status)) pending.add(w);
        return pending;
    }
    
    public ValidationResult approveWithdrawal(int id) {
        for (WithdrawalTransaction w : withdrawals) {
            if (w.id == id) { w.status = "APPROVED"; w.processedDate = LocalDateTime.now(); return ValidationResult.success(); }
        }
        return ValidationResult.error("Not found");
    }
    
    public ValidationResult rejectWithdrawal(int id, String reason) {
        for (WithdrawalTransaction w : withdrawals) {
            if (w.id == id) { w.status = "REJECTED"; w.remarks = reason; w.processedDate = LocalDateTime.now(); return ValidationResult.success(); }
        }
        return ValidationResult.error("Not found");
    }
    
    // ==================== Support Tickets ====================
    public List<SupportTicket> getAllTickets() { return new ArrayList<>(supportTickets); }
    public List<SupportTicket> getOpenTickets() {
        List<SupportTicket> open = new ArrayList<>();
        for (SupportTicket t : supportTickets) if ("OPEN".equals(t.status) || "IN_PROGRESS".equals(t.status)) open.add(t);
        return open;
    }
    
    public ValidationResult updateTicketStatus(int id, String status) {
        for (SupportTicket t : supportTickets) {
            if (t.id == id) { t.status = status; return ValidationResult.success(); }
        }
        return ValidationResult.error("Not found");
    }
    
    public ValidationResult replyToTicket(int id, String reply) {
        for (SupportTicket t : supportTickets) {
            if (t.id == id) { t.adminReply = reply; t.status = "REPLIED"; return ValidationResult.success(); }
        }
        return ValidationResult.error("Not found");
    }
    
    // ==================== Comments ====================
    public List<CommentData> getAllComments() { return new ArrayList<>(comments); }
    public List<CommentData> getPendingComments() {
        List<CommentData> pending = new ArrayList<>();
        for (CommentData c : comments) if ("PENDING".equals(c.status)) pending.add(c);
        return pending;
    }
    
    public ValidationResult approveComment(int id) {
        for (CommentData c : comments) {
            if (c.id == id) { c.status = "APPROVED"; return ValidationResult.success(); }
        }
        return ValidationResult.error("Not found");
    }
    
    public ValidationResult rejectComment(int id) {
        for (CommentData c : comments) {
            if (c.id == id) { c.status = "REJECTED"; return ValidationResult.success(); }
        }
        return ValidationResult.error("Not found");
    }
    
    public ValidationResult deleteComment(int id) {
        return comments.removeIf(c -> c.id == id) ? ValidationResult.success() : ValidationResult.error("Not found");
    }
    
    // ==================== Subscribers ====================
    public List<Subscriber> getAllSubscribers() { return new ArrayList<>(subscribers); }
    public int getActiveSubscribersCount() { return (int) subscribers.stream().filter(s -> s.active).count(); }
    
    public ValidationResult addSubscriber(String email) {
        if (!ValidationUtils.validateEmail(email).isValid()) return ValidationResult.error("Invalid email");
        for (Subscriber s : subscribers) if (s.email.equalsIgnoreCase(email)) return ValidationResult.error("Already subscribed");
        subscribers.add(new Subscriber(nextId++, email, true));
        return ValidationResult.success();
    }
    
    public ValidationResult removeSubscriber(int id) {
        return subscribers.removeIf(s -> s.id == id) ? ValidationResult.success() : ValidationResult.error("Not found");
    }
    
    // ==================== System Settings ====================
    public SystemSettings getSystemSettings() { return systemSettings; }
    
    // ==================== Dashboard Stats ====================
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCategories", categories.size());
        stats.put("totalSubCategories", subCategories.size());
        stats.put("totalMarkets", markets.size());
        stats.put("pendingDeposits", getPendingDeposits().size());
        stats.put("pendingWithdrawals", getPendingWithdrawals().size());
        stats.put("openTickets", getOpenTickets().size());
        stats.put("pendingComments", getPendingComments().size());
        stats.put("totalSubscribers", subscribers.size());
        stats.put("activeSubscribers", getActiveSubscribersCount());
        return stats;
    }
    
    // ==================== Data Classes ====================
    public static class Category {
        public int id;
        public String name;
        public String description;
        public boolean active;
        public LocalDateTime createdDate;
        
        public Category(int id, String name, String description, boolean active) {
            this.id = id; this.name = name; this.description = description; this.active = active;
            this.createdDate = LocalDateTime.now();
        }
    }
    
    public static class SubCategory {
        public int id;
        public int categoryId;
        public String name;
        public String description;
        public boolean active = true;
        
        public SubCategory(int id, int categoryId, String name, String description) {
            this.id = id; this.categoryId = categoryId; this.name = name; this.description = description;
        }
    }
    
    public static class MarketData {
        public int id;
        public String name;
        public String description;
        public double indexValue;
        public double change;
        public double changePercent;
        public double high;
        public double low;
        public long volume;
        public double turnover;
        public String status;
        
        public MarketData(int id, String name, String description) {
            this.id = id; this.name = name; this.description = description;
        }
        
        public void setIndexValue(double v) { this.indexValue = v; }
        public void setChange(double c) { this.change = c; }
        public void setChangePercent(double cp) { this.changePercent = cp; }
        public void setHigh(double h) { this.high = h; }
        public void setLow(double l) { this.low = l; }
        public void setVolume(long v) { this.volume = v; }
        public void setTurnover(double t) { this.turnover = t; }
        public void setStatus(String s) { this.status = s; }
    }
    
    public static class UpcomingMarket {
        public int id;
        public String companyName;
        public String symbol;
        public String type;
        public String eventDate;
        public double pricePerShare;
        public long totalUnits;
        public String status = "SCHEDULED";
        
        public UpcomingMarket(int id, String company, String symbol, String type, String date, double price, long units) {
            this.id = id; this.companyName = company; this.symbol = symbol; this.type = type;
            this.eventDate = date; this.pricePerShare = price; this.totalUnits = units;
        }
    }
    
    public static class DepositTransaction {
        public int id;
        public String username;
        public double amount;
        public String paymentMethod;
        public String status;
        public String remarks;
        public LocalDateTime createdDate;
        public LocalDateTime processedDate;
        
        public DepositTransaction(int id, String username, double amount, String method, String status, LocalDateTime created) {
            this.id = id; this.username = username; this.amount = amount; this.paymentMethod = method;
            this.status = status; this.createdDate = created;
        }
    }
    
    public static class WithdrawalTransaction {
        public int id;
        public String username;
        public double amount;
        public String bankName;
        public String accountNumber;
        public String status;
        public String remarks;
        public LocalDateTime createdDate;
        public LocalDateTime processedDate;
        
        public WithdrawalTransaction(int id, String username, double amount, String bank, String account, String status, LocalDateTime created) {
            this.id = id; this.username = username; this.amount = amount; this.bankName = bank;
            this.accountNumber = account; this.status = status; this.createdDate = created;
        }
    }
    
    public static class SupportTicket {
        public int id;
        public String username;
        public String subject;
        public String description;
        public String status;
        public String adminReply;
        public LocalDateTime createdDate;
        
        public SupportTicket(int id, String username, String subject, String desc, String status, LocalDateTime created) {
            this.id = id; this.username = username; this.subject = subject; this.description = desc;
            this.status = status; this.createdDate = created;
        }
    }
    
    public static class CommentData {
        public int id;
        public String username;
        public String stockSymbol;
        public String content;
        public String status;
        public LocalDateTime createdDate;
        
        public CommentData(int id, String username, String symbol, String content, String status) {
            this.id = id; this.username = username; this.stockSymbol = symbol; this.content = content;
            this.status = status; this.createdDate = LocalDateTime.now();
        }
    }
    
    public static class Subscriber {
        public int id;
        public String email;
        public boolean active;
        public LocalDateTime subscribedDate;
        
        public Subscriber(int id, String email, boolean active) {
            this.id = id; this.email = email; this.active = active; this.subscribedDate = LocalDateTime.now();
        }
    }
}
