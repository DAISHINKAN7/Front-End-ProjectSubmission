package com.example.foodorderingapp.activities;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapters.RestaurantAdapter;
import com.example.foodorderingapp.viewmodels.RestaurantViewModel;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private RestaurantViewModel viewModel;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View progressBar;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRecyclerView();
        setupSearchView();
        setupViewModel();
        setupSwipeRefresh();
    }

    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.restaurant_recycler_view);
        searchView = findViewById(R.id.search_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        progressBar = findViewById(R.id.progress_bar);
        emptyView = findViewById(R.id.empty_view);
    }

    private void setupRecyclerView() {
        adapter = new RestaurantAdapter(restaurant -> {
            // Start RestaurantMenuActivity when a restaurant is clicked
            startActivity(RestaurantMenuActivity.newIntent(this, restaurant));
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchView() {
        searchView.setQueryHint(getString(R.string.search_restaurants));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);

        viewModel.getRestaurants().observe(this, restaurants -> {
            adapter.setRestaurants(restaurants);
            emptyView.setVisibility(restaurants.isEmpty() ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(restaurants.isEmpty() ? View.GONE : View.VISIBLE);
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            swipeRefreshLayout.setRefreshing(isLoading);
        });

        viewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                // Show error message to user
            }
        });
    }

    private void setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refreshRestaurants();
        });
    }
}