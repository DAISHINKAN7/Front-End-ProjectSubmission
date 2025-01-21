package com.example.foodorderingapp.models;

import java.io.Serializable;
import java.util.List;

public class MenuItem implements Serializable {
    private String id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String category;
    private boolean isVegetarian;
    private boolean isSpicy;
    private List<String> allergens;
    private int quantity;
    private boolean isAvailable;
    private List<String> customizationOptions;

    // Constructor with essential fields
    public MenuItem(String id, String name, String description, double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = 0;
        this.isAvailable = true;
    }

    // Full constructor
    public MenuItem(String id, String name, String description, double price,
                    String imageUrl, String category, boolean isVegetarian,
                    boolean isSpicy, List<String> allergens, boolean isAvailable,
                    List<String> customizationOptions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.isVegetarian = isVegetarian;
        this.isSpicy = isSpicy;
        this.allergens = allergens;
        this.quantity = 0;
        this.isAvailable = isAvailable;
        this.customizationOptions = customizationOptions;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getCategory() { return category; }
    public boolean isVegetarian() { return isVegetarian; }
    public boolean isSpicy() { return isSpicy; }
    public List<String> getAllergens() { return allergens; }
    public int getQuantity() { return quantity; }
    public boolean isAvailable() { return isAvailable; }
    public List<String> getCustomizationOptions() { return customizationOptions; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setCategory(String category) { this.category = category; }
    public void setVegetarian(boolean vegetarian) { isVegetarian = vegetarian; }
    public void setSpicy(boolean spicy) { isSpicy = spicy; }
    public void setAllergens(List<String> allergens) { this.allergens = allergens; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setCustomizationOptions(List<String> customizationOptions) {
        this.customizationOptions = customizationOptions;
    }
}