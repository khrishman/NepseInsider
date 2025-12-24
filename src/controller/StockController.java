package controller;

import model.Stock;
import util.DataStructures.*;
import util.SearchAlgorithms.*;
import util.SortAlgorithms.*;
import util.ValidationUtils;
import util.ValidationUtils.ValidationResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * StockController - Manages all stock-related operations
 */
public class StockController {
    
    private ArrayList<Stock> stocks;
    private HashMap<String, Stock> stocksBySymbol;
    private StockQueue<Stock> recentlyAddedStocks;
    private int nextStockId;
    private static StockController instance;
    
    private StockController() {
        stocks = new ArrayList<>();
        stocksBySymbol = new HashMap<>();
        recentlyAddedStocks = new StockQueue<>(5);
        nextStockId = 1;
        initializeSampleData();
    }
    
    public static StockController getInstance() {
        if (instance == null) {
            instance = new StockController();
        }
        return instance;
    }
    
    private void initializeSampleData() {
        // Banking Sector
        addStockInternal(new Stock(nextStockId++, "NABIL", "Nabil Bank Limited", "Commercial Bank", 1250.00, 50000000000.0, 125000, 1984));
        addStockInternal(new Stock(nextStockId++, "NICA", "NIC Asia Bank Limited", "Commercial Bank", 850.00, 35000000000.0, 89000, 1998));
        addStockInternal(new Stock(nextStockId++, "SBL", "Siddhartha Bank Limited", "Commercial Bank", 420.00, 18000000000.0, 65000, 2002));
        addStockInternal(new Stock(nextStockId++, "GBIME", "Global IME Bank Limited", "Commercial Bank", 385.00, 42000000000.0, 78000, 2007));
        addStockInternal(new Stock(nextStockId++, "SANIMA", "Sanima Bank Limited", "Commercial Bank", 410.00, 25000000000.0, 45000, 2004));
        
        // Development Banks
        addStockInternal(new Stock(nextStockId++, "LBBL", "Lumbini Bikas Bank", "Development Bank", 480.00, 8000000000.0, 35000, 2005));
        addStockInternal(new Stock(nextStockId++, "MNBBL", "Muktinath Bikas Bank", "Development Bank", 520.00, 12000000000.0, 42000, 2007));
        
        // Hydropower
        addStockInternal(new Stock(nextStockId++, "NHPC", "Nepal Hydro Power", "Hydropower", 680.00, 15000000000.0, 55000, 1996));
        addStockInternal(new Stock(nextStockId++, "CHCL", "Chilime Hydropower", "Hydropower", 720.00, 22000000000.0, 68000, 2003));
        addStockInternal(new Stock(nextStockId++, "AKPL", "Arun Kabeli Power", "Hydropower", 385.00, 5000000000.0, 25000, 2010));
        addStockInternal(new Stock(nextStockId++, "UPPER", "Upper Tamakoshi", "Hydropower", 580.00, 35000000000.0, 85000, 2011));
        
        // Insurance
        addStockInternal(new Stock(nextStockId++, "NLIC", "Nepal Life Insurance", "Life Insurance", 1850.00, 28000000000.0, 95000, 2001));
        addStockInternal(new Stock(nextStockId++, "SICL", "Shikhar Insurance", "Non-Life Insurance", 920.00, 12000000000.0, 48000, 2004));
        
        // Others
        addStockInternal(new Stock(nextStockId++, "NTC", "Nepal Telecom", "Trading", 850.00, 85000000000.0, 150000, 1995));
        addStockInternal(new Stock(nextStockId++, "NRIC", "Nepal Reinsurance", "Insurance", 1200.00, 18000000000.0, 62000, 2015));
        
        // Set price changes
        stocks.get(0).setChangePercent(2.50);
        stocks.get(1).setChangePercent(-1.80);
        stocks.get(2).setChangePercent(0.50);
        stocks.get(3).setChangePercent(-0.30);
        stocks.get(4).setChangePercent(1.20);
        stocks.get(5).setChangePercent(3.50);
        stocks.get(6).setChangePercent(-2.10);
        stocks.get(7).setChangePercent(1.80);
        stocks.get(8).setChangePercent(-0.90);
        stocks.get(9).setChangePercent(2.30);
    }
    
