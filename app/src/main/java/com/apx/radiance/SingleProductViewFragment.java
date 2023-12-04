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
import com.apx.radiance.adapter.SingleProductViewImageAdapter;
import com.apx.radiance.model.Product;

import java.util.ArrayList;

public class SingleProductViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public SingleProductViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_single_product_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        ArrayList<String> imageList = new ArrayList<>();


//        for (int i = 0; i < 10; i++) {
            imageList.add("https://i.ebayimg.com/images/g/6l4AAOSwiadlBoUS/s-l1600.jpg");
            imageList.add("https://i.ebayimg.com/images/g/hEYAAOSwxQFlByjD/s-l960.jpg");
            imageList.add("https://i.ebayimg.com/images/g/SVcAAOSwvwZlVFQ8/s-l1600.jpg");
//        }

        recyclerView = fragment.findViewById(R.id.singleProductViewImageRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new SingleProductViewImageAdapter(imageList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}