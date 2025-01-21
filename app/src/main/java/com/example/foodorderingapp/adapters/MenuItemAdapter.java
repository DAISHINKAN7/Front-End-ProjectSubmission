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
import com.example.foodorderingapp.models.MenuItem;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {
    private List<MenuItem> menuItems = new ArrayList<>();
    private final OnMenuItemClickListener listener;

    public interface OnMenuItemClickListener {
        void onMenuItemClick(MenuItem menuItem, int quantity);
    }

    public MenuItemAdapter(OnMenuItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);
        return new MenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        holder.bind(menuItems.get(position));
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
        notifyDataSetChanged();
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView foodImageView;
        private final TextView nameTextView;
        private final TextView descriptionTextView;
        private final TextView priceTextView;
        private final TextView quantityTextView;
        private final ImageButton decreaseButton;
        private final ImageButton increaseButton;

        MenuItemViewHolder(View itemView) {
            super(itemView);
            foodImageView = itemView.findViewById(R.id.food_image);
            nameTextView = itemView.findViewById(R.id.food_name);
            descriptionTextView = itemView.findViewById(R.id.food_description);
            priceTextView = itemView.findViewById(R.id.food_price);
            quantityTextView = itemView.findViewById(R.id.quantity_text);
            decreaseButton = itemView.findViewById(R.id.decrease_button);
            increaseButton = itemView.findViewById(R.id.increase_button);

            setupButtons();
        }

        private void setupButtons() {
            decreaseButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MenuItem item = menuItems.get(position);
                    int newQuantity = Math.max(0, item.getQuantity() - 1);
                    item.setQuantity(newQuantity);
                    updateQuantityDisplay(item.getQuantity());
                    listener.onMenuItemClick(item, newQuantity);
                }
            });

            increaseButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MenuItem item = menuItems.get(position);
                    int newQuantity = item.getQuantity() + 1;
                    item.setQuantity(newQuantity);
                    updateQuantityDisplay(newQuantity);
                    listener.onMenuItemClick(item, newQuantity);
                }
            });
        }

        private void updateQuantityDisplay(int quantity) {
            quantityTextView.setText(String.valueOf(quantity));
            decreaseButton.setEnabled(quantity > 0);
        }

        void bind(MenuItem menuItem) {
            nameTextView.setText(menuItem.getName());
            descriptionTextView.setText(menuItem.getDescription());
            priceTextView.setText(String.format("$%.2f", menuItem.getPrice()));
            updateQuantityDisplay(menuItem.getQuantity());

            if (!TextUtils.isEmpty(menuItem.getImageUrl())) {
                Picasso.get()
                        .load(menuItem.getImageUrl())
                        .placeholder(R.drawable.food)
                        .error(R.drawable.food)
                        .into(foodImageView);
            } else {
                foodImageView.setImageResource(R.drawable.food);
            }
        }
    }
}