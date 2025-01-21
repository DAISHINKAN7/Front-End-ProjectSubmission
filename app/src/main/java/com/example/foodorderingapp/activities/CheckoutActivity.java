package com.example.foodorderingapp.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.adapters.CartItemAdapter;
import com.example.foodorderingapp.models.CartItem;
import com.example.foodorderingapp.utils.SharedPreferencesManager;
import com.example.foodorderingapp.viewmodels.CheckoutViewModel;
import com.google.android.material.textfield.TextInputLayout;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private Button placeOrderButton;
    private ProgressBar progressBar;
    private RecyclerView orderSummaryRecyclerView;
    private CartItemAdapter adapter;
    private CheckoutViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initializeViews();
        setupRecyclerView();
        setupViewModel();
        loadUserData();
        setupPlaceOrderButton();
    }

    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.checkout);

        nameEditText = findViewById(R.id.name_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        addressEditText = findViewById(R.id.address_edit_text);
        placeOrderButton = findViewById(R.id.place_order_button);
        progressBar = findViewById(R.id.progress_bar);
        orderSummaryRecyclerView = findViewById(R.id.order_summary_recycler_view);
    }

    private void setupRecyclerView() {
        adapter = new CartItemAdapter((cartItem, quantity) -> {
            // Cart items can't be modified in checkout
        });
        orderSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderSummaryRecyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);

        viewModel.getCartItems().observe(this, cartItems -> {
            adapter.setCartItems(cartItems);
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            placeOrderButton.setEnabled(!isLoading);
        });

        viewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadUserData() {
        SharedPreferencesManager prefsManager = SharedPreferencesManager.getInstance(this);
        nameEditText.setText(prefsManager.getUserName());
        emailEditText.setText(prefsManager.getUserEmail());
        addressEditText.setText(prefsManager.getUserAddress());
    }

    private void setupPlaceOrderButton() {
        placeOrderButton.setOnClickListener(v -> {
            if (validateInput()) {
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();

                // Save user data for future use
                SharedPreferencesManager.getInstance(this).saveUserData(name, email, address);

                viewModel.placeOrder(name, email, address);

                // Show success message and finish activity
                Toast.makeText(this, R.string.order_placed, Toast.LENGTH_LONG).show();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private boolean validateInput() {
        boolean isValid = true;

        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        if (name.isEmpty()) {
            nameEditText.setError(getString(R.string.required_field));
            isValid = false;
        }

        if (email.isEmpty()) {
            emailEditText.setError(getString(R.string.required_field));
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError(getString(R.string.invalid_email));
            isValid = false;
        }

        if (address.isEmpty()) {
            addressEditText.setError(getString(R.string.required_field));
            isValid = false;
        }

        return isValid;
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