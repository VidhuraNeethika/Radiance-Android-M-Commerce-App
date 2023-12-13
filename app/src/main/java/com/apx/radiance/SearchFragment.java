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
import android.widget.SearchView;

import com.apx.radiance.adapter.GridProductAdapter;
import com.apx.radiance.adapter.TagAdapter;
import com.apx.radiance.model.Product;
import com.apx.radiance.model.Tags;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerRecyclerView;
    private GridProductAdapter recyclerAdapter;
    private ArrayList<Tags> tagsArrayList;
    private RecyclerView.LayoutManager horizontalLayoutManager;
    GridLayoutManager gridLayoutManager;
    private String[] tagsName;
    private SearchView searchView;

    private FirebaseDatabase firebaseDatabase;

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

        searchView = fragment.findViewById(R.id.searchFieldSearch);
        searchView.clearFocus();
        ArrayList<Product> productsList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }

        });

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

//        ArrayList<String> imageList = new ArrayList<>();
//        imageList.add("https://i.ebayimg.com/images/g/6l4AAOSwiadlBoUS/s-l1600.jpg");
//
//        Product product = new Product();
//        product.setImageList(imageList);
//        product.setName("Logitech - G305 LIGHTSPEED Wireless Optical Gaming Mouse - 6 Programmable Button");
//        product.setBrand("Logitech");
//        product.setCategory("Gaming Mouse");
//        product.setPrice(100.00);
//
//        for (int i = 0; i < 10; i++) {
//            productsList.add(product);
//        }

        firebaseDatabase.getReference("Products").orderByChild("regDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    productsList.add(product);
                }
                Collections.reverse(productsList);
                recyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Product> searchList = new ArrayList<>();
                for (Product product : productsList) {
                    if (product.getName().toLowerCase().contains(newText.toLowerCase())) {
                        searchList.add(product);
                    }
                }
                recyclerAdapter.searchDetails(searchList);
                return true;
            }
        });

        recyclerRecyclerView = fragment.findViewById(R.id.searchResultRecycler);
        recyclerRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerAdapter = new GridProductAdapter(productsList, SearchFragment.this);
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
