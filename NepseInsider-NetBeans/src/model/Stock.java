package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Stock Model - Represents a stock entity in the NepseInsider system
 * Contains all stock-related data and validation logic
 */
public class Stock implements Serializable, Comparable<Stock> {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String symbol;
    private String companyName;
    private String sector;
    private double currentPrice;
    private double previousPrice;
    private double marketCap;
    private long volume;
    private int yearListed;
    private LocalDateTime addedDate;
    private double changePercent;
    
    // Constructor
    public Stock(int id, String symbol, String companyName, String sector, 
                 double currentPrice, double marketCap, long volume, int yearListed) {
        this.id = id;
        this.symbol = symbol;
        this.companyName = companyName;
        this.sector = sector;
        this.currentPrice = currentPrice;
        this.previousPrice = currentPrice;
        this.marketCap = marketCap;
        this.volume = volume;
        this.yearListed = yearListed;
        this.addedDate = LocalDateTime.now();
        this.changePercent = 0.0;
    }
    
    // Default constructor
    public Stock() {
        this.addedDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    
    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
    
    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) {
        this.previousPrice = this.currentPrice;
        this.currentPrice = currentPrice;
        calculateChangePercent();
    }
    
    public double getPreviousPrice() { return previousPrice; }
    public void setPreviousPrice(double previousPrice) { this.previousPrice = previousPrice; }
    
    public double getMarketCap() { return marketCap; }
    public void setMarketCap(double marketCap) { this.marketCap = marketCap; }
    
    public long getVolume() { return volume; }
    public void setVolume(long volume) { this.volume = volume; }
    
    public int getYearListed() { return yearListed; }
    public void setYearListed(int yearListed) { this.yearListed = yearListed; }
    
    public LocalDateTime getAddedDate() { return addedDate; }
    public void setAddedDate(LocalDateTime addedDate) { this.addedDate = addedDate; }
    
    public double getChangePercent() { return changePercent; }
    public void setChangePercent(double changePercent) { this.changePercent = changePercent; }
    
    // Calculate price change percentage
    private void calculateChangePercent() {
        if (previousPrice != 0) {
            this.changePercent = ((currentPrice - previousPrice) / previousPrice) * 100;
        }
    }
    
    // Check if stock is a gainer
    public boolean isGainer() {
        return changePercent > 0;
    }
    
    // Check if stock is a loser
    public boolean isLoser() {
        return changePercent < 0;
    }
    
    @Override
    public int compareTo(Stock other) {
        return this.symbol.compareTo(other.symbol);
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s (Rs. %.2f)", symbol, companyName, currentPrice);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Stock stock = (Stock) obj;
        return symbol.equalsIgnoreCase(stock.symbol);
    }
    
    @Override
    public int hashCode() {
        return symbol.toLowerCase().hashCode();
    }
}
