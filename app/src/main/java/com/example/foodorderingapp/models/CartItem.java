package com.example.foodorderingapp.models;

public class CartItem {
    private MenuItem menuItem;
    private int quantity;

    public CartItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    // Getters
    public MenuItem getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }

    // Setters
    public void setMenuItem(MenuItem menuItem) { this.menuItem = menuItem; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Helper methods
    public double getTotalPrice() {
        return menuItem.getPrice() * quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }
}