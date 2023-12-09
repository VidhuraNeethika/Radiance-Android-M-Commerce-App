package com.apx.radiance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apx.radiance.adapter.NotificationAdapter;
import com.apx.radiance.model.NotificationItems;
import com.apx.radiance.model.Tags;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerRecyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private ArrayList<Tags> tagsArrayList;
    private RecyclerView.LayoutManager horizontalLayoutManager;
    GridLayoutManager gridLayoutManager;
    private String[] tagsName;

    public NotificationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        // Notification Start //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<NotificationItems> notificationItemsList = new ArrayList<>();

        notificationItemsList.add(new NotificationItems(
                "Become a personal customer",
                "Great news! Discover amazing deals and discounts on your favorite products with our m-commerce Android app. Shop conveniently from anywhere, browse a wide range of products, and place orders with ease. Don't miss out on exclusive offers and updates. Download our app now and start shopping today!",
                "5 Min Ago",
                R.drawable.bell_notification_icon));

        notificationItemsList.add(new NotificationItems(
                "Stay Updated with Exciting Offers!",
                "Hey there! Get ready for some incredible deals on our m-commerce Android app. Explore a wide range of products, from fashion to electronics, and enjoy exclusive discounts. Don't miss out on the latest trends and must-have items. Download our app now and start saving today!",
                "30 Min Ago",
                R.drawable.bell_notification_icon));

        notificationItemsList.add(new NotificationItems(
                "Seasonal Offers Just for You!",
                "Hello there! Get ready for the season with our amazing deals and discounts on our m-commerce Android app. From winter clothing to holiday gifts, we have everything you need to celebrate in style. Don't miss out on the chance to save big this season. Download our app now and start shopping today!",
                "45 Min Ago",
                R.drawable.bell_notification_icon));


        recyclerRecyclerView = fragment.findViewById(R.id.notificiation_recylcer);
        recyclerRecyclerView.setHasFixedSize(true);
        horizontalLayoutManager = new LinearLayoutManager(getContext());
        recyclerAdapter = new NotificationAdapter(notificationItemsList);
        recyclerRecyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerRecyclerView.setAdapter(recyclerAdapter);

        // Notification End ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }
}