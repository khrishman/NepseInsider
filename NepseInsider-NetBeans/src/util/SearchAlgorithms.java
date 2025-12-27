package util;

import model.Stock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Search Algorithms for NepseInsider
 * Implements Binary Search and Linear Search for stock lookup
 */
public class SearchAlgorithms {
    
    /**
     * Binary Search - O(log n) time complexity
     * Requires sorted list by the search criteria
     * Used for exact matches on sorted data
     */
    public static class BinarySearch {
        
        /**
         * Search for stock by symbol (exact match)
         * @param stocks Sorted list of stocks by symbol
         * @param symbol Symbol to search for
         * @return Index of found stock, or -1 if not found
         */
        public static int searchBySymbol(List<Stock> stocks, String symbol) {
            if (stocks == null || stocks.isEmpty() || symbol == null) {
                return -1;
            }
            
            // Ensure list is sorted by symbol
            List<Stock> sortedStocks = new ArrayList<>(stocks);
            Collections.sort(sortedStocks, (a, b) -> a.getSymbol().compareToIgnoreCase(b.getSymbol()));
            
            int left = 0;
            int right = sortedStocks.size() - 1;
            
            while (left <= right) {
                int mid = left + (right - left) / 2;
                String midSymbol = sortedStocks.get(mid).getSymbol();
                
                int comparison = midSymbol.compareToIgnoreCase(symbol);
                
                if (comparison == 0) {
                    return mid; // Found
                } else if (comparison < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
            return -1; // Not found
        }
        
        /**
         * Search for stock by price (exact match)
         * @param stocks Sorted list of stocks by price
         * @param price Price to search for
         * @return Index of found stock, or -1 if not found
         */
        public static int searchByPrice(List<Stock> stocks, double price) {
            if (stocks == null || stocks.isEmpty()) {
                return -1;
            }
            
            // Ensure list is sorted by price
            List<Stock> sortedStocks = new ArrayList<>(stocks);
            Collections.sort(sortedStocks, (a, b) -> Double.compare(a.getCurrentPrice(), b.getCurrentPrice()));
            
            int left = 0;
            int right = sortedStocks.size() - 1;
            
            while (left <= right) {
                int mid = left + (right - left) / 2;
                double midPrice = sortedStocks.get(mid).getCurrentPrice();
                
                if (Math.abs(midPrice - price) < 0.01) { // Allow small tolerance
                    return mid;
                } else if (midPrice < price) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
            return -1;
        }
        
        /**
         * Search for stock by year listed (exact match)
         * @param stocks Sorted list of stocks by year
         * @param year Year to search for
         * @return Index of found stock, or -1 if not found
         */
        public static int searchByYear(List<Stock> stocks, int year) {
            if (stocks == null || stocks.isEmpty()) {
                return -1;
            }
            
            // Ensure list is sorted by year
            List<Stock> sortedStocks = new ArrayList<>(stocks);
            Collections.sort(sortedStocks, (a, b) -> Integer.compare(a.getYearListed(), b.getYearListed()));
            
            int left = 0;
            int right = sortedStocks.size() - 1;
            
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int midYear = sortedStocks.get(mid).getYearListed();
                
                if (midYear == year) {
                    return mid;
                } else if (midYear < year) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            
            return -1;
        }
        
        /**
         * Find all stocks within a price range using modified binary search
         * @param stocks Sorted list by price
         * @param minPrice Minimum price
         * @param maxPrice Maximum price
         * @return List of stocks within price range
         */
        public static List<Stock> searchByPriceRange(List<Stock> stocks, double minPrice, double maxPrice) {
            List<Stock> result = new ArrayList<>();
            
            if (stocks == null || stocks.isEmpty()) {
                return result;
            }
            
            // Sort by price
            List<Stock> sortedStocks = new ArrayList<>(stocks);
            Collections.sort(sortedStocks, (a, b) -> Double.compare(a.getCurrentPrice(), b.getCurrentPrice()));
            
            // Find starting index using binary search
            int startIndex = findLowerBound(sortedStocks, minPrice);
            
            // Collect all stocks within range
            for (int i = startIndex; i < sortedStocks.size(); i++) {
                double price = sortedStocks.get(i).getCurrentPrice();
                if (price > maxPrice) {
                    break;
                }
                if (price >= minPrice) {
                    result.add(sortedStocks.get(i));
                }
            }
            
            return result;
        }
        
        // Helper: Find lower bound index
        private static int findLowerBound(List<Stock> stocks, double price) {
            int left = 0;
            int right = stocks.size();
            
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (stocks.get(mid).getCurrentPrice() < price) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            
            return left;
        }
    }
    
    /**
     * Linear Search - O(n) time complexity
     * Used for partial matches and flexible searching
     */
    public static class LinearSearch {
        
        /**
         * Search stocks by name (partial match)
         * @param stocks List of stocks to search
         * @param query Search query
         * @return List of matching stocks
         */
        public static List<Stock> searchByName(List<Stock> stocks, String query) {
            List<Stock> results = new ArrayList<>();
            
            if (stocks == null || query == null || query.trim().isEmpty()) {
                return results;
            }
            
            String searchQuery = query.toLowerCase().trim();
            
            for (Stock stock : stocks) {
                if (stock.getCompanyName().toLowerCase().contains(searchQuery) ||
                    stock.getSymbol().toLowerCase().contains(searchQuery)) {
                    results.add(stock);
                }
            }
            
            return results;
        }
        
        /**
         * Search stocks by sector (partial match)
         * @param stocks List of stocks to search
         * @param sector Sector to search for
         * @return List of matching stocks
         */
        public static List<Stock> searchBySector(List<Stock> stocks, String sector) {
            List<Stock> results = new ArrayList<>();
            
            if (stocks == null || sector == null || sector.trim().isEmpty()) {
                return results;
            }
            
            String searchSector = sector.toLowerCase().trim();
            
            for (Stock stock : stocks) {
                if (stock.getSector().toLowerCase().contains(searchSector)) {
                    results.add(stock);
                }
            }
            
            return results;
        }
        
        /**
         * Search stocks by multiple criteria
         * @param stocks List of stocks to search
         * @param name Name query (can be null/empty)
         * @param sector Sector query (can be null/empty)
         * @param minPrice Minimum price (use 0 for no min)
         * @param maxPrice Maximum price (use Double.MAX_VALUE for no max)
         * @return List of matching stocks
         */
        public static List<Stock> searchMultipleCriteria(List<Stock> stocks, 
                String name, String sector, double minPrice, double maxPrice) {
            List<Stock> results = new ArrayList<>();
            
            if (stocks == null) {
                return results;
            }
            
            for (Stock stock : stocks) {
                boolean matches = true;
                
                // Check name if provided
                if (name != null && !name.trim().isEmpty()) {
                    String searchName = name.toLowerCase().trim();
                    if (!stock.getCompanyName().toLowerCase().contains(searchName) &&
                        !stock.getSymbol().toLowerCase().contains(searchName)) {
                        matches = false;
                    }
                }
                
                // Check sector if provided
                if (matches && sector != null && !sector.trim().isEmpty()) {
                    if (!stock.getSector().toLowerCase().contains(sector.toLowerCase().trim())) {
                        matches = false;
                    }
                }
                
                // Check price range
                if (matches) {
                    double price = stock.getCurrentPrice();
                    if (price < minPrice || price > maxPrice) {
                        matches = false;
                    }
                }
                
                if (matches) {
                    results.add(stock);
                }
            }
            
            return results;
        }
        
        /**
         * Search for top gainers
         * @param stocks List of stocks
         * @param limit Number of results to return
         * @return List of top gaining stocks
         */
        public static List<Stock> findTopGainers(List<Stock> stocks, int limit) {
            List<Stock> gainers = new ArrayList<>();
            
            if (stocks == null) {
                return gainers;
            }
            
            for (Stock stock : stocks) {
                if (stock.getChangePercent() > 0) {
                    gainers.add(stock);
                }
            }
            
            // Sort by change percent descending
            gainers.sort((a, b) -> Double.compare(b.getChangePercent(), a.getChangePercent()));
            
            // Return limited results
            return gainers.subList(0, Math.min(limit, gainers.size()));
        }
        
        /**
         * Search for top losers
         * @param stocks List of stocks
         * @param limit Number of results to return
         * @return List of top losing stocks
         */
        public static List<Stock> findTopLosers(List<Stock> stocks, int limit) {
            List<Stock> losers = new ArrayList<>();
            
            if (stocks == null) {
                return losers;
            }
            
            for (Stock stock : stocks) {
                if (stock.getChangePercent() < 0) {
                    losers.add(stock);
                }
            }
            
            // Sort by change percent ascending (most negative first)
            losers.sort((a, b) -> Double.compare(a.getChangePercent(), b.getChangePercent()));
            
            // Return limited results
            return losers.subList(0, Math.min(limit, losers.size()));
        }
        
        /**
         * Find stock by exact symbol (case insensitive)
         * @param stocks List of stocks
         * @param symbol Symbol to find
         * @return Stock if found, null otherwise
         */
        public static Stock findBySymbol(List<Stock> stocks, String symbol) {
            if (stocks == null || symbol == null) {
                return null;
            }
            
            for (Stock stock : stocks) {
                if (stock.getSymbol().equalsIgnoreCase(symbol.trim())) {
                    return stock;
                }
            }
            
            return null;
        }
    }
}
