package com.example.foodorderingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapters.CartItemAdapter;
import com.example.foodorderingapp.models.CartItem;
import com.example.foodorderingapp.viewmodels.CartViewModel;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartItemAdapter adapter;
    private CartViewModel viewModel;
    private TextView totalPriceTextView;
    private Button checkoutButton;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initializeViews();
        setupRecyclerView();
        setupViewModel();
        setupCheckoutButton();
    }

    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.cart);

        recyclerView = findViewById(R.id.cart_recycler_view);
        totalPriceTextView = findViewById(R.id.total_price_text_view);
        checkoutButton = findViewById(R.id.checkout_button);
        emptyView = findViewById(R.id.empty_cart_text);
    }

    private void setupRecyclerView() {
        adapter = new CartItemAdapter((cartItem, quantity) -> {
            if (quantity == 0) {
                viewModel.removeFromCart(cartItem);
            } else {
                viewModel.updateCartItemQuantity(cartItem, quantity);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        viewModel.getCartItems().observe(this, cartItems -> {
            adapter.setCartItems(cartItems);
            emptyView.setVisibility(cartItems.isEmpty() ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(cartItems.isEmpty() ? View.GONE : View.VISIBLE);
            updateTotalPrice();
        });
    }

    private void setupCheckoutButton() {
        checkoutButton.setOnClickListener(v -> {
            if (viewModel.getCartItems().getValue() != null && !viewModel.getCartItems().getValue().isEmpty()) {
                startActivity(new Intent(this, CheckoutActivity.class));
            }
        });
    }

    private void updateTotalPrice() {
        double total = 0;
        if (viewModel.getCartItems().getValue() != null) {
            for (CartItem item : viewModel.getCartItems().getValue()) {
                total += item.getTotalPrice();
            }
        }
        totalPriceTextView.setText(getString(R.string.total, String.format("$%.2f", total)));
        checkoutButton.setEnabled(total > 0);
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