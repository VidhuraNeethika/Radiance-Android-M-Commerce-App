package com.apx.radiance;

import android.app.ActivityOptions;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.apx.radiance.adapter.GridProductAdapter;
import com.apx.radiance.adapter.MtLProductAdapter;
import com.apx.radiance.adapter.SliderImageAdapter;
import com.apx.radiance.adapter.TagAdapter;
import com.apx.radiance.model.Product;
import com.apx.radiance.model.Tags;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerRecyclerView;
    private RecyclerView.Adapter comAccAdapter, eleAdapter, moreToLoveAdapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Tags> tagsArrayList;
    private RecyclerView.LayoutManager horizontalLayoutManager;
    private String[] tagsName;
    private FirebaseDatabase firebaseDatabase;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();

        //Slider Start //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        RecyclerView recyclerView = fragment.findViewById(R.id.sliderRecycler);
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("https://static1.xdaimages.com/wordpress/wp-content/uploads/2022/10/XDA-9.jpg");
        arrayList.add("https://www.iclarified.com/images/news/91148/436756/436756-640.jpg");
        arrayList.add("https://image.benq.com/is/image/benqco/dp1310-banner-231122-2048-1?$ResponsivePreset$");
        arrayList.add("https://plugmedia-wp-uploads.s3.ap-southeast-1.amazonaws.com/wp-content/uploads/2022/08/Screenshot-2022-08-12-at-14-30-10-Anker-737-Power-Bank-PowerCore-24K.jpg");

        SliderImageAdapter adapter = new SliderImageAdapter(getContext(), arrayList);

        adapter.setOnItemClickListener(new SliderImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String path) {
                startActivity(new Intent(getContext(), ImageViewActivity.class).putExtra("image", path),
                        ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, "image").toBundle());
            }
        });

        recyclerView.setAdapter(adapter);

        //Slider End //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Computer Accessories Start ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<Product> comAccProducts = new ArrayList<>();

        firebaseDatabase.getReference("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);

                    if(product.getCategory().equals("Computer Accessories")){

                        comAccProducts.add(product);

                    }


                }
                comAccAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Db data load fail", Toast.LENGTH_LONG).show();
            }
        });

        recyclerRecyclerView = fragment.findViewById(R.id.serachResultRecycler);
        recyclerRecyclerView.setHasFixedSize(true);
        horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        comAccAdapter = new GridProductAdapter(comAccProducts);
        recyclerRecyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerRecyclerView.setAdapter(comAccAdapter);

        //Computer Accessories End /////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Electronic items Start /////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<Product> eleProductList = new ArrayList<>();

        firebaseDatabase.getReference("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);

                    if(product.getCategory().equals("Electronic Accessories")){

                        eleProductList.add(product);

                    }
                }
                eleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Db data load fail", Toast.LENGTH_LONG).show();
            }
        });

        recyclerRecyclerView = fragment.findViewById(R.id.trendingProductRecycler);
        recyclerRecyclerView.setHasFixedSize(true);
        horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        eleAdapter = new GridProductAdapter(eleProductList);
        recyclerRecyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerRecyclerView.setAdapter(eleAdapter);

        //Electronic items End /////////////////////////////////////////////////////////////////////////////////////////////////////////


        //Tags Start ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

        categoryInitialized();
        recyclerRecyclerView = fragment.findViewById(R.id.tagsRecyclerView);
        horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerRecyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerRecyclerView.setHasFixedSize(true);
        TagAdapter tagAdapter = new TagAdapter(getContext(), tagsArrayList);
        recyclerRecyclerView.setAdapter(tagAdapter);

        //Tags End /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //More to Love Start ///////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<Product> moretoLoveProducts = new ArrayList<>();

        firebaseDatabase.getReference("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    moretoLoveProducts.add(product);
                }
                moreToLoveAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Db data load fail", Toast.LENGTH_LONG).show();
            }
        });

        recyclerRecyclerView = fragment.findViewById(R.id.moreToLoveRecycler);
        recyclerRecyclerView.setHasFixedSize(true);
        horizontalLayoutManager = new LinearLayoutManager(getContext());
        moreToLoveAdapter = new MtLProductAdapter(moretoLoveProducts);
        recyclerRecyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerRecyclerView.setAdapter(moreToLoveAdapter);

        //More to Love End /////////////////////////////////////////////////////////////////////////////////////////////////////////

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