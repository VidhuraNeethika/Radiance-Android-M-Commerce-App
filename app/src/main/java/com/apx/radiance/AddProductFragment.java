package com.apx.radiance;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class AddProductFragment extends Fragment {

    private ImageButton imageButton01, imageButton02, imageButton03, imageButton04;
    private Uri imgUri;
    private FirebaseFirestore db;
    private Spinner brandView, categoryView,modelView;
    private String categoryText, brandText, modelText;

    public AddProductFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        imageButton01 = fragment.findViewById(R.id.pImg01);
        imageButton02 = fragment.findViewById(R.id.pImg02);
        imageButton03 = fragment.findViewById(R.id.pImg03);
        imageButton04 = fragment.findViewById(R.id.pImg04);

        db = FirebaseFirestore.getInstance();
        categoryView = fragment.findViewById(R.id.categoryItems);

        imageButton01.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        activityResultLauncher1.launch(Intent.createChooser(intent, "Select Image"));
                    }
                }
        );

        imageButton02.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        activityResultLauncher2.launch(Intent.createChooser(intent, "Select Image"));
                    }
                }
        );

        imageButton03.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        activityResultLauncher3.launch(Intent.createChooser(intent, "Select Image"));
                    }
                }
        );

        imageButton04.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        activityResultLauncher4.launch(Intent.createChooser(intent, "Select Image"));
                    }
                }
        );

        categoryView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                categoryText = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // load categories

        db.collection("/Category").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList categoryArrayList = new ArrayList<>();
                            categoryArrayList.add("Select Category");
                            for (QueryDocumentSnapshot result : task.getResult()) {
                                String name = result.getId();
                                categoryArrayList.add(name);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                                    android.R.layout.simple_spinner_item, categoryArrayList);
                            adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                            categoryView.setAdapter(adapter);
                        }
                    }
                }
        );

    }

    ActivityResultLauncher<Intent> activityResultLauncher1 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        imgUri = result.getData().getData();
                        Glide.with(getContext()).load(imgUri).fitCenter().into(imageButton01);
                    }
                }
            }
    );
    ActivityResultLauncher<Intent> activityResultLauncher2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        imgUri = result.getData().getData();
                        Glide.with(getContext()).load(imgUri).fitCenter().into(imageButton02);
                    }
                }
            }
    );
    ActivityResultLauncher<Intent> activityResultLauncher3 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        imgUri = result.getData().getData();
                        Glide.with(getContext()).load(imgUri).fitCenter().into(imageButton03);
                    }
                }
            }
    );
    ActivityResultLauncher<Intent> activityResultLauncher4 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        imgUri = result.getData().getData();
                        Glide.with(getContext()).load(imgUri).fitCenter().into(imageButton04);
                    }
                }
            }
    );

}