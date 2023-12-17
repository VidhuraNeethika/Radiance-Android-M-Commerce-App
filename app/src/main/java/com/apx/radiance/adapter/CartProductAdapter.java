package com.apx.radiance.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.apx.radiance.R;
import com.apx.radiance.SingleProductViewFragment;
import com.apx.radiance.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.ProductViewHolder> {

    private ArrayList<Product> productItemsList;

    private Fragment transactionFragment;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private View viewFragment;

    public CartProductAdapter(ArrayList<Product> productsList, Fragment fragment, View viewFragment) {
        this.productItemsList = productsList;
        this.transactionFragment = fragment;
        this.viewFragment = viewFragment;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView name, brand, category, price, qtyTextField;
        public ImageButton deleteBtn, qtyAddBtn, qtyMinusBtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productNameText);
            brand = itemView.findViewById(R.id.brandText);
            category = itemView.findViewById(R.id.categoryText);
            price = itemView.findViewById(R.id.priceText);
            deleteBtn = itemView.findViewById(R.id.deleteButtonWishlist);

            qtyAddBtn = itemView.findViewById(R.id.qtyAddCart);
            qtyMinusBtn = itemView.findViewById(R.id.qtyMinusCart);
            qtyTextField = itemView.findViewById(R.id.qtyTextCart);
        }
    }

    @NonNull
    @Override
    public CartProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_card_detailed, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CartProductAdapter.ProductViewHolder holder, int position) {

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        Product currentItem = productItemsList.get(position);
        Picasso.get().load(currentItem.getImageList().get(0)).into(holder.imageView);
        holder.name.setText(currentItem.getName());
        holder.brand.setText(currentItem.getBrand());
        holder.category.setText(currentItem.getCategory());
        holder.price.setText("Rs." + currentItem.getPrice().toString() + "0");

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

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                currentUser = firebaseAuth.getCurrentUser();
                String pId = currentItem.getpId();
                String uid = currentUser.getUid();

                databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(uid);

                databaseReference.child(pId).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        Toast.makeText(v.getContext(), "Successfully deleted product from your cart", Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

        int currentQty = currentItem.getQuantity();
        TextView totalPrice = viewFragment.findViewById(R.id.totalPriceField);
        TextView netTotalPrice = viewFragment.findViewById(R.id.netTotalField);

        int oldTotalPrice = (int) Double.parseDouble(totalPrice.getText().toString().substring(3));
        int oldNetTotal = (int) Double.parseDouble(netTotalPrice.getText().toString().substring(3));

        holder.qtyAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qtyText = Integer.parseInt((String) holder.qtyTextField.getText());


                if (qtyText < currentQty) {
                    holder.qtyTextField.setText(String.valueOf(qtyText + 1));
                    totalPrice.setText(String.format("Rs.%s0", oldTotalPrice + (currentItem.getPrice() * qtyText)));
                    netTotalPrice.setText(String.format("Rs.%s0", oldNetTotal + (currentItem.getPrice() * qtyText)));

                } else {
                    Toast.makeText(v.getContext(), "Maximum quantity deserve", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.qtyMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int qtyText = Integer.parseInt((String) holder.qtyTextField.getText());
                int oldTotalPrice2 = (int) Double.parseDouble(totalPrice.getText().toString().substring(3));
                int oldNetTotal2 = (int) Double.parseDouble(netTotalPrice.getText().toString().substring(3));

                if (qtyText > 1) {
                    holder.qtyTextField.setText(String.valueOf(qtyText - 1));
                    totalPrice.setText(String.format("Rs.%s0", oldTotalPrice2 - currentItem.getPrice()));
                    netTotalPrice.setText(String.format("Rs.%s0", oldNetTotal2 - currentItem.getPrice()));
                } else {
                    Toast.makeText(v.getContext(), "Minimum quantity is 1", Toast.LENGTH_LONG).show();
                }
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
