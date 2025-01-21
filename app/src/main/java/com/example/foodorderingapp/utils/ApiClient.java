package com.example.foodorderingapp.utils;

import com.example.foodorderingapp.models.Restaurant;
import com.example.foodorderingapp.models.MenuItem;
import com.example.foodorderingapp.models.Order;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.os.Looper;

public class ApiClient {
    // Mock data for demonstration
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface ApiCallback<T> {
        void onSuccess(T result);
        void onError(Exception e);
    }

    public void getRestaurants(ApiCallback<List<Restaurant>> callback) {
        // Simulate network delay
        mainHandler.postDelayed(() -> {
            try {
                List<Restaurant> restaurants = new ArrayList<>();
                restaurants.add(new Restaurant("1", "Italian Delight", "https://example.com/italian.jpg", "Italian"));
                restaurants.add(new Restaurant("2", "Sushi Express", "https://example.com/sushi.jpg", "Japanese"));
                restaurants.add(new Restaurant("3", "Taco Fiesta", "https://example.com/taco.jpg", "Mexican"));
                restaurants.add(new Restaurant("4", "Burger House", "https://example.com/burger.jpg", "American"));
                callback.onSuccess(restaurants);
            } catch (Exception e) {
                callback.onError(e);
            }
        }, 1000);
    }

    public void getMenuItems(String restaurantId, ApiCallback<List<MenuItem>> callback) {
        mainHandler.postDelayed(() -> {
            try {
                List<MenuItem> menuItems = new ArrayList<>();
                switch (restaurantId) {
                    case "1": // Italian restaurant
                        menuItems.add(new MenuItem("1", "Margherita Pizza", "Fresh tomatoes, mozzarella, basil", 12.99, ""));
                        menuItems.add(new MenuItem("2", "Pasta Carbonara", "Creamy sauce with bacon", 10.99, ""));
                        break;
                    case "2": // Japanese restaurant
                        menuItems.add(new MenuItem("3", "California Roll", "Crab, avocado, cucumber", 8.99, ""));
                        menuItems.add(new MenuItem("4", "Salmon Nigiri", "Fresh salmon sushi", 9.99, ""));
                        break;
                    default:
                        menuItems.add(new MenuItem("5", "Sample Dish", "Description", 9.99, ""));
                }
                callback.onSuccess(menuItems);
            } catch (Exception e) {
                callback.onError(e);
            }
        }, 800);
    }

    public void placeOrder(Order order, ApiCallback<Boolean> callback) {
        mainHandler.postDelayed(() -> {
            try {
                // Simulate order processing
                callback.onSuccess(true);
            } catch (Exception e) {
                callback.onError(e);
            }
        }, 1500);
    }
}