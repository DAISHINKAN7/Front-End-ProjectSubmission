package com.example.foodorderingapp.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.models.CartItem;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    private List<CartItem> cartItems = new ArrayList<>();
    private final OnCartItemUpdateListener listener;

    public interface OnCartItemUpdateListener {
        void onCartItemUpdate(CartItem cartItem, int quantity);
    }

    public CartItemAdapter(OnCartItemUpdateListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        holder.bind(cartItems.get(position));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems != null ? cartItems : new ArrayList<>();
        notifyDataSetChanged();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView foodImageView;
        private final TextView nameTextView;
        private final TextView priceTextView;
        private final TextView quantityTextView;
        private final TextView totalPriceTextView;
        private final ImageButton decreaseButton;
        private final ImageButton increaseButton;

        CartItemViewHolder(View itemView) {
            super(itemView);
            foodImageView = itemView.findViewById(R.id.cart_item_image);
            nameTextView = itemView.findViewById(R.id.cart_item_name);
            priceTextView = itemView.findViewById(R.id.cart_item_price);
            quantityTextView = itemView.findViewById(R.id.cart_item_quantity);
            totalPriceTextView = itemView.findViewById(R.id.cart_item_total);
            decreaseButton = itemView.findViewById(R.id.cart_decrease_button);
            increaseButton = itemView.findViewById(R.id.cart_increase_button);

            setupButtons();
        }

        private void setupButtons() {
            decreaseButton.setOnClickListener(v -> updateQuantity(-1));
            increaseButton.setOnClickListener(v -> updateQuantity(1));
        }

        private void updateQuantity(int delta) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                CartItem item = cartItems.get(position);
                int newQuantity = Math.max(0, item.getQuantity() + delta);
                listener.onCartItemUpdate(item, newQuantity);
            }
        }

        void bind(CartItem cartItem) {
            nameTextView.setText(cartItem.getMenuItem().getName());
            priceTextView.setText(String.format("$%.2f", cartItem.getMenuItem().getPrice()));
            quantityTextView.setText(String.valueOf(cartItem.getQuantity()));
            totalPriceTextView.setText(String.format("$%.2f", cartItem.getTotalPrice()));

            String imageUrl = cartItem.getMenuItem().getImageUrl();
            if (!TextUtils.isEmpty(imageUrl)) {
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.food)
                        .error(R.drawable.food)
                        .into(foodImageView);
            } else {
                foodImageView.setImageResource(R.drawable.food);
            }

            decreaseButton.setEnabled(cartItem.getQuantity() > 1);
        }
    }
}