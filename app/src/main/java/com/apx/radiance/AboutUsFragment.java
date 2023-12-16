package com.apx.radiance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class AboutUsFragment extends Fragment {

    private WebView webView;

    public AboutUsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragment, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragment, savedInstanceState);

        webView = fragment.findViewById(R.id.videoView);
        String video = "<iframe width=\"400\" height=\"250\" src=\"https://www.youtube.com/embed/ZDFXcgMZJ_A?si=y8xOHhtzw-a75Q77\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";

        webView.loadData(video,"text/html","utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());


        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapLayout);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                LatLng latLng = new LatLng(6.9336739,79.8474667);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng).title("Radiance");
                googleMap.clear();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                googleMap.addMarker(markerOptions);

            }
        });

        fragment.findViewById(R.id.contactUsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + "0761821354"));

                    startActivity(callIntent);

                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                }


            }
        });

//        String url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"; // your URL here
//        MediaPlayer mediaPlayer = new MediaPlayer();
//        mediaPlayer.setAudioAttributes(
//                new AudioAttributes.Builder()
//                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                        .setUsage(AudioAttributes.USAGE_MEDIA)
//                        .build()
//        );
//
//        try {
//
//            mediaPlayer.setDataSource(url);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }
}