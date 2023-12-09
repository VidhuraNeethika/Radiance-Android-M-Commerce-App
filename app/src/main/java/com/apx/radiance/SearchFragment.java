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

import com.apx.radiance.adapter.GridProductAdapter;
import com.apx.radiance.adapter.TagAdapter;
import com.apx.radiance.model.Product;
import com.apx.radiance.model.Tags;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerRecyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private ArrayList<Tags> tagsArrayList;
    private RecyclerView.LayoutManager horizontalLayoutManager;
    GridLayoutManager gridLayoutManager;
    private String[] tagsName;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        // Tags Start ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        categoryInitialized();
        recyclerRecyclerView = fragment.findViewById(R.id.tagsRecyclerView);
        horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerRecyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerRecyclerView.setHasFixedSize(true);
        TagAdapter tagAdapter = new TagAdapter(getContext(), tagsArrayList);
        recyclerRecyclerView.setAdapter(tagAdapter);

        // Tags End //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Search Start //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<String> imageList = new ArrayList<>();
        imageList.add("https://i.ebayimg.com/images/g/6l4AAOSwiadlBoUS/s-l1600.jpg");

        ArrayList<Product> productsList = new ArrayList<>();
        Product product = new Product();
        product.setImageList(imageList);
        product.setName("Logitech - G305 LIGHTSPEED Wireless Optical Gaming Mouse - 6 Programmable Button");
        product.setBrand("Logitech");
        product.setCategory("Gaming Mouse");
        product.setPrice(100.00);

        for (int i = 0; i < 10; i++) {
            productsList.add(product);
        }

        recyclerRecyclerView = fragment.findViewById(R.id.searchResultRecycler);
        recyclerRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerAdapter = new GridProductAdapter(productsList);
        recyclerRecyclerView.setLayoutManager(gridLayoutManager);
        recyclerRecyclerView.setAdapter(recyclerAdapter);

        // Search End ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    private void categoryInitialized() {

        tagsArrayList = new ArrayList<>();

        tagsName = new String[]{
                "All",
                "New Arrivals",
                "Popular",
                "Trending",
                "See more",
        };

        for (int i = 0; i < tagsName.length; i++) {

            Tags tags = new Tags(tagsName[i]);
            tagsArrayList.add(tags);
        }


    }
}