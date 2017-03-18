package com.adurcup.adurcupseller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.fragment.HomeFragment;
import com.adurcup.adurcupseller.fragment.OrderFragment;
import com.adurcup.adurcupseller.fragment.PaymentFragment;
import com.adurcup.adurcupseller.fragment.FoodPackagingFragment;
import com.adurcup.adurcupseller.misc.UserDetail;
import com.adurcup.adurcupseller.misc.UserLocalDatabase;

/**
 * Created by kshivang on 21/12/16.
 *
 */

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private UserLocalDatabase localDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        localDatabase = new UserLocalDatabase(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);

            View headerView = navigationView.getHeaderView(0);
            headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(NavigationActivity.this, ProfileActivity.class));
                }
            });

            UserDetail userDetail = localDatabase.getUserDetail();
            if (userDetail.getName() != null
                    && userDetail.getName().length() < 0) {
                ((TextView) headerView.findViewById(R.id.nav_name))
                        .setText(userDetail.getName());
            }
            ((TextView) headerView.findViewById(R.id.nav_mobile))
                    .setText(userDetail.getUsername());
        }
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.fragment, HomeFragment.newInstance());
        tx.commit();
    }

    public void onNavigationItemHighlight(int item) {
        navigationView.setCheckedItem(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.home:
                tx.replace(R.id.fragment, HomeFragment.newInstance());
                tx.commit();
                break;
            case R.id.services:
                tx.replace(R.id.fragment, FoodPackagingFragment.newInstance());
                tx.commit();
                break;
            case R.id.order:
                tx.replace(R.id.fragment, OrderFragment.newInstance());
                tx.commit();
                break;
            case R.id.payment:
                tx.replace(R.id.fragment, PaymentFragment.newInstance());
                tx.commit();
                break;
            case R.id.help:
                startActivity(new Intent(NavigationActivity.this, HelpActivity.class));
                break;
            case R.id.contact:
                startActivity(new Intent(NavigationActivity.this,ContactUsActivity.class));
                break;
            case R.id.logout:
                localDatabase.logout();
                startActivity(new Intent(NavigationActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (navigationView.getMenu().getItem(0).isChecked()) {
            this.moveTaskToBack(true);
        } else {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.fragment, HomeFragment.newInstance());
            tx.commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }
}
