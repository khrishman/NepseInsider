package util;

import model.Stock;
import java.util.ArrayList;
import java.util.List;

/**
 * Sorting Algorithms for NepseInsider
 * Implements QuickSort and MergeSort for efficient stock sorting
 */
public class SortAlgorithms {
    
    /**
     * Enum for sort order
     */
    public enum SortOrder {
        ASCENDING, DESCENDING
    }
    
    /**
     * Enum for sort criteria
     */
    public enum SortCriteria {
        SYMBOL, NAME, PRICE, MARKET_CAP, VOLUME, YEAR, CHANGE_PERCENT
    }
    
    /**
     * QuickSort Implementation - O(n log n) average case
     * Efficient for most datasets, in-place sorting
     */
    public static class QuickSort {
        
        /**
         * Sort stocks by specified criteria
         * @param stocks List to sort (modified in place)
         * @param criteria Sort criteria
         * @param order Sort order
         */
        public static void sort(List<Stock> stocks, SortCriteria criteria, SortOrder order) {
            if (stocks == null || stocks.size() <= 1) {
                return;
            }
            
            quickSort(stocks, 0, stocks.size() - 1, criteria, order);
        }
        
        private static void quickSort(List<Stock> stocks, int low, int high, 
                SortCriteria criteria, SortOrder order) {
            if (low < high) {
                int pivotIndex = partition(stocks, low, high, criteria, order);
                quickSort(stocks, low, pivotIndex - 1, criteria, order);
                quickSort(stocks, pivotIndex + 1, high, criteria, order);
            }
        }
        
        private static int partition(List<Stock> stocks, int low, int high, 
                SortCriteria criteria, SortOrder order) {
            Stock pivot = stocks.get(high);
            int i = low - 1;
            
            for (int j = low; j < high; j++) {
                if (shouldSwap(stocks.get(j), pivot, criteria, order)) {
                    i++;
                    swap(stocks, i, j);
                }
            }
            
            swap(stocks, i + 1, high);
            return i + 1;
        }
        
        private static boolean shouldSwap(Stock a, Stock b, SortCriteria criteria, SortOrder order) {
            int comparison = compare(a, b, criteria);
            return order == SortOrder.ASCENDING ? comparison < 0 : comparison > 0;
        }
        
        /**
         * Sort stocks by price (convenience method)
         */
        public static void sortByPrice(List<Stock> stocks, SortOrder order) {
            sort(stocks, SortCriteria.PRICE, order);
        }
        
        /**
         * Sort stocks by market cap (convenience method)
         */
        public static void sortByMarketCap(List<Stock> stocks, SortOrder order) {
            sort(stocks, SortCriteria.MARKET_CAP, order);
        }
        
        /**
         * Sort stocks by volume (convenience method)
         */
        public static void sortByVolume(List<Stock> stocks, SortOrder order) {
            sort(stocks, SortCriteria.VOLUME, order);
        }
        
        /**
         * Sort stocks by change percent (convenience method)
         */
        public static void sortByChangePercent(List<Stock> stocks, SortOrder order) {
            sort(stocks, SortCriteria.CHANGE_PERCENT, order);
        }
    }
    
    /**
     * MergeSort Implementation - O(n log n) guaranteed
     * Stable sort, good for large datasets
     */
    public static class MergeSort {
        
        /**
         * Sort stocks by specified criteria
         * @param stocks List to sort
         * @param criteria Sort criteria
         * @param order Sort order
         * @return New sorted list (original unchanged)
         */
        public static List<Stock> sort(List<Stock> stocks, SortCriteria criteria, SortOrder order) {
            if (stocks == null || stocks.size() <= 1) {
                return stocks;
            }
            
            List<Stock> result = new ArrayList<>(stocks);
            mergeSort(result, 0, result.size() - 1, criteria, order);
            return result;
        }
        
        private static void mergeSort(List<Stock> stocks, int left, int right,
                SortCriteria criteria, SortOrder order) {
            if (left < right) {
                int mid = left + (right - left) / 2;
                
                mergeSort(stocks, left, mid, criteria, order);
                mergeSort(stocks, mid + 1, right, criteria, order);
                
                merge(stocks, left, mid, right, criteria, order);
            }
        }
        
        private static void merge(List<Stock> stocks, int left, int mid, int right,
                SortCriteria criteria, SortOrder order) {
            // Create temp arrays
            List<Stock> leftArray = new ArrayList<>();
            List<Stock> rightArray = new ArrayList<>();
            
            for (int i = left; i <= mid; i++) {
                leftArray.add(stocks.get(i));
            }
            for (int i = mid + 1; i <= right; i++) {
                rightArray.add(stocks.get(i));
            }
            
            // Merge back
            int i = 0, j = 0, k = left;
            
            while (i < leftArray.size() && j < rightArray.size()) {
                int comparison = compare(leftArray.get(i), rightArray.get(j), criteria);
                boolean takeLeft = order == SortOrder.ASCENDING ? comparison <= 0 : comparison >= 0;
                
                if (takeLeft) {
                    stocks.set(k++, leftArray.get(i++));
                } else {
                    stocks.set(k++, rightArray.get(j++));
                }
            }
            
            // Copy remaining elements
            while (i < leftArray.size()) {
                stocks.set(k++, leftArray.get(i++));
            }
            while (j < rightArray.size()) {
                stocks.set(k++, rightArray.get(j++));
            }
        }
        
        /**
         * Sort stocks by symbol (convenience method)
         */
        public static List<Stock> sortBySymbol(List<Stock> stocks, SortOrder order) {
            return sort(stocks, SortCriteria.SYMBOL, order);
        }
        
        /**
         * Sort stocks by name (convenience method)
         */
        public static List<Stock> sortByName(List<Stock> stocks, SortOrder order) {
            return sort(stocks, SortCriteria.NAME, order);
        }
        
        /**
         * Sort stocks by year (convenience method)
         */
        public static List<Stock> sortByYear(List<Stock> stocks, SortOrder order) {
            return sort(stocks, SortCriteria.YEAR, order);
        }
    }
    
    /**
     * Compare two stocks based on criteria
     */
    private static int compare(Stock a, Stock b, SortCriteria criteria) {
        switch (criteria) {
            case SYMBOL:
                return a.getSymbol().compareToIgnoreCase(b.getSymbol());
            case NAME:
                return a.getCompanyName().compareToIgnoreCase(b.getCompanyName());
            case PRICE:
                return Double.compare(a.getCurrentPrice(), b.getCurrentPrice());
            case MARKET_CAP:
                return Double.compare(a.getMarketCap(), b.getMarketCap());
            case VOLUME:
                return Long.compare(a.getVolume(), b.getVolume());
            case YEAR:
                return Integer.compare(a.getYearListed(), b.getYearListed());
            case CHANGE_PERCENT:
                return Double.compare(a.getChangePercent(), b.getChangePercent());
            default:
                return 0;
        }
    }
    
    /**
     * Swap two elements in list
     */
    private static void swap(List<Stock> stocks, int i, int j) {
        Stock temp = stocks.get(i);
        stocks.set(i, stocks.get(j));
        stocks.set(j, temp);
    }
    
    /**
     * Utility method to get sorted copy without modifying original
     */
    public static List<Stock> getSortedCopy(List<Stock> stocks, SortCriteria criteria, SortOrder order) {
        List<Stock> copy = new ArrayList<>(stocks);
        QuickSort.sort(copy, criteria, order);
        return copy;
    }
}
