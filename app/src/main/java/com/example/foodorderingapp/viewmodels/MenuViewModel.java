package com.example.foodorderingapp.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.foodorderingapp.models.MenuItem;
import com.example.foodorderingapp.models.CartItem;
import com.example.foodorderingapp.utils.ApiClient;
import com.example.foodorderingapp.utils.SharedPreferencesManager;
import java.util.List;

public class MenuViewModel extends AndroidViewModel {
    private final MutableLiveData<List<MenuItem>> menuItems = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final ApiClient apiClient;
    private final SharedPreferencesManager preferencesManager;
    private String currentRestaurantId;

    public MenuViewModel(Application application) {
        super(application);
        apiClient = new ApiClient();
        preferencesManager = SharedPreferencesManager.getInstance(application);
    }

    public LiveData<List<MenuItem>> getMenuItems() {
        return menuItems;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadMenuItems(String restaurantId) {
        if (restaurantId.equals(currentRestaurantId) && menuItems.getValue() != null) {
            return;
        }

        currentRestaurantId = restaurantId;
        isLoading.setValue(true);
        error.setValue(null);

        apiClient.getMenuItems(restaurantId, new ApiClient.ApiCallback<List<MenuItem>>() {
            @Override
            public void onSuccess(List<MenuItem> result) {
                menuItems.postValue(result);
                isLoading.postValue(false);
            }

            @Override
            public void onError(Exception e) {
                error.postValue(e.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    public void updateCartItem(MenuItem menuItem, int quantity) {
        if (quantity == 0) {
            preferencesManager.removeFromCart(new CartItem(menuItem, quantity));
        } else {
            preferencesManager.addToCart(new CartItem(menuItem, quantity));
        }
    }

    public void refreshMenu() {
        if (currentRestaurantId != null) {
            loadMenuItems(currentRestaurantId);
        }
    }
}