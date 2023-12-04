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

import com.apx.radiance.adapter.OrdersAdapter;
import com.apx.radiance.model.Orders;
import com.apx.radiance.model.ProductItem;

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

        //Products
        ArrayList<Orders> orderArrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            orderArrayList.add(new Orders(
                    new ProductItem(
                            R.drawable.logitech_mouse,
                            "Logitech - G305 LIGHTSPEED Wireless Optical Gaming Mouse - 6 Programmable Button",
                            "Logitech",
                            "Gaming Mouse",
                            100.00),
                    "22:00 23-12-2023",
                    2
            ));
        }

        RecyclerView recyclerView = fragment.findViewById(R.id.ordersRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter<OrdersAdapter.ViewHolder> adapter = new OrdersAdapter(orderArrayList);

        if (orderArrayList.isEmpty()) {
            fragment.findViewById(R.id.ordersBodyEmptyLayout).setVisibility(View.VISIBLE);
            fragment.findViewById(R.id.ordersLayout).setVisibility(View.INVISIBLE);
        } else {
            recyclerView.setAdapter(adapter);
        }


    }
}