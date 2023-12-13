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
import android.widget.Toast;

import com.apx.radiance.adapter.WishlistProductAdapter;
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


public class WishlistFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    public WishlistFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wishlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        ArrayList<Product> productsList = new ArrayList<>();

        ArrayList<String> list = new ArrayList<>();

        firebaseDatabase.getReference("Wishlist/" + currentUser.getUid()).orderByValue()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.getChildrenCount() == 0) {
                            fragment.findViewById(R.id.wishlistBodyEmptyLayout).setVisibility(View.VISIBLE);
                            fragment.findViewById(R.id.wishlistBodyLayout).setVisibility(View.GONE);
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

        recyclerView = fragment.findViewById(R.id.productListRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());

        adapter = new WishlistProductAdapter(productsList, WishlistFragment.this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fragment.findViewById(R.id.startShoppingBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

    }
}