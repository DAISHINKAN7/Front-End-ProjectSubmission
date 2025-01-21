package com.example.foodorderingapp.models;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String id;
    private String name;
    private String email;
    private String address;
    private List<CartItem> items;
    private double totalAmount;
    private String orderStatus;
    private long orderTime;

    public Order(String name, String email, String address, List<CartItem> items) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.items = items;
        this.orderTime = System.currentTimeMillis();
        this.orderStatus = "PENDING";
        calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        this.totalAmount = 0;
        if (items != null) {
            for (CartItem item : items) {
                this.totalAmount += item.getTotalPrice();
            }
        }
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public List<CartItem> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public String getOrderStatus() { return orderStatus; }
    public long getOrderTime() { return orderTime; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setItems(List<CartItem> items) {
        this.items = items;
        calculateTotalAmount();
    }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    public void setOrderTime(long orderTime) { this.orderTime = orderTime; }
}