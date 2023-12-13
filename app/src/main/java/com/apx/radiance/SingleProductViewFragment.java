package com.apx.radiance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apx.radiance.adapter.CartProductAdapter;
import com.apx.radiance.adapter.SingleProductViewImageAdapter;
import com.apx.radiance.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SingleProductViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Product product;

    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;

    public SingleProductViewFragment() {
    }

    public SingleProductViewFragment(Product product) {
        this.product = product;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_single_product_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        ArrayList<String> imageList = new ArrayList<>();

        TextView categoryField = fragment.findViewById(R.id.catTextField);
        TextView brandField = fragment.findViewById(R.id.brandField);
        TextView nameField = fragment.findViewById(R.id.productNameFieldS);
        TextView priceField = fragment.findViewById(R.id.priceFieldS);
        TextView descriptionField = fragment.findViewById(R.id.descrptionFieldS);
        TextView qtyField = fragment.findViewById(R.id.qtyTextS);

        for (int i = 0; i < product.getImageList().size(); i++) {
            imageList.add(product.getImageList().get(i));
        }

        recyclerView = fragment.findViewById(R.id.singleProductViewImageRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new SingleProductViewImageAdapter(imageList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        categoryField.setText(product.getCategory());
        brandField.setText(product.getBrand());
        nameField.setText(product.getName());
        priceField.setText("Rs." + product.getPrice().toString() + "0");
        descriptionField.setText(product.getDescription());
        qtyField.setText("Quantity : " + product.getQuantity());

        fragment.findViewById(R.id.addToCartBtnS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long currentTimeMillis = System.currentTimeMillis();

                // ADD TO CART
                FirebaseDatabase.getInstance().getReference("Cart").child(currentUser.getUid())
                        .child(product.getpId()).setValue(currentTimeMillis)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show();

                            }
                        });


            }
        });

    }
}