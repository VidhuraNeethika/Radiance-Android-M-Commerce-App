package com.apx.radiance;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apx.radiance.adapter.GridProductAdapter;
import com.apx.radiance.adapter.MtLProductAdapter;
import com.apx.radiance.adapter.SliderImageAdapter;
import com.apx.radiance.adapter.TagAdapter;
import com.apx.radiance.model.Product;
import com.apx.radiance.model.Tags;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerRecyclerView;
    private RecyclerView.Adapter comAccAdapter, eleAdapter, moreToLoveAdapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<Tags> tagsArrayList;
    private RecyclerView.LayoutManager horizontalLayoutManager;
    private String[] tagsName;
    private FirebaseDatabase firebaseDatabase;

    // Location
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView locationText;
    private final static int CURRENT_LOCATION_REQUEST_CODE = 101;

    Dialog dialog;
    Button okButton;

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

        // Shared Preferences
        SharedPreferences preferences = getContext().getSharedPreferences("user_log", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.welcome_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.dialog_background));
        dialog.setCancelable(false);

        okButton = dialog.findViewById(R.id.okButtonHome);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("login_status", "success");
                editor.apply();
                dialog.dismiss();
            }
        });

        String successText = preferences.getString("login_status", "fail");
        if(successText.equals("fail")){
            dialog.show();
        }

        // Location Start

        locationText = fragment.findViewById(R.id.locationTextHome);
        locationText.setAllCaps(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getUserCurrentLocation();
            }
        }).start();

        //Location End

        //Slider Start

        fragment.findViewById(R.id.searchFieldHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SearchFragment());
            }
        });

        fragment.findViewById(R.id.notificationIconHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new NotificationFragment());
            }
        });

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

        //Slider End

        //Computer Accessories Start

        ArrayList<Product> comAccProducts = new ArrayList<>();

        firebaseDatabase.getReference("Products").orderByChild("regDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);

                    if (product.getCategory().equals("Computer Accessories")) {

                        comAccProducts.add(product);

                    }

                    Collections.reverse(comAccProducts);

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
        comAccAdapter = new GridProductAdapter(comAccProducts, HomeFragment.this);
        recyclerRecyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerRecyclerView.setAdapter(comAccAdapter);

        //Computer Accessories End

        //Electronic items Start

        ArrayList<Product> eleProductList = new ArrayList<>();

        firebaseDatabase.getReference("Products").orderByChild("regDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);

                    if (product.getCategory().equals("Electronic Accessories")) {

                        eleProductList.add(product);

                    }
                }
                Collections.reverse(eleProductList);
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
        eleAdapter = new GridProductAdapter(eleProductList, HomeFragment.this);
        recyclerRecyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerRecyclerView.setAdapter(eleAdapter);

        //Electronic items End


        //Tags Start

        categoryInitialized();
        recyclerRecyclerView = fragment.findViewById(R.id.tagsRecyclerView);
        horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerRecyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerRecyclerView.setHasFixedSize(true);
        TagAdapter tagAdapter = new TagAdapter(getContext(), tagsArrayList);
        recyclerRecyclerView.setAdapter(tagAdapter);

        //Tags End

        //More to Love Start

        ArrayList<Product> moretoLoveProducts = new ArrayList<>();

        firebaseDatabase.getReference("Products").orderByChild("regDate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    moretoLoveProducts.add(product);
                }
                Collections.reverse(moretoLoveProducts);
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
        moreToLoveAdapter = new MtLProductAdapter(moretoLoveProducts, HomeFragment.this);
        recyclerRecyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerRecyclerView.setAdapter(moreToLoveAdapter);

        //More to Love End

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

    public void loadFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    private void getUserCurrentLocation(){

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null){

                        try {
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            locationText.setText(addresses.get(0).getLocality()+", "+addresses.get(0).getCountryName());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            });

        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CURRENT_LOCATION_REQUEST_CODE);
            loadFragment(new HomeFragment());

        }

    }

}