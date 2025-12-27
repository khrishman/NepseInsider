package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Category Model - Represents a market category
 * Used for organizing stocks into categories
 */
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private String description;
    private String icon;
    private boolean active;
    private LocalDateTime createdDate;
    private List<SubCategory> subCategories;
    
    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = true;
        this.createdDate = LocalDateTime.now();
        this.subCategories = new ArrayList<>();
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    
    public List<SubCategory> getSubCategories() { return subCategories; }
    
    public void addSubCategory(SubCategory subCategory) {
        subCategories.add(subCategory);
    }
    
    public void removeSubCategory(int subCategoryId) {
        subCategories.removeIf(sc -> sc.getId() == subCategoryId);
    }
    
    @Override
    public String toString() {
        return name;
    }
}

/**
 * SubCategory Model - Represents a sub-category within a category
 */
class SubCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private int categoryId;
    private String name;
    private String description;
    private boolean active;
    private LocalDateTime createdDate;
    
    public SubCategory(int id, int categoryId, String name, String description) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.active = true;
        this.createdDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    
    @Override
    public String toString() {
        return name;
    }
}
