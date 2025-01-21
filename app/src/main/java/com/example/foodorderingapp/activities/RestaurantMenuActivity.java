package com.example.foodorderingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapters.MenuItemAdapter;
import com.example.foodorderingapp.models.Restaurant;
import com.example.foodorderingapp.viewmodels.MenuViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RestaurantMenuActivity extends AppCompatActivity {
    private static final String EXTRA_RESTAURANT_ID = "extra_restaurant_id";
    private static final String EXTRA_RESTAURANT_NAME = "extra_restaurant_name";

    private RecyclerView recyclerView;
    private MenuItemAdapter adapter;
    private MenuViewModel viewModel;
    private FloatingActionButton fabCart;
    private View emptyView;
    private View progressBar;

    public static Intent newIntent(Context context, Restaurant restaurant) {
        Intent intent = new Intent(context, RestaurantMenuActivity.class);
        intent.putExtra(EXTRA_RESTAURANT_ID, restaurant.getId());
        intent.putExtra(EXTRA_RESTAURANT_NAME, restaurant.getName());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        String restaurantId = getIntent().getStringExtra(EXTRA_RESTAURANT_ID);
        String restaurantName = getIntent().getStringExtra(EXTRA_RESTAURANT_NAME);

        if (restaurantId == null) {
            finish();
            return;
        }

        initializeViews(restaurantName);
        setupRecyclerView();
        setupViewModel(restaurantId);
        setupCartButton();
    }

    private void initializeViews(String restaurantName) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(restaurantName);

        recyclerView = findViewById(R.id.menu_recycler_view);
        fabCart = findViewById(R.id.fab_cart);
        emptyView = findViewById(R.id.empty_view);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void setupRecyclerView() {
        adapter = new MenuItemAdapter((menuItem, quantity) -> {
            viewModel.updateCartItem(menuItem, quantity);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupViewModel(String restaurantId) {
        viewModel = new ViewModelProvider(this).get(MenuViewModel.class);

        viewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getMenuItems().observe(this, menuItems -> {
            adapter.setMenuItems(menuItems);
            emptyView.setVisibility(menuItems.isEmpty() ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(menuItems.isEmpty() ? View.GONE : View.VISIBLE);
        });

        viewModel.loadMenuItems(restaurantId);
    }

    private void setupCartButton() {
        fabCart.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}