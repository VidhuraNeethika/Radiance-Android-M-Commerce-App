package com.apx.radiance.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apx.radiance.R;
import com.apx.radiance.SignInActivity;
import com.apx.radiance.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WishlistProductAdapter extends RecyclerView.Adapter<WishlistProductAdapter.ProductViewHolder> {

    private ArrayList<Product> productItemsList;
    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private Fragment transactionFragment;

    public WishlistProductAdapter(ArrayList<Product> productsList, Fragment fragment) {
        this.productItemsList = productsList;
        this.transactionFragment = fragment;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView name, brand, category, price;
        public ImageButton addToCartBtn;
        public ImageButton removeFromWishlistBtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productNameText);
            brand = itemView.findViewById(R.id.brandText);
            category = itemView.findViewById(R.id.categoryText);
            price = itemView.findViewById(R.id.priceText);
            addToCartBtn = itemView.findViewById(R.id.addToCartBtnWishlist);
            removeFromWishlistBtn = itemView.findViewById(R.id.deleteBtnWishlist);
        }
    }

    @NonNull
    @Override
    public WishlistProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_card_detailed_wishlist, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull WishlistProductAdapter.ProductViewHolder holder, int position) {
        Product currentItem = productItemsList.get(position);
        Picasso.get().load(currentItem.getImageList().get(0)).into(holder.imageView);
        holder.name.setText(currentItem.getName());
        holder.brand.setText(currentItem.getBrand());
        holder.category.setText(currentItem.getCategory());
        holder.price.setText("Rs."+currentItem.getPrice().toString()+"0");

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentUser == null) {
                    Intent intent = new Intent(transactionFragment.getActivity(), SignInActivity.class);
                    startActivity(transactionFragment.getContext(), intent, null);
                } else {
                    long currentTimeMillis = System.currentTimeMillis();

                    // ADD TO CART
                    firebaseDatabase.getReference("Cart").child(currentUser.getUid())
                            .child(currentItem.getpId()).setValue(currentTimeMillis)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(transactionFragment.getContext(), "Added to cart", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(transactionFragment.getContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show();

                                }
                            });
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return productItemsList.size();
    }
}
