package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Market Model - Represents market data and status
 * Tracks market sessions and indices
 */
public class Market implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum MarketStatus {
        OPEN, CLOSED, PRE_MARKET, POST_MARKET, UPCOMING
    }
    
    private int id;
    private String name;
    private String description;
    private double indexValue;
    private double previousClose;
    private double changePercent;
    private double highValue;
    private double lowValue;
    private long totalVolume;
    private double totalTurnover;
    private int totalTransactions;
    private MarketStatus status;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private LocalDateTime lastUpdated;
    private boolean isLive;
    
    public Market(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = MarketStatus.CLOSED;
        this.lastUpdated = LocalDateTime.now();
        this.isLive = false;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public double getIndexValue() { return indexValue; }
    public void setIndexValue(double indexValue) {
        this.previousClose = this.indexValue;
        this.indexValue = indexValue;
        calculateChangePercent();
        this.lastUpdated = LocalDateTime.now();
    }
    
    public double getPreviousClose() { return previousClose; }
    public void setPreviousClose(double previousClose) { this.previousClose = previousClose; }
    
    public double getChangePercent() { return changePercent; }
    
    public double getHighValue() { return highValue; }
    public void setHighValue(double highValue) { this.highValue = highValue; }
    
    public double getLowValue() { return lowValue; }
    public void setLowValue(double lowValue) { this.lowValue = lowValue; }
    
    public long getTotalVolume() { return totalVolume; }
    public void setTotalVolume(long totalVolume) { this.totalVolume = totalVolume; }
    
    public double getTotalTurnover() { return totalTurnover; }
    public void setTotalTurnover(double totalTurnover) { this.totalTurnover = totalTurnover; }
    
    public int getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(int totalTransactions) { this.totalTransactions = totalTransactions; }
    
    public MarketStatus getStatus() { return status; }
    public void setStatus(MarketStatus status) { this.status = status; }
    
    public LocalDateTime getOpenTime() { return openTime; }
    public void setOpenTime(LocalDateTime openTime) { this.openTime = openTime; }
    
    public LocalDateTime getCloseTime() { return closeTime; }
    public void setCloseTime(LocalDateTime closeTime) { this.closeTime = closeTime; }
    
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    
    public boolean isLive() { return isLive; }
    public void setLive(boolean live) { this.isLive = live; }
    
    private void calculateChangePercent() {
        if (previousClose != 0) {
            this.changePercent = ((indexValue - previousClose) / previousClose) * 100;
        }
    }
    
    public boolean isPositive() {
        return changePercent >= 0;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %.2f (%.2f%%)", name, indexValue, changePercent);
    }
}

/**
 * UpcomingMarket Model - Represents upcoming market events/IPOs
 */
class UpcomingMarket implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum EventType {
        IPO, FPO, RIGHT_SHARE, BONUS_SHARE, DIVIDEND, AGM
    }
    
    private int id;
    private String companyName;
    private String symbol;
    private EventType eventType;
    private LocalDateTime eventDate;
    private double pricePerShare;
    private long totalUnits;
    private String description;
    private boolean active;
    private LocalDateTime createdDate;
    
    public UpcomingMarket(int id, String companyName, String symbol, EventType eventType) {
        this.id = id;
        this.companyName = companyName;
        this.symbol = symbol;
        this.eventType = eventType;
        this.active = true;
        this.createdDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public EventType getEventType() { return eventType; }
    public void setEventType(EventType eventType) { this.eventType = eventType; }
    
    public LocalDateTime getEventDate() { return eventDate; }
    public void setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; }
    
    public double getPricePerShare() { return pricePerShare; }
    public void setPricePerShare(double pricePerShare) { this.pricePerShare = pricePerShare; }
    
    public long getTotalUnits() { return totalUnits; }
    public void setTotalUnits(long totalUnits) { this.totalUnits = totalUnits; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    
    @Override
    public String toString() {
        return String.format("%s - %s (%s)", symbol, companyName, eventType);
    }
}
