package com.example.foodorderingapp.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.foodorderingapp.models.CartItem;
import com.example.foodorderingapp.models.Order;
import com.example.foodorderingapp.utils.SharedPreferencesManager;
import java.util.List;

public class CheckoutViewModel extends AndroidViewModel {
    private final SharedPreferencesManager preferencesManager;
    private final MutableLiveData<List<CartItem>> cartItems;
    private final MutableLiveData<Double> totalAmount;
    private final MutableLiveData<Boolean> isLoading;
    private final MutableLiveData<String> error;

    public CheckoutViewModel(Application application) {
        super(application);
        preferencesManager = SharedPreferencesManager.getInstance(application);
        cartItems = new MutableLiveData<>();
        totalAmount = new MutableLiveData<>();
        isLoading = new MutableLiveData<>(false);
        error = new MutableLiveData<>();
        loadCartItems();
    }

    private void loadCartItems() {
        List<CartItem> items = preferencesManager.getCartItems();
        cartItems.setValue(items);
        calculateTotal(items);
    }

    private void calculateTotal(List<CartItem> items) {
        double total = 0;
        if (items != null) {
            for (CartItem item : items) {
                total += item.getTotalPrice();
            }
        }
        totalAmount.setValue(total);
    }

    public LiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }

    public LiveData<Double> getTotalAmount() {
        return totalAmount;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void placeOrder(String name, String email, String address) {
        List<CartItem> currentItems = cartItems.getValue();
        if (currentItems == null || currentItems.isEmpty()) {
            error.setValue("Cart is empty");
            return;
        }

        isLoading.setValue(true);
        Order newOrder = new Order(name, email, address, currentItems);
        // Add any additional order processing logic here

        // Clear cart after successful order
        preferencesManager.clearCart();
        cartItems.setValue(null);
        totalAmount.setValue(0.0);
        isLoading.setValue(false);
    }
}