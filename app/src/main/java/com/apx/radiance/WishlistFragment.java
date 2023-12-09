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

import com.apx.radiance.adapter.WishlistProductAdapter;
import com.apx.radiance.model.Product;

import java.util.ArrayList;


public class WishlistFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

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

        ArrayList<Product> productsList = new ArrayList<>();

        ArrayList<String> imageList = new ArrayList<>();
        imageList.add("https://i.ebayimg.com/images/g/6l4AAOSwiadlBoUS/s-l1600.jpg");

        Product product = new Product();
        product.setImageList(imageList);
        product.setName("Logitech - G305 LIGHTSPEED Wireless Optical Gaming Mouse - 6 Programmable Button");
        product.setBrand("Logitech");
        product.setCategory("Gaming Mouse");
        product.setPrice(100.00);

        for (int i = 0; i < 10; i++) {
            productsList.add(product);
        }

        recyclerView = fragment.findViewById(R.id.productListRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());

        adapter = new WishlistProductAdapter(productsList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}