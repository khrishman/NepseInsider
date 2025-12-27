package util;

import java.util.regex.Pattern;

/**
 * Validation Utilities for NepseInsider
 * Handles all input validation and provides clear error messages
 */
public class ValidationUtils {
    
    // Regular expression patterns
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[0-9]{10}$");
    private static final Pattern SYMBOL_PATTERN = 
        Pattern.compile("^[A-Z]{2,10}$");
    private static final Pattern USERNAME_PATTERN = 
        Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    
    /**
     * Validation result class
     */
    public static class ValidationResult {
        private boolean valid;
        private String errorMessage;
        
        public ValidationResult(boolean valid, String errorMessage) {
            this.valid = valid;
            this.errorMessage = errorMessage;
        }
        
        public boolean isValid() { return valid; }
        public String getErrorMessage() { return errorMessage; }
        
        public static ValidationResult success() {
            return new ValidationResult(true, null);
        }
        
        public static ValidationResult error(String message) {
            return new ValidationResult(false, message);
        }
    }
    
    // ==================== Stock Validation ====================
    
    /**
     * Validate stock symbol
     */
    public static ValidationResult validateStockSymbol(String symbol) {
        if (symbol == null || symbol.trim().isEmpty()) {
            return ValidationResult.error("Stock symbol cannot be empty");
        }
        
        String trimmed = symbol.trim().toUpperCase();
        
        if (trimmed.length() < 2 || trimmed.length() > 10) {
            return ValidationResult.error("Stock symbol must be 2-10 characters");
        }
        
        if (!SYMBOL_PATTERN.matcher(trimmed).matches()) {
            return ValidationResult.error("Stock symbol must contain only uppercase letters");
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate company name
     */
    public static ValidationResult validateCompanyName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return ValidationResult.error("Company name cannot be empty");
        }
        
        if (name.trim().length() < 2) {
            return ValidationResult.error("Company name must be at least 2 characters");
        }
        
        if (name.trim().length() > 100) {
            return ValidationResult.error("Company name cannot exceed 100 characters");
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate stock price
     */
    public static ValidationResult validateStockPrice(String priceStr) {
        if (priceStr == null || priceStr.trim().isEmpty()) {
            return ValidationResult.error("Stock price cannot be empty");
        }
        
        try {
            double price = Double.parseDouble(priceStr.trim());
            
            if (price < 0) {
                return ValidationResult.error("Stock price cannot be negative");
            }
            
            if (price > 100000000) { // 10 crore limit
                return ValidationResult.error("Stock price exceeds maximum allowed value");
            }
            
            return ValidationResult.success();
        } catch (NumberFormatException e) {
            return ValidationResult.error("Stock price must be a valid number");
        }
    }
    
    /**
     * Validate market cap
     */
    public static ValidationResult validateMarketCap(String marketCapStr) {
        if (marketCapStr == null || marketCapStr.trim().isEmpty()) {
            return ValidationResult.error("Market cap cannot be empty");
        }
        
        try {
            double marketCap = Double.parseDouble(marketCapStr.trim());
            
            if (marketCap < 0) {
                return ValidationResult.error("Market cap cannot be negative");
            }
            
            return ValidationResult.success();
        } catch (NumberFormatException e) {
            return ValidationResult.error("Market cap must be a valid number");
        }
    }
    
    /**
     * Validate volume
     */
    public static ValidationResult validateVolume(String volumeStr) {
        if (volumeStr == null || volumeStr.trim().isEmpty()) {
            return ValidationResult.error("Volume cannot be empty");
        }
        
        try {
            long volume = Long.parseLong(volumeStr.trim());
            
            if (volume < 0) {
                return ValidationResult.error("Volume cannot be negative");
            }
            
            return ValidationResult.success();
        } catch (NumberFormatException e) {
            return ValidationResult.error("Volume must be a valid whole number");
        }
    }
    
    /**
     * Validate year listed
     */
    public static ValidationResult validateYearListed(String yearStr) {
        if (yearStr == null || yearStr.trim().isEmpty()) {
            return ValidationResult.error("Year listed cannot be empty");
        }
        
        try {
            int year = Integer.parseInt(yearStr.trim());
            int currentYear = java.time.Year.now().getValue();
            
            if (year < 1937) { // NEPSE established year reference
                return ValidationResult.error("Year must be 1937 or later");
            }
            
            if (year > currentYear) {
                return ValidationResult.error("Year cannot be in the future");
            }
            
            return ValidationResult.success();
        } catch (NumberFormatException e) {
            return ValidationResult.error("Year must be a valid number (e.g., 2020)");
        }
    }
    
    /**
     * Validate sector
     */
    public static ValidationResult validateSector(String sector) {
        if (sector == null || sector.trim().isEmpty()) {
            return ValidationResult.error("Sector cannot be empty");
        }
        
        if (sector.trim().length() < 2) {
            return ValidationResult.error("Sector must be at least 2 characters");
        }
        
        return ValidationResult.success();
    }
    
    // ==================== User Validation ====================
    
    /**
     * Validate username
     */
    public static ValidationResult validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return ValidationResult.error("Username cannot be empty");
        }
        
        String trimmed = username.trim();
        
        if (trimmed.length() < 3) {
            return ValidationResult.error("Username must be at least 3 characters");
        }
        
        if (trimmed.length() > 20) {
            return ValidationResult.error("Username cannot exceed 20 characters");
        }
        
        if (!USERNAME_PATTERN.matcher(trimmed).matches()) {
            return ValidationResult.error("Username can only contain letters, numbers, and underscores");
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate password
     */
    public static ValidationResult validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return ValidationResult.error("Password cannot be empty");
        }
        
        if (password.length() < 4) {
            return ValidationResult.error("Password must be at least 4 characters");
        }
        
        if (password.length() > 50) {
            return ValidationResult.error("Password cannot exceed 50 characters");
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate email
     */
    public static ValidationResult validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return ValidationResult.error("Email cannot be empty");
        }
        
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return ValidationResult.error("Please enter a valid email address");
        }
        
        return ValidationResult.success();
    }
    
    /**
     * Validate phone number
     */
    public static ValidationResult validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return ValidationResult.success(); // Phone is optional
        }
        
        String digitsOnly = phone.replaceAll("[^0-9]", "");
        
        if (digitsOnly.length() != 10) {
            return ValidationResult.error("Phone number must be 10 digits");
        }
        
        return ValidationResult.success();
    }
    
    // ==================== Transaction Validation ====================
    
    /**
     * Validate amount
     */
    public static ValidationResult validateAmount(String amountStr, double minimum) {
        if (amountStr == null || amountStr.trim().isEmpty()) {
            return ValidationResult.error("Amount cannot be empty");
        }
        
        try {
            double amount = Double.parseDouble(amountStr.trim());
            
            if (amount < 0) {
                return ValidationResult.error("Amount cannot be negative");
            }
            
            if (amount < minimum) {
                return ValidationResult.error(String.format("Minimum amount is Rs. %.2f", minimum));
            }
            
            return ValidationResult.success();
        } catch (NumberFormatException e) {
            return ValidationResult.error("Amount must be a valid number");
        }
    }
    
    // ==================== General Utilities ====================
    
    /**
     * Check if string is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Safe parse integer with default value
     */
    public static int safeParseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * Safe parse double with default value
     */
    public static double safeParseDouble(String str, double defaultValue) {
        try {
            return Double.parseDouble(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * Safe parse long with default value
     */
    public static long safeParseLong(String str, long defaultValue) {
        try {
            return Long.parseLong(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