    private void addStockInternal(Stock stock) {
        stocks.add(stock);
        stocksBySymbol.put(stock.getSymbol().toUpperCase(), stock);
        recentlyAddedStocks.enqueue(stock);
    }
    
    public ValidationResult addStock(String symbol, String companyName, String sector, 
            double price, double marketCap, long volume, int year) {
        
        ValidationResult symbolResult = ValidationUtils.validateStockSymbol(symbol);
        if (!symbolResult.isValid()) return symbolResult;
        
        ValidationResult nameResult = ValidationUtils.validateCompanyName(companyName);
        if (!nameResult.isValid()) return nameResult;
        
        if (stocksBySymbol.containsKey(symbol.toUpperCase())) {
            return ValidationResult.error("Stock with symbol " + symbol + " already exists");
        }
        
        Stock stock = new Stock(nextStockId++, symbol.toUpperCase(), companyName, sector, price, marketCap, volume, year);
        stocks.add(stock);
        stocksBySymbol.put(symbol.toUpperCase(), stock);
        recentlyAddedStocks.enqueue(stock);
        
        return ValidationResult.success();
    }
    
    public ValidationResult updateStock(String symbol, String companyName, String sector, 
            double price, double marketCap, long volume, int year) {
        
        Stock stock = stocksBySymbol.get(symbol.toUpperCase());
        if (stock == null) {
            return ValidationResult.error("Stock not found: " + symbol);
        }
        
        stock.setCompanyName(companyName);
        stock.setSector(sector);
        stock.setCurrentPrice(price);
        stock.setMarketCap(marketCap);
        stock.setVolume(volume);
        stock.setYearListed(year);
        
        return ValidationResult.success();
    }
    
    public ValidationResult deleteStock(String symbol) {
        Stock stock = stocksBySymbol.remove(symbol.toUpperCase());
        if (stock == null) {
            return ValidationResult.error("Stock not found: " + symbol);
        }
        stocks.remove(stock);
        return ValidationResult.success();
    }
    
    public Stock getStock(String symbol) {
        return stocksBySymbol.get(symbol.toUpperCase());
    }
    
    public List<Stock> getAllStocks() {
        return new ArrayList<>(stocks);
    }
    
    public int getTotalStocks() {
        return stocks.size();
    }
    
    // Search operations
    public List<Stock> searchByName(String query) {
        return LinearSearch.searchByName(stocks, query);
    }
    
    public List<Stock> searchBySector(String sector) {
        return LinearSearch.searchBySector(stocks, sector);
    }
    
    public List<Stock> searchMultipleCriteria(String name, String sector, double minPrice, double maxPrice) {
        return LinearSearch.searchMultipleCriteria(stocks, name, sector, minPrice, maxPrice);
    }
    
    // Sort operations
    public void sortByPrice(SortOrder order) {
        QuickSort.sortByPrice(stocks, order);
    }
    
    public void sortByMarketCap(SortOrder order) {
        QuickSort.sortByMarketCap(stocks, order);
    }
    
    public void sortByChangePercent(SortOrder order) {
        QuickSort.sortByChangePercent(stocks, order);
    }
    
    // Get top gainers/losers
    public List<Stock> getTopGainers(int limit) {
        return LinearSearch.findTopGainers(stocks, limit);
    }
    
    public List<Stock> getTopLosers(int limit) {
        return LinearSearch.findTopLosers(stocks, limit);
    }
    
    public LinkedList<Stock> getRecentlyAddedStocks() {
        return recentlyAddedStocks.getAll();
    }
    
    public Map<String, Integer> getStocksBySector() {
        Map<String, Integer> sectorCount = new HashMap<>();
        for (Stock stock : stocks) {
            sectorCount.merge(stock.getSector(), 1, Integer::sum);
        }
        return sectorCount;
    }
    
    public double getTotalMarketCap() {
        return stocks.stream().mapToDouble(Stock::getMarketCap).sum();
    }
    
    public List<String> getAllSectors() {
        List<String> sectors = new ArrayList<>();
        for (Stock stock : stocks) {
            if (!sectors.contains(stock.getSector())) {
                sectors.add(stock.getSector());
            }
        }
        return sectors;
    }
}
