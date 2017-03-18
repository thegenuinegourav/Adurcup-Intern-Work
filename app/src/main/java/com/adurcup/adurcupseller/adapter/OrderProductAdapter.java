package com.adurcup.adurcupseller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.DownloadImageTask;
import com.adurcup.adurcupseller.misc.OnItemClickListener;
import com.adurcup.adurcupseller.misc.ProductModel;

import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.MyViewHolder> {

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product,company,category,identifier1,identifier2, order_quantity, order_price, order_total_price;
        ImageView imageView;
        CheckBox checkBox;

        MyViewHolder(View view) {
            super(view);
            product = (TextView) view.findViewById(R.id.product_name);
            category = (TextView) view.findViewById(R.id.category);
            company = (TextView) view.findViewById(R.id.company_name);
            identifier1 = (TextView) view.findViewById(R.id.identifier1);
            identifier2 = (TextView) view.findViewById(R.id.identifier2);
            imageView = (ImageView) view.findViewById(R.id.image);
            order_price = (TextView) view.findViewById(R.id.order_price);
            order_quantity = (TextView) view.findViewById(R.id.number_of_orders);
            order_total_price = (TextView) view.findViewById(R.id.order_total_price);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);

        }

    }

    private Context mContext;
    private List<ProductModel> productsList;
    private ProductModel p;

    public OrderProductAdapter(Context context, List<ProductModel> productList)
    {
        this.mContext = context;
        this.productsList = productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_order_food_product, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        p = productsList.get(position);
        holder.product.setText(p.getProductTitle());
        holder.company.setText(p.getManufacturer());
        holder.category.setText(p.getCategory());

        holder.order_price.setText("$ " + String.valueOf(p.getOrder_price() + " X "));
        holder.order_quantity.setText(String.valueOf(p.getOrder_quantity()) + " pieces");
        holder.order_total_price.setText("$ " + String.valueOf(p.getOrder_quantity()*p.getOrder_price()));

        if (p.getVolume() != null && p.getVolume().getUnit() != null && p.getVolume().getValue() != null)
            holder.identifier1.setText("The identifier1 (Volume): " + p.getVolume().getValue() + " " + p.getVolume().getUnit());

        if (p.getSizeTop() != null && p.getSizeTop().getType() != null) {
            if (holder.identifier1.getText().equals(" "))
                holder.identifier1.setText("The identifier1 (Size): " + p.getSizeTop().getType() + " " + p.getSizeTop().getValue() + p.getSizeTop().getUnit());
            if (holder.identifier2.getText().equals(" "))
                holder.identifier2.setText("The identifier2 (Size): " + p.getSizeTop().getType() + " " + p.getSizeTop().getValue() + p.getSizeTop().getUnit());
        }

        if (p.getCompartment() != null) {
            if (holder.identifier1.getText().equals(" "))
                holder.identifier1.setText("The identifier1 (Compartment): " + p.getCompartment());
            if (holder.identifier2.getText().equals(" "))
                holder.identifier2.setText("The identifier2 (Compartment): " + p.getCompartment());
        }


        if (p.getGsm() != null) {
            if (holder.identifier1.getText().equals(" "))
                holder.identifier1.setText("The identifier1 (GSM): " + p.getGsm());
            if (holder.identifier2.getText().equals(" "))
                holder.identifier2.setText("The identifier2 (GSM): " + p.getGsm());
        }

        new DownloadImageTask(holder.imageView)
                .execute(mContext.getString(R.string.url_image_75_from_AWS) + p.getImagesURLs().get(0));


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked())
                {
                    p.setChecked(true);
                }
                else {
                    p.setChecked(false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

}