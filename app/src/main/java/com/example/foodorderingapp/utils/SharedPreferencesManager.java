package com.example.foodorderingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.foodorderingapp.models.CartItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesManager {
    private static SharedPreferencesManager instance;
    private final SharedPreferences prefs;
    private final Gson gson;

    public SharedPreferencesManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(
                Constants.PREF_NAME,
                Context.MODE_PRIVATE
        );
        gson = new Gson();
    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
        return instance;
    }

    // User Data Methods
    public void saveUserData(String name, String email, String address) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.KEY_USER_NAME, name);
        editor.putString(Constants.KEY_USER_EMAIL, email);
        editor.putString(Constants.KEY_USER_ADDRESS, address);
        editor.apply();
    }

    public String getUserName() {
        return prefs.getString(Constants.KEY_USER_NAME, "");
    }

    public String getUserEmail() {
        return prefs.getString(Constants.KEY_USER_EMAIL, "");
    }

    public String getUserAddress() {
        return prefs.getString(Constants.KEY_USER_ADDRESS, "");
    }

    // Cart Methods
    public void saveCartItems(List<CartItem> cartItems) {
        SharedPreferences.Editor editor = prefs.edit();
        String json = gson.toJson(cartItems);
        editor.putString(Constants.KEY_CART_ITEMS, json);
        editor.apply();
    }

    public List<CartItem> getCartItems() {
        String json = prefs.getString(Constants.KEY_CART_ITEMS, null);
        if (json != null) {
            Type type = new TypeToken<List<CartItem>>(){}.getType();
            return gson.fromJson(json, type);
        }
        return new ArrayList<>();
    }

    public void clearCart() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Constants.KEY_CART_ITEMS);
        editor.apply();
    }

    // Clear all data
    public void clearAll() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    // Add item to cart
    public void addToCart(CartItem cartItem) {
        List<CartItem> currentItems = getCartItems();
        boolean itemExists = false;

        for (CartItem item : currentItems) {
            if (item.getMenuItem().getId().equals(cartItem.getMenuItem().getId())) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            currentItems.add(cartItem);
        }

        saveCartItems(currentItems);
    }

    // Update cart item quantity
    public void updateCartItemQuantity(CartItem cartItem, int newQuantity) {
        List<CartItem> currentItems = getCartItems();

        for (CartItem item : currentItems) {
            if (item.getMenuItem().getId().equals(cartItem.getMenuItem().getId())) {
                item.setQuantity(newQuantity);
                break;
            }
        }

        saveCartItems(currentItems);
    }

    // Remove item from cart
    public void removeFromCart(CartItem cartItem) {
        List<CartItem> currentItems = getCartItems();
        currentItems.removeIf(item ->
                item.getMenuItem().getId().equals(cartItem.getMenuItem().getId())
        );
        saveCartItems(currentItems);
    }
}