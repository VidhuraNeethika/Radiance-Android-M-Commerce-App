package com.apx.radiance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.apx.radiance.R;
import com.apx.radiance.SingleProductViewFragment;
import com.apx.radiance.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.ProductViewHolder> {

    private ArrayList<Product> productItemsList;
    private Fragment transactionFragment;

    public MyProductAdapter(ArrayList<Product> productsList,Fragment fragment) {
        this.productItemsList = productsList;
        this.transactionFragment = fragment;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

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
    }

    @NonNull
    @Override
    public MyProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_product_card, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyProductAdapter.ProductViewHolder holder, int position) {
        Product currentItem = productItemsList.get(position);
        Picasso.get().load(currentItem.getImageList().get(0)).into(holder.imageView);
        holder.name.setText(currentItem.getName());
        holder.brand.setText(currentItem.getBrand());
        holder.category.setText(currentItem.getCategory());
        holder.price.setText("Rs."+currentItem.getPrice().toString()+"0");

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new SingleProductViewFragment(currentItem));
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new SingleProductViewFragment(currentItem));
            }
        });

    }

    @Override
    public int getItemCount() {
        return productItemsList.size();
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = transactionFragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
