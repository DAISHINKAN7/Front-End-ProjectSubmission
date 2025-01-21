package com.example.foodorderingapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.foodorderingapp.models.Restaurant;
import com.example.foodorderingapp.utils.ApiClient;
import java.util.List;

public class RestaurantViewModel extends ViewModel {
    private final MutableLiveData<List<Restaurant>> restaurants = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final ApiClient apiClient;

    public RestaurantViewModel() {
        apiClient = new ApiClient();
        loadRestaurants();
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return restaurants;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadRestaurants() {
        isLoading.setValue(true);
        error.setValue(null);

        apiClient.getRestaurants(new ApiClient.ApiCallback<List<Restaurant>>() {
            @Override
            public void onSuccess(List<Restaurant> result) {
                restaurants.postValue(result);
                isLoading.postValue(false);
            }

            @Override
            public void onError(Exception e) {
                error.postValue(e.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    public void refreshRestaurants() {
        loadRestaurants();
    }
}