package com.apx.radiance;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apx.radiance.adapter.MyProductAdapter;
import com.apx.radiance.adapter.WishlistProductAdapter;
import com.apx.radiance.model.Product;
import com.apx.radiance.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore database;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageReference;

    public MyProductFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        database = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();

        checkUser();

        ArrayList<Product> myProducts = new ArrayList<>();

        firebaseDatabase.getReference("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);

                    if (product.getSellerEmail().equals(currentUser.getEmail())) {

                        myProducts.add(product);

                    } else {
//                        fragment.findViewById(R.id.myProductsBodyEmptyLayout).setVisibility(View.VISIBLE);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Db data load fail", Toast.LENGTH_LONG).show();
            }
        });

        recyclerView = fragment.findViewById(R.id.productListRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());

        adapter = new MyProductAdapter(myProducts);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fragment.findViewById(R.id.addProductBtnEmptyLayoutMP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AddProductFragment());
            }
        });

        fragment.findViewById(R.id.switchToSellerAccountBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference docRef = database.collection("Users").document(currentUser.getUid());
                docRef.update("userType", "seller").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        fragment.findViewById(R.id.myProductsBodyEmptyLayout).setVisibility(View.VISIBLE);
                        fragment.findViewById(R.id.myProductCustomerView).setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Successfully Switch", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("onFailure : ");
                    }
                });

            }
        });

    }

    private void checkUser() {

        if (currentUser != null) {

            database.collection("Users").document(firebaseAuth.getUid()).get().addOnSuccessListener(
                    new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(com.google.firebase.firestore.DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists()) {

                                User user = documentSnapshot.toObject(User.class);

                                if (user.getUserType().equals("user")) {
                                    getView().findViewById(R.id.myProductsBodyLayout).setVisibility(View.GONE);
                                    getView().findViewById(R.id.myProductsBodyEmptyLayout).setVisibility(View.GONE);

                                    getView().findViewById(R.id.myProductCustomerView).setVisibility(View.VISIBLE);
                                } else {
                                    getView().findViewById(R.id.myProductsBodyLayout).setVisibility(View.VISIBLE);
                                    getView().findViewById(R.id.myProductCustomerView).setVisibility(View.GONE);
                                }

                            } else {
                                Toast.makeText(getContext(), "Please update your account details", Toast.LENGTH_SHORT).show();
                                loadFragment(new ProfileFragment());
                            }

                        }
                    }
            );

        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}