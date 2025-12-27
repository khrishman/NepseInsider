package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * SystemSettings Model - Manages all system configuration settings
 */
public class SystemSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static SystemSettings instance;
    
    // General Settings
    private String siteName;
    private String siteTitle;
    private String tagline;
    private String timezone;
    private String currency;
    private String currencySymbol;
    
    // Logo and Favicon
    private String logoPath;
    private String faviconPath;
    private String footerLogoPath;
    
    // Contact Information
    private String contactEmail;
    private String contactPhone;
    private String address;
    
    // Social Media Links
    private String facebookUrl;
    private String twitterUrl;
    private String linkedinUrl;
    private String youtubeUrl;
    
    // System Configuration
    private boolean registrationEnabled;
    private boolean emailVerificationRequired;
    private boolean maintenanceMode;
    private String maintenanceMessage;
    
    // Notification Settings
    private boolean emailNotificationsEnabled;
    private boolean smsNotificationsEnabled;
    private boolean pushNotificationsEnabled;
    
    // Trading Settings
    private double minimumDeposit;
    private double minimumWithdrawal;
    private double depositFeePercent;
    private double withdrawalFeePercent;
    
    // Custom Settings Map for flexibility
    private Map<String, String> customSettings;
    
    private LocalDateTime lastUpdated;
    
    private SystemSettings() {
        initializeDefaults();
    }
    
    public static SystemSettings getInstance() {
        if (instance == null) {
            instance = new SystemSettings();
        }
        return instance;
    }
    
    private void initializeDefaults() {
        this.siteName = "NepseInsider";
        this.siteTitle = "NepseInsider - Your Stock Market Companion";
        this.tagline = "Trade Smart, Invest Wisely";
        this.timezone = "Asia/Kathmandu";
        this.currency = "NPR";
        this.currencySymbol = "Rs.";
        
        this.registrationEnabled = true;
        this.emailVerificationRequired = false;
        this.maintenanceMode = false;
        
        this.emailNotificationsEnabled = true;
        this.smsNotificationsEnabled = false;
        this.pushNotificationsEnabled = true;
        
        this.minimumDeposit = 100.0;
        this.minimumWithdrawal = 500.0;
        this.depositFeePercent = 0.0;
        this.withdrawalFeePercent = 1.0;
        
        this.customSettings = new HashMap<>();
        this.lastUpdated = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { 
        this.siteName = siteName; 
        this.lastUpdated = LocalDateTime.now();
    }
    
    public String getSiteTitle() { return siteTitle; }
    public void setSiteTitle(String siteTitle) { 
        this.siteTitle = siteTitle;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public String getTagline() { return tagline; }
    public void setTagline(String tagline) { 
        this.tagline = tagline;
        this.lastUpdated = LocalDateTime.now();
    }
    
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getCurrencySymbol() { return currencySymbol; }
    public void setCurrencySymbol(String currencySymbol) { this.currencySymbol = currencySymbol; }
    
    public String getLogoPath() { return logoPath; }
    public void setLogoPath(String logoPath) { this.logoPath = logoPath; }
    
    public String getFaviconPath() { return faviconPath; }
    public void setFaviconPath(String faviconPath) { this.faviconPath = faviconPath; }
    
    public String getFooterLogoPath() { return footerLogoPath; }
    public void setFooterLogoPath(String footerLogoPath) { this.footerLogoPath = footerLogoPath; }
    
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getFacebookUrl() { return facebookUrl; }
    public void setFacebookUrl(String facebookUrl) { this.facebookUrl = facebookUrl; }
    
    public String getTwitterUrl() { return twitterUrl; }
    public void setTwitterUrl(String twitterUrl) { this.twitterUrl = twitterUrl; }
    
    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
    
    public String getYoutubeUrl() { return youtubeUrl; }
    public void setYoutubeUrl(String youtubeUrl) { this.youtubeUrl = youtubeUrl; }
    
    public boolean isRegistrationEnabled() { return registrationEnabled; }
    public void setRegistrationEnabled(boolean registrationEnabled) { 
        this.registrationEnabled = registrationEnabled; 
    }
    
    public boolean isEmailVerificationRequired() { return emailVerificationRequired; }
    public void setEmailVerificationRequired(boolean emailVerificationRequired) { 
        this.emailVerificationRequired = emailVerificationRequired; 
    }
    
    public boolean isMaintenanceMode() { return maintenanceMode; }
    public void setMaintenanceMode(boolean maintenanceMode) { 
        this.maintenanceMode = maintenanceMode; 
    }
    
    public String getMaintenanceMessage() { return maintenanceMessage; }
    public void setMaintenanceMessage(String maintenanceMessage) { 
        this.maintenanceMessage = maintenanceMessage; 
    }
    
    public boolean isEmailNotificationsEnabled() { return emailNotificationsEnabled; }
    public void setEmailNotificationsEnabled(boolean emailNotificationsEnabled) { 
        this.emailNotificationsEnabled = emailNotificationsEnabled; 
    }
    
    public boolean isSmsNotificationsEnabled() { return smsNotificationsEnabled; }
    public void setSmsNotificationsEnabled(boolean smsNotificationsEnabled) { 
        this.smsNotificationsEnabled = smsNotificationsEnabled; 
    }
    
    public boolean isPushNotificationsEnabled() { return pushNotificationsEnabled; }
    public void setPushNotificationsEnabled(boolean pushNotificationsEnabled) { 
        this.pushNotificationsEnabled = pushNotificationsEnabled; 
    }
    
    public double getMinimumDeposit() { return minimumDeposit; }
    public void setMinimumDeposit(double minimumDeposit) { this.minimumDeposit = minimumDeposit; }
    
    public double getMinimumWithdrawal() { return minimumWithdrawal; }
    public void setMinimumWithdrawal(double minimumWithdrawal) { this.minimumWithdrawal = minimumWithdrawal; }
    
    public double getDepositFeePercent() { return depositFeePercent; }
    public void setDepositFeePercent(double depositFeePercent) { this.depositFeePercent = depositFeePercent; }
    
    public double getWithdrawalFeePercent() { return withdrawalFeePercent; }
    public void setWithdrawalFeePercent(double withdrawalFeePercent) { this.withdrawalFeePercent = withdrawalFeePercent; }
    
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    
    // Custom settings methods
    public void setCustomSetting(String key, String value) {
        customSettings.put(key, value);
        this.lastUpdated = LocalDateTime.now();
    }
    
    public String getCustomSetting(String key) {
        return customSettings.get(key);
    }
    
    public String getCustomSetting(String key, String defaultValue) {
        return customSettings.getOrDefault(key, defaultValue);
    }
    
    public void removeCustomSetting(String key) {
        customSettings.remove(key);
    }
    
    public Map<String, String> getAllCustomSettings() {
        return new HashMap<>(customSettings);
    }
}
