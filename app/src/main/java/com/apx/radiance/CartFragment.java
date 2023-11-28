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

import com.apx.radiance.adapter.CartProductAdapter;
import com.apx.radiance.model.ProductItem;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

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

        ArrayList<ProductItem> productsList = new ArrayList<>();
        productsList.add(new ProductItem(
                R.drawable.logitech_mouse,
                "Logitech - G305 LIGHTSPEED Wireless Optical Gaming Mouse - 6 Programmable Button",
                "Logitech",
                "Gaming Mouse",
                100.00));

        productsList.add(new ProductItem(
                R.drawable.logitech_mouse,
                "Logitech - G305 LIGHTSPEED Wireless Optical Gaming Mouse - 6 Programmable Button",
                "Logitech",
                "Gaming Mouse",
                100.00));

        productsList.add(new ProductItem(
                R.drawable.logitech_mouse,
                "Logitech - G305 LIGHTSPEED Wireless Optical Gaming Mouse - 6 Programmable Button",
                "Logitech",
                "Gaming Mouse",
                100.00));

        recyclerView = fragment.findViewById(R.id.productListRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new CartProductAdapter(productsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}