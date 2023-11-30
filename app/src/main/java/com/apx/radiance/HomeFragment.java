package com.apx.radiance;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apx.radiance.adapter.SliderImageAdapter;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

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

        fragment.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SignInActivity.class));
            }
        });

        //Slider Start //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        RecyclerView recyclerView = fragment.findViewById(R.id.sliderRecycler);
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("https://images.pexels.com/photos/4050388/pexels-photo-4050388.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1");
        arrayList.add("https://images.pexels.com/photos/3755516/pexels-photo-3755516.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1");
        arrayList.add("https://images.pexels.com/photos/4968386/pexels-photo-4968386.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1");
        arrayList.add("https://images.pexels.com/photos/3823488/pexels-photo-3823488.jpeg");

        SliderImageAdapter adapter = new SliderImageAdapter(getContext(), arrayList);

        adapter.setOnItemClickListener(new SliderImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String path) {
                startActivity(new Intent(getContext(),ImageViewActivity.class).putExtra("image",path),
                        ActivityOptions.makeSceneTransitionAnimation(getActivity(),imageView,"image").toBundle());
            }
        });

        recyclerView.setAdapter(adapter);

        //Slider End //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }
}