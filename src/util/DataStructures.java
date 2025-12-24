package util;

import model.Stock;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Custom Data Structures for NepseInsider
 * Implements Stack and Queue for managing stocks
 */
public class DataStructures {
    
    /**
     * Custom Stack implementation for tracking price changes and undo operations
     * Uses LinkedList as underlying data structure
     */
    public static class StockStack<T> {
        private LinkedList<T> stack;
        private int maxSize;
        
        public StockStack() {
            this.stack = new LinkedList<>();
            this.maxSize = Integer.MAX_VALUE;
        }
        
        public StockStack(int maxSize) {
            this.stack = new LinkedList<>();
            this.maxSize = maxSize;
        }
        
        // Push element onto stack
        public void push(T item) {
            if (stack.size() >= maxSize) {
                stack.removeLast(); // Remove oldest if max size reached
            }
            stack.addFirst(item);
        }
        
        // Pop element from stack
        public T pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Stack is empty");
            }
            return stack.removeFirst();
        }
        
        // Peek at top element without removing
        public T peek() {
            if (isEmpty()) {
                throw new NoSuchElementException("Stack is empty");
            }
            return stack.getFirst();
        }
        
        // Check if stack is empty
        public boolean isEmpty() {
            return stack.isEmpty();
        }
        
        // Get size of stack
        public int size() {
            return stack.size();
        }
        
        // Clear the stack
        public void clear() {
            stack.clear();
        }
        
        // Get all elements as array
        public Object[] toArray() {
            return stack.toArray();
        }
    }
    
    /**
     * Custom Queue implementation for recently added stocks (FIFO)
     * Uses LinkedList as underlying data structure
     */
    public static class StockQueue<T> {
        private LinkedList<T> queue;
        private int maxSize;
        
        public StockQueue() {
            this.queue = new LinkedList<>();
            this.maxSize = 5; // Default: keep last 5 items
        }
        
        public StockQueue(int maxSize) {
            this.queue = new LinkedList<>();
            this.maxSize = maxSize;
        }
        
        // Enqueue element (add to back)
        public void enqueue(T item) {
            if (queue.size() >= maxSize) {
                queue.removeFirst(); // Remove oldest (front) if max size reached
            }
            queue.addLast(item);
        }
        
        // Dequeue element (remove from front)
        public T dequeue() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue is empty");
            }
            return queue.removeFirst();
        }
        
        // Peek at front element without removing
        public T peek() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue is empty");
            }
            return queue.getFirst();
        }
        
        // Peek at back element
        public T peekLast() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue is empty");
            }
            return queue.getLast();
        }
        
        // Check if queue is empty
        public boolean isEmpty() {
            return queue.isEmpty();
        }
        
        // Get size of queue
        public int size() {
            return queue.size();
        }
        
        // Clear the queue
        public void clear() {
            queue.clear();
        }
        
        // Get all elements as array
        public Object[] toArray() {
            return queue.toArray();
        }
        
        // Get all elements as LinkedList
        public LinkedList<T> getAll() {
            return new LinkedList<>(queue);
        }
        
        // Check if queue contains element
        public boolean contains(T item) {
            return queue.contains(item);
        }
    }
    
    /**
     * Recently Viewed Stocks Queue - specialized for tracking recently viewed stocks
     */
    public static class RecentlyViewedQueue extends StockQueue<Stock> {
        
        public RecentlyViewedQueue() {
            super(5); // Keep last 5 viewed stocks
        }
        
        public RecentlyViewedQueue(int maxSize) {
            super(maxSize);
        }
        
        // Add stock to recently viewed, moving to front if already exists
        public void addViewed(Stock stock) {
            // Remove if already exists (to move to end)
            LinkedList<Stock> all = getAll();
            all.removeIf(s -> s.getSymbol().equals(stock.getSymbol()));
            
            // Clear and re-add all
            clear();
            for (Stock s : all) {
                enqueue(s);
            }
            
            // Add the new/viewed stock
            enqueue(stock);
        }
    }
    
    /**
     * Price Change History Stack - specialized for tracking stock price changes
     */
    public static class PriceChangeStack {
        private StockStack<PriceChange> stack;
        
        public PriceChangeStack() {
            this.stack = new StockStack<>(100); // Keep last 100 price changes
        }
        
        public void recordChange(String symbol, double oldPrice, double newPrice) {
            stack.push(new PriceChange(symbol, oldPrice, newPrice));
        }
        
        public PriceChange getLastChange() {
            return stack.isEmpty() ? null : stack.peek();
        }
        
        public PriceChange undoLastChange() {
            return stack.isEmpty() ? null : stack.pop();
        }
        
        public boolean hasChanges() {
            return !stack.isEmpty();
        }
        
        public int getChangeCount() {
            return stack.size();
        }
        
        // Inner class for price change record
        public static class PriceChange {
            public final String symbol;
            public final double oldPrice;
            public final double newPrice;
            public final long timestamp;
            
            public PriceChange(String symbol, double oldPrice, double newPrice) {
                this.symbol = symbol;
                this.oldPrice = oldPrice;
                this.newPrice = newPrice;
                this.timestamp = System.currentTimeMillis();
            }
            
            public double getChangePercent() {
                if (oldPrice == 0) return 0;
                return ((newPrice - oldPrice) / oldPrice) * 100;
            }
            
            @Override
            public String toString() {
                return String.format("%s: Rs.%.2f -> Rs.%.2f (%.2f%%)", 
                        symbol, oldPrice, newPrice, getChangePercent());
            }
        }
    }
}
