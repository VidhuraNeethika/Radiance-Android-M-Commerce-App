package com.apx.radiance;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.apx.radiance.adapter.CartProductAdapter;
import com.apx.radiance.adapter.SingleProductViewImageAdapter;
import com.apx.radiance.model.Product;
import com.apx.radiance.model.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SingleProductViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Product product;

    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseFirestore fireStoreDatabase;

    private NotificationManager notificationManager;
    private String channelId = "info";

    public SingleProductViewFragment() {
    }

    public SingleProductViewFragment(Product product) {
        this.product = product;
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

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        fireStoreDatabase = FirebaseFirestore.getInstance();

        ArrayList<String> imageList = new ArrayList<>();

        TextView categoryField = fragment.findViewById(R.id.catTextField);
        TextView brandField = fragment.findViewById(R.id.brandField);
        TextView nameField = fragment.findViewById(R.id.productNameFieldS);
        TextView priceField = fragment.findViewById(R.id.priceFieldS);
        TextView descriptionField = fragment.findViewById(R.id.descrptionFieldS);
        TextView qtyField = fragment.findViewById(R.id.qtyTextS);

        for (int i = 0; i < product.getImageList().size(); i++) {
            imageList.add(product.getImageList().get(i));
        }

        recyclerView = fragment.findViewById(R.id.singleProductViewImageRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new SingleProductViewImageAdapter(imageList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        categoryField.setText(product.getCategory());
        brandField.setText(product.getBrand());
        nameField.setText(product.getName());
        priceField.setText("Rs." + product.getPrice().toString() + "0");
        descriptionField.setText(product.getDescription());
        qtyField.setText("Quantity : " + product.getQuantity());

        // ADD TO CART
        fragment.findViewById(R.id.addToCartBtnS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentUser == null) {
                    startActivity(new Intent(getContext(), SignInActivity.class));
                } else {
                    long currentTimeMillis = System.currentTimeMillis();

                    // ADD TO CART
                    firebaseDatabase.getReference("Cart").child(currentUser.getUid())
                            .child(product.getpId()).setValue(currentTimeMillis)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(getContext(), "Failed to add to cart", Toast.LENGTH_SHORT).show();

                                }
                            });
                }

            }
        });

        // ADD TO WISHLIST
        fragment.findViewById(R.id.addToWishListBtnS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentUser == null) {
                    startActivity(new Intent(getContext(), SignInActivity.class));
                } else {
                    long currentTimeMillis = System.currentTimeMillis();

                    // ADD TO WISHLIST
                    firebaseDatabase.getReference("Wishlist").child(currentUser.getUid())
                            .child(product.getpId()).setValue(currentTimeMillis)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(getContext(), "Added to wishlist", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(getContext(), "Failed to add to wishlist", Toast.LENGTH_SHORT).show();

                                }
                            });
                }

            }
        });

        fragment.findViewById(R.id.buyNowBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentUser != null) {

                    // SEND SMS

                    fireStoreDatabase.collection("Users").document(firebaseAuth.getUid()).get().addOnSuccessListener(
                            new OnSuccessListener<com.google.firebase.firestore.DocumentSnapshot>() {
                                @Override
                                public void onSuccess(com.google.firebase.firestore.DocumentSnapshot documentSnapshot) {

                                    if (documentSnapshot.exists()) {

                                        User user = documentSnapshot.toObject(User.class);

                                        // NOTIFICATION

                                        notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

                                        requestPermissions(new String[]{
                                                Manifest.permission.POST_NOTIFICATIONS
                                        }, 1000);

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            NotificationChannel channel = new NotificationChannel(channelId, "INFO", NotificationManager.IMPORTANCE_DEFAULT);
                                            channel.setShowBadge(true);
                                            channel.setDescription("This is Information Channel");
                                            channel.enableLights(true);
                                            channel.setLightColor(Color.BLUE);
                                            channel.setVibrationPattern(new long[]{0, 1000, 1000, 1000});
                                            channel.enableVibration(true);
                                            notificationManager.createNotificationChannel(channel);
                                        }

                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        intent.putExtra("name", "testing");

                                        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
                                        );

                                        String message = "Hello " + user.getFirstName() + ", Your order has been placed successfully! Thank you for shopping with us.";

                                        Notification notification = new NotificationCompat.Builder(getActivity().getApplicationContext(), channelId)
                                                .setAutoCancel(true)
                                                .setSmallIcon(R.drawable.notification_icon)
                                                .setContentTitle("Order Placed Successfully!")
                                                .setContentText(message)
                                                .setContentIntent(pendingIntent)
                                                .build();

                                        notificationManager.notify(1, notification);

                                        // NOTIFICATION

                                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

                                            SmsManager smsManager = SmsManager.getDefault();
                                            smsManager.sendTextMessage(user.getMobile(), null, message, null, null);

                                            Toast.makeText(getContext(), "Message Sent Successfully.", Toast.LENGTH_LONG).show();

                                        } else {
                                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
                                        }

                                    }

                                }
                            }
                    ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Something went wrong.Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // SEND SMS

                } else {
                    Toast.makeText(getContext(), "Please login first!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}