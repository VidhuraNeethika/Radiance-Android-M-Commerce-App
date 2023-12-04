package com.apx.radiance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apx.radiance.R;
import com.apx.radiance.holders.ProductViewHolder;
import com.apx.radiance.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MtLProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private ArrayList<Product> productItemsList;

    public MtLProductAdapter(ArrayList<Product> productsList) {
        this.productItemsList = productsList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_card, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentItem = productItemsList.get(position);
        Picasso.get().load(currentItem.getImageList().get(0)).into(holder.imageView);
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
