package com.example.foodorderingapp.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.foodorderingapp.models.CartItem;
import com.example.foodorderingapp.models.MenuItem;
import com.example.foodorderingapp.models.Order;
import com.example.foodorderingapp.utils.ApiClient;
import com.example.foodorderingapp.utils.SharedPreferencesManager;
import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private final MutableLiveData<List<CartItem>> cartItems = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Double> totalPrice = new MutableLiveData<>(0.0);
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final SharedPreferencesManager preferencesManager;

    public CartViewModel(Application application) {
        super(application);
        preferencesManager = SharedPreferencesManager.getInstance(application);
        loadCartItems();
    }

    private void loadCartItems() {
        List<CartItem> items = preferencesManager.getCartItems();
        cartItems.setValue(items);
        calculateTotal();
    }

    public LiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }

    public LiveData<Double> getTotalPrice() {
        return totalPrice;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void addToCart(MenuItem menuItem, int quantity) {
        List<CartItem> currentItems = cartItems.getValue();
        if (currentItems == null) {
            currentItems = new ArrayList<>();
        }

        boolean itemExists = false;
        if (quantity > 0) {
            for (CartItem item : currentItems) {
                if (item.getMenuItem().getId().equals(menuItem.getId())) {
                    item.setQuantity(quantity);
                    itemExists = true;
                    break;
                }
            }

            if (!itemExists) {
                currentItems.add(new CartItem(menuItem, quantity));
            }
        }

        cartItems.setValue(currentItems);
        preferencesManager.saveCartItems(currentItems);
        calculateTotal();
    }

    public void updateCartItemQuantity(CartItem cartItem, int newQuantity) {
        List<CartItem> currentItems = cartItems.getValue();
        if (currentItems == null) {
            return;
        }

        if (newQuantity <= 0) {
            currentItems.remove(cartItem);
        } else {
            for (CartItem item : currentItems) {
                if (item.getMenuItem().getId().equals(cartItem.getMenuItem().getId())) {
                    item.setQuantity(newQuantity);
                    break;
                }
            }
        }

        cartItems.setValue(currentItems);
        preferencesManager.saveCartItems(currentItems);
        calculateTotal();
    }

    public void removeFromCart(CartItem cartItem) {
        List<CartItem> currentItems = cartItems.getValue();
        if (currentItems != null) {
            currentItems.remove(cartItem);
            cartItems.setValue(currentItems);
            preferencesManager.saveCartItems(currentItems);
            calculateTotal();
        }
    }

    private void calculateTotal() {
        List<CartItem> items = cartItems.getValue();
        double total = 0;
        if (items != null) {
            for (CartItem item : items) {
                total += item.getTotalPrice();
            }
        }
        totalPrice.setValue(total);
    }

    public void clearCart() {
        cartItems.setValue(new ArrayList<>());
        preferencesManager.clearCart();
        calculateTotal();
    }

    public void refreshCart() {
        loadCartItems();
    }
}