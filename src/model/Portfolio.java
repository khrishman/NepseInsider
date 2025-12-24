package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Portfolio Model - Represents a user's stock portfolio
 * Manages portfolio holdings and calculates portfolio value
 */
public class Portfolio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String userId;
    private String portfolioName;
    private List<PortfolioItem> holdings;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdated;
    
    public Portfolio(int id, String userId, String portfolioName) {
        this.id = id;
        this.userId = userId;
        this.portfolioName = portfolioName;
        this.holdings = new ArrayList<>();
        this.createdDate = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getPortfolioName() { return portfolioName; }
    public void setPortfolioName(String portfolioName) { this.portfolioName = portfolioName; }
    
    public List<PortfolioItem> getHoldings() { return holdings; }
    public void setHoldings(List<PortfolioItem> holdings) { this.holdings = holdings; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    
    // Add stock to portfolio
    public void addHolding(PortfolioItem item) {
        holdings.add(item);
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Remove stock from portfolio
    public boolean removeHolding(String symbol) {
        boolean removed = holdings.removeIf(item -> item.getStock().getSymbol().equals(symbol));
        if (removed) {
            this.lastUpdated = LocalDateTime.now();
        }
        return removed;
    }
    
    // Calculate total portfolio value
    public double getTotalValue() {
        return holdings.stream()
                .mapToDouble(item -> item.getStock().getCurrentPrice() * item.getQuantity())
                .sum();
    }
    
    // Calculate total profit/loss
    public double getTotalProfitLoss() {
        return holdings.stream()
                .mapToDouble(PortfolioItem::getProfitLoss)
                .sum();
    }
    
    // Get number of holdings
    public int getHoldingsCount() {
        return holdings.size();
    }
    
    /**
     * Inner class representing a portfolio holding
     */
    public static class PortfolioItem implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private Stock stock;
        private int quantity;
        private double buyPrice;
        private LocalDateTime purchaseDate;
        
        public PortfolioItem(Stock stock, int quantity, double buyPrice) {
            this.stock = stock;
            this.quantity = quantity;
            this.buyPrice = buyPrice;
            this.purchaseDate = LocalDateTime.now();
        }
        
        public Stock getStock() { return stock; }
        public void setStock(Stock stock) { this.stock = stock; }
        
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        
        public double getBuyPrice() { return buyPrice; }
        public void setBuyPrice(double buyPrice) { this.buyPrice = buyPrice; }
        
        public LocalDateTime getPurchaseDate() { return purchaseDate; }
        
        // Calculate profit/loss for this holding
        public double getProfitLoss() {
            return (stock.getCurrentPrice() - buyPrice) * quantity;
        }
        
        // Calculate profit/loss percentage
        public double getProfitLossPercent() {
            if (buyPrice == 0) return 0;
            return ((stock.getCurrentPrice() - buyPrice) / buyPrice) * 100;
        }
        
        // Calculate current value
        public double getCurrentValue() {
            return stock.getCurrentPrice() * quantity;
        }
        
        // Calculate invested amount
        public double getInvestedAmount() {
            return buyPrice * quantity;
        }
    }
}
