package com.adurcup.adurcupseller.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.adapter.OrderProductAdapter;
import com.adurcup.adurcupseller.adapter.OrderProductModificationAdapter;
import com.adurcup.adurcupseller.adapter.ProductAdapter;
import com.adurcup.adurcupseller.misc.OrderPendingModel;
import com.adurcup.adurcupseller.misc.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductModificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderProductModificationAdapter adapter;
    private List<ProductModel> productList;
    private OrderPendingModel order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_modification);

        Intent i = getIntent();
        order = (OrderPendingModel) i.getSerializableExtra("OrdersList");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(Typeface.DEFAULT_BOLD);
        mTitle.setText("Product Modification");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        productList = new ArrayList<>();

        productList = order.getProducts();

        adapter = new OrderProductModificationAdapter(ProductModificationActivity.this, productList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }

}
