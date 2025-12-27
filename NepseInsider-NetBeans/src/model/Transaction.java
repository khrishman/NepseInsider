package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Transaction Model - Base class for all financial transactions
 */
public abstract class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum TransactionStatus {
        PENDING, APPROVED, REJECTED, COMPLETED, CANCELLED
    }
    
    protected int id;
    protected int userId;
    protected String username;
    protected double amount;
    protected TransactionStatus status;
    protected String remarks;
    protected LocalDateTime createdDate;
    protected LocalDateTime processedDate;
    protected String processedBy;
    
    public Transaction(int id, int userId, String username, double amount) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.amount = amount;
        this.status = TransactionStatus.PENDING;
        this.createdDate = LocalDateTime.now();
    }
    
    // Common getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getProcessedDate() { return processedDate; }
    
    public String getProcessedBy() { return processedBy; }
    
    public void approve(String adminUsername) {
        this.status = TransactionStatus.APPROVED;
        this.processedDate = LocalDateTime.now();
        this.processedBy = adminUsername;
    }
    
    public void reject(String adminUsername, String reason) {
        this.status = TransactionStatus.REJECTED;
        this.processedDate = LocalDateTime.now();
        this.processedBy = adminUsername;
        this.remarks = reason;
    }
    
    public abstract String getTransactionType();
}

/**
 * Deposit Model - Represents a deposit transaction
 */
class Deposit extends Transaction {
    private static final long serialVersionUID = 1L;
    
    private String paymentMethod;
    private String transactionReference;
    private String proofImagePath;
    
    public Deposit(int id, int userId, String username, double amount, String paymentMethod) {
        super(id, userId, username, amount);
        this.paymentMethod = paymentMethod;
    }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }
    
    public String getProofImagePath() { return proofImagePath; }
    public void setProofImagePath(String proofImagePath) { this.proofImagePath = proofImagePath; }
    
    @Override
    public String getTransactionType() {
        return "DEPOSIT";
    }
    
    @Override
    public String toString() {
        return String.format("Deposit[User: %s, Amount: Rs.%.2f, Status: %s]", username, amount, status);
    }
}

/**
 * Withdrawal Model - Represents a withdrawal transaction
 */
class Withdrawal extends Transaction {
    private static final long serialVersionUID = 1L;
    
    private String bankName;
    private String accountNumber;
    private String accountHolderName;
    
    public Withdrawal(int id, int userId, String username, double amount) {
        super(id, userId, username, amount);
    }
    
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    
    public String getAccountHolderName() { return accountHolderName; }
    public void setAccountHolderName(String accountHolderName) { this.accountHolderName = accountHolderName; }
    
    @Override
    public String getTransactionType() {
        return "WITHDRAWAL";
    }
    
    @Override
    public String toString() {
        return String.format("Withdrawal[User: %s, Amount: Rs.%.2f, Status: %s]", username, amount, status);
    }
}
