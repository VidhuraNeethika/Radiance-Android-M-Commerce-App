package com.apx.radiance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apx.radiance.R;
import com.apx.radiance.model.ProductItem;

import java.util.ArrayList;

public class WishlistProductAdapter extends RecyclerView.Adapter<WishlistProductAdapter.ViewHolder> {

    private ArrayList<ProductItem> productItemsList;

    public WishlistProductAdapter(ArrayList<ProductItem> productsList) {
        this.productItemsList = productsList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name, brand, category, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productNameText);
            brand = itemView.findViewById(R.id.brandText);
            category = itemView.findViewById(R.id.categoryText);
            price = itemView.findViewById(R.id.priceText);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_card_detailed_wishlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItem currentItem = productItemsList.get(position);
        holder.imageView.setImageResource(currentItem.getImageSource());
        holder.name.setText(currentItem.getName());
        holder.brand.setText(currentItem.getBrand());
        holder.category.setText(currentItem.getCategory());
        holder.price.setText(currentItem.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return productItemsList.size();
    }
}
