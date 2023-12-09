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
import android.widget.ImageView;

import com.apx.radiance.adapter.OrdersAdapter;
import com.apx.radiance.model.Orders;
import com.apx.radiance.model.Product;

import java.util.ArrayList;

public class OrderFragment extends Fragment {
    public OrderFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        ImageView imageView =  fragment.findViewById(R.id.imageView10);

        ArrayList<String> imageList = new ArrayList<>();
        imageList.add("https://i.ebayimg.com/images/g/6l4AAOSwiadlBoUS/s-l1600.jpg");

        Product product = new Product();
        product.setImageList(imageList);
        product.setName("Logitech - G305 LIGHTSPEED Wireless Optical Gaming Mouse - 6 Programmable Button");
        product.setBrand("Logitech");
        product.setCategory("Gaming Mouse");
        product.setPrice(100.00);

        //Products
        ArrayList<Orders> orderArrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            orderArrayList.add(new Orders(product,
                    "22:00 23-12-2023",
                    2
            ));
        }

        RecyclerView recyclerView = fragment.findViewById(R.id.ordersRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter<OrdersAdapter.ViewHolder> adapter = new OrdersAdapter(orderArrayList);

        if (orderArrayList.isEmpty()) {
            fragment.findViewById(R.id.myProductsBodyEmptyLayout).setVisibility(View.VISIBLE);
            fragment.findViewById(R.id.ordersLayout).setVisibility(View.INVISIBLE);
        } else {
            recyclerView.setAdapter(adapter);
        }


    }
}