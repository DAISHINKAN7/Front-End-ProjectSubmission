package com.example.foodorderingapp.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderingapp.R;
import com.example.foodorderingapp.models.Restaurant;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private List<Restaurant> restaurants = new ArrayList<>();
    private List<Restaurant> filteredList = new ArrayList<>();
    private final OnRestaurantClickListener listener;

    public interface OnRestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant);
    }

    public RestaurantAdapter(OnRestaurantClickListener listener) {
        this.listener = listener;
        this.filteredList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.bind(filteredList.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        this.filteredList.clear();
        this.filteredList.addAll(restaurants);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(restaurants);
        } else {
            String searchText = text.toLowerCase().trim();
            for (Restaurant restaurant : restaurants) {
                if (restaurant.getName().toLowerCase().contains(searchText) ||
                        restaurant.getCuisineType().toLowerCase().contains(searchText)) {
                    filteredList.add(restaurant);
                }
            }
        }
        notifyDataSetChanged();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView nameTextView;
        private final TextView cuisineTextView;

        RestaurantViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restaurant_image);
            nameTextView = itemView.findViewById(R.id.restaurant_name);
            cuisineTextView = itemView.findViewById(R.id.restaurant_cuisine);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onRestaurantClick(filteredList.get(position));
                }
            });
        }

        void bind(Restaurant restaurant) {
            nameTextView.setText(restaurant.getName());
            cuisineTextView.setText(restaurant.getCuisineType());

            if (!TextUtils.isEmpty(restaurant.getImageUrl())) {
                Picasso.get()
                        .load(restaurant.getImageUrl())
                        .placeholder(R.drawable.restaurant)
                        .error(R.drawable.restaurant)
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.restaurant);
            }
        }
    }
}