package com.apx.radiance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apx.radiance.R;
import com.apx.radiance.holders.ProductViewHolder;
import com.apx.radiance.model.ProductItem;

import java.util.ArrayList;

public class GridProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private ArrayList<ProductItem> productItemsList;

    public GridProductAdapter(ArrayList<ProductItem> productsList) {
        this.productItemsList = productsList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout_card, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
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
