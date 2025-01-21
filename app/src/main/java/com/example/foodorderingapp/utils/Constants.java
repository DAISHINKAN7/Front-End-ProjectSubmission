package com.example.foodorderingapp.utils;

public class Constants {
    // API Related Constants
    public static final String BASE_URL = "https://api.foodorderingapp.com/";
    public static final int API_TIMEOUT = 30; // seconds

    // SharedPreferences Keys
    public static final String PREF_NAME = "FoodOrderingPrefs";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_EMAIL = "user_email";
    public static final String KEY_USER_ADDRESS = "user_address";
    public static final String KEY_CART_ITEMS = "cart_items";

    // Intent Extra Keys
    public static final String EXTRA_RESTAURANT_ID = "restaurant_id";
    public static final String EXTRA_RESTAURANT_NAME = "restaurant_name";
    public static final String EXTRA_MENU_ITEM = "menu_item";

    // Request Codes
    public static final int REQUEST_CART = 100;
    public static final int REQUEST_CHECKOUT = 101;

    // Error Messages
    public static final String ERROR_NETWORK = "Network error occurred";
    public static final String ERROR_INVALID_RESPONSE = "Invalid response from server";
    public static final String ERROR_GENERAL = "Something went wrong";

    // Order Status
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_PREPARING = "PREPARING";
    public static final String STATUS_READY = "READY";
    public static final String STATUS_DELIVERED = "DELIVERED";

    // UI Related Constants
    public static final int SEARCH_DELAY = 300; // milliseconds
    public static final int MAX_QUANTITY = 99;
    public static final int DEFAULT_ANIMATION_DURATION = 300;
}