package com.apx.radiance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apx.radiance.R;
import com.apx.radiance.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SingleProductViewImageAdapter extends RecyclerView.Adapter<SingleProductViewImageAdapter.ViewHolder>{

    ArrayList<String> productImageList;

    public SingleProductViewImageAdapter(ArrayList<String> productImageList) {
        this.productImageList = productImageList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImageSingleProductView);
        }
    }

    @NonNull
    @Override
    public SingleProductViewImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_product_view_image_slider, parent, false);
        return new SingleProductViewImageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleProductViewImageAdapter.ViewHolder holder, int position) {
        String currentItem = productImageList.get(position);
        Picasso.get().load(currentItem).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productImageList.size();
    }

}
