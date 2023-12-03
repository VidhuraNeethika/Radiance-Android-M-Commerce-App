package com.apx.radiance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, NavigationBarView.OnItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.materialToolBar);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        findViewById(R.id.imageMenuButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout dl = findViewById(R.id.drawerLayout);

        if (item.getItemId() == R.id.sideNavHome || item.getItemId() == R.id.bottomNavHome) {
            loadFragment(new HomeFragment());
        } else if (item.getItemId() == R.id.sideNavCart || item.getItemId() == R.id.bottomNavCart) {
            loadFragment(new CartFragment());
            dl.setBackgroundResource(R.color.dark_white);
        } else if (item.getItemId() == R.id.sideNavWishlist) {
            loadFragment(new WishlistFragment());
        }else if (item.getItemId() == R.id.sideNavProfile || item.getItemId() == R.id.bottomNavProfile) {
            loadFragment(new ProfileFragment());
        }else if (item.getItemId() == R.id.sideNavSearch || item.getItemId() == R.id.bottomNavSerach) {
            loadFragment(new SearchFragment());
        }else if (item.getItemId() == R.id.sideNavNotification || item.getItemId() == R.id.bottomNavNotification) {
            loadFragment(new NotificationFragment());
        }else if (item.getItemId() == R.id.privacyPolicy ) {
            loadFragment(new PrivacyPolicyFragment());
        }else if (item.getItemId() == R.id.sideNavOrders ) {
            loadFragment(new OrderFragment());
        }else if (item.getItemId() == R.id.sideNavLog ) {
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
        }else if (item.getItemId() == R.id.helpCenter ) {
            loadFragment(new SingleProductViewFragment());
        }

        dl.setBackgroundResource(R.color.white);
        drawerLayout.close();
        return true;

    }

    public void loadFragment(Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}