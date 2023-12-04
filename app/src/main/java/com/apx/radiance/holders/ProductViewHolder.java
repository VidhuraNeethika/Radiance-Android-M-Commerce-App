package com.apx.radiance.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apx.radiance.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView name, brand, category, price;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productNameText);
            brand = itemView.findViewById(R.id.brandText);
            category = itemView.findViewById(R.id.categoryText);
            price = itemView.findViewById(R.id.priceText);
        }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getBrand() {
        return brand;
    }

    public void setBrand(TextView brand) {
        this.brand = brand;
    }

    public TextView getCategory() {
        return category;
    }

    public void setCategory(TextView category) {
        this.category = category;
    }

    public TextView getPrice() {
        return price;
    }

    public void setPrice(TextView price) {
        this.price = price;
    }
}
