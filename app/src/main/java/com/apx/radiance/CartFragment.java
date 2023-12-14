package com.apx.radiance;

import android.content.Intent;
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

import com.apx.radiance.adapter.CartProductAdapter;
import com.apx.radiance.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private Double total = 0.0;
    private Double shipping = 0.0;
    private Double totalnetTotal = 0.0;

    public CartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        ArrayList<Product> productsList = new ArrayList<>();

        ArrayList<String> list = new ArrayList<>();

        firebaseDatabase.getReference("Cart/" + currentUser.getUid()).orderByValue()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getChildrenCount() == 0) {
                            fragment.findViewById(R.id.cartBodyEmptyLayout).setVisibility(View.VISIBLE);
                            fragment.findViewById(R.id.cartBodyLayout).setVisibility(View.GONE);
                        }

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            list.add(snapshot.getKey());
                        }
                        Collections.reverse(list);

                        for (String id : list) {
                            DatabaseReference reference = firebaseDatabase.getReference("Products");
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot data : snapshot.getChildren()) {
                                        Product product = data.getValue(Product.class);

                                        if (product.getpId().equals(id)) {
                                            productsList.add(product);
                                            total = total + product.getPrice();
                                        }

                                    }
                                    adapter.notifyDataSetChanged();


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//        for (int i = 0; i < productsList.size(); i++) {
//            total = total + productsList.get(i).getPrice();
//        }

        TextView totalTextView = fragment.findViewById(R.id.totalPriceField);
        TextView shippingTextView = fragment.findViewById(R.id.shippingField);
        TextView netTotalTextView = fragment.findViewById(R.id.netTotalField);

        totalTextView.setText(String.valueOf(total));
        shippingTextView.setText(String.valueOf(shipping));
        netTotalTextView.setText(String.valueOf(total + shipping));

        recyclerView = fragment.findViewById(R.id.productListRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new CartProductAdapter(productsList,CartFragment.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fragment.findViewById(R.id.cartStartShippingBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

    }
}