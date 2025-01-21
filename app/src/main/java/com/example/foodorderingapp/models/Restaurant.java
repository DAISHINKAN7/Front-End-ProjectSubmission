package com.example.foodorderingapp.models;

import java.io.Serializable;
import java.util.List;

public class Restaurant implements Serializable {
    private String id;
    private String name;
    private String imageUrl;
    private String cuisineType;
    private String address;
    private double rating;
    private List<String> openingHours;
    private boolean isOpen;
    private double deliveryFee;
    private int estimatedDeliveryTime; // in minutes

    // Constructor with essential fields
    public Restaurant(String id, String name, String imageUrl, String cuisineType) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.cuisineType = cuisineType;
    }

    // Full constructor
    public Restaurant(String id, String name, String imageUrl, String cuisineType,
                      String address, double rating, List<String> openingHours,
                      boolean isOpen, double deliveryFee, int estimatedDeliveryTime) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.cuisineType = cuisineType;
        this.address = address;
        this.rating = rating;
        this.openingHours = openingHours;
        this.isOpen = isOpen;
        this.deliveryFee = deliveryFee;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public String getCuisineType() { return cuisineType; }
    public String getAddress() { return address; }
    public double getRating() { return rating; }
    public List<String> getOpeningHours() { return openingHours; }
    public boolean isOpen() { return isOpen; }
    public double getDeliveryFee() { return deliveryFee; }
    public int getEstimatedDeliveryTime() { return estimatedDeliveryTime; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setCuisineType(String cuisineType) { this.cuisineType = cuisineType; }
    public void setAddress(String address) { this.address = address; }
    public void setRating(double rating) { this.rating = rating; }
    public void setOpeningHours(List<String> openingHours) { this.openingHours = openingHours; }
    public void setOpen(boolean open) { isOpen = open; }
    public void setDeliveryFee(double deliveryFee) { this.deliveryFee = deliveryFee; }
    public void setEstimatedDeliveryTime(int estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }
}