package com.apx.radiance;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apx.radiance.adapter.NotificationAdapter;
import com.apx.radiance.model.NotificationItems;
import com.apx.radiance.model.Tags;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerRecyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private ArrayList<Tags> tagsArrayList;
    private RecyclerView.LayoutManager horizontalLayoutManager;

    SharedPreferences sharedPreferences;

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

        loadNotification(fragment);

        fragment.findViewById(R.id.clearAllText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                loadNotification(fragment);

            }
        });

        fragment.findViewById(R.id.searchAgainButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment());
            }
        });

    }

    private void loadNotification(View fragment) {

        ArrayList<NotificationItems> notificationItemsList = new ArrayList<>();

        Gson gson = new Gson();

        sharedPreferences = getContext().getSharedPreferences("notification", Context.MODE_PRIVATE);

        if (sharedPreferences.getAll().isEmpty()) {
            fragment.findViewById(R.id.notificationBodyEmptyLayout).setVisibility(View.VISIBLE);
            fragment.findViewById(R.id.notificationBodyLayout).setVisibility(View.GONE);
        } else {
            fragment.findViewById(R.id.notificationBodyEmptyLayout).setVisibility(View.GONE);
            fragment.findViewById(R.id.notificationBodyLayout).setVisibility(View.VISIBLE);

            Map<String, ?> allEntries = sharedPreferences.getAll();

            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                String key = entry.getKey();
                String jsonList = entry.getValue().toString();

                List<NotificationItems> notificationItems = gson.fromJson(jsonList, new TypeToken<List<NotificationItems>>() {
                }.getType());
                for (NotificationItems item : notificationItems) {

                    notificationItemsList.add(new NotificationItems(
                            item.getTitle(),
                            item.getDescription(),
                            item.getTime(),
                            R.drawable.bell_notification_icon));
                }
            }

            recyclerRecyclerView = fragment.findViewById(R.id.notificiation_recylcer);
            recyclerRecyclerView.setHasFixedSize(true);
            horizontalLayoutManager = new LinearLayoutManager(getContext());
            recyclerAdapter = new NotificationAdapter(notificationItemsList);
            recyclerRecyclerView.setLayoutManager(horizontalLayoutManager);
            recyclerRecyclerView.setAdapter(recyclerAdapter);

            // Notification End ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }

    }

    public void loadFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

}