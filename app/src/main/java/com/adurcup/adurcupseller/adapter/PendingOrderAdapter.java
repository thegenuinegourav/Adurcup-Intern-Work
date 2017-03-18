package com.adurcup.adurcupseller.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.OnItemClickListener;
import com.adurcup.adurcupseller.misc.OnItemClickListenerOrder;
import com.adurcup.adurcupseller.misc.OrderPendingModel;
import com.adurcup.adurcupseller.misc.ProductModel;

import java.util.List;

public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView order_number, order_date, order_from, total_order_amount, status, return_id, return_amount;
        public LinearLayout return_section;

        public MyViewHolder(View view) {
            super(view);
            order_number = (TextView) view.findViewById(R.id.order_number);
            order_date = (TextView) view.findViewById(R.id.order_date);
            order_from = (TextView) view.findViewById(R.id.order_from);
            total_order_amount = (TextView) view.findViewById(R.id.total_order_amount);
            status = (TextView) view.findViewById(R.id.status);
            return_id = (TextView) view.findViewById(R.id.return_id);
            return_amount = (TextView) view.findViewById(R.id.return_amount);
            return_section = (LinearLayout) view.findViewById(R.id.return_id_and_return_amount);
            view.setOnClickListener(this);

            switch (whichFragment)
            {
                case 0: status.setTextColor(Color.RED);
                    return_section.setVisibility(View.GONE);
                    break;
                case 1: status.setTextColor(Color.rgb(13, 112, 255));
                    return_section.setVisibility(View.GONE);
                    break;
                case 2: status.setTextColor(Color.BLUE);
                    return_section.setVisibility(View.VISIBLE);
                    break;
            }

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition(), orderList.get(getPosition()));
            }
        }
    }

    private Context mContext;
    private List<OrderPendingModel> orderList;
    private int whichFragment;
    private OnItemClickListenerOrder mItemClickListener;
    private OrderPendingModel o;

    public PendingOrderAdapter(Context context, List<OrderPendingModel> orderList, int whichFragment)
    {
        this.mContext = context;
        this.orderList = orderList;
        this.whichFragment = whichFragment;
    }

    public void SetOnItemClickListener(final OnItemClickListenerOrder mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_pending_order, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        o = orderList.get(position);
        holder.order_number.setText(o.getOrder_number());
        holder.order_date.setText(o.getOrder_date());
        holder.order_from.setText(o.getOrder_from());
        holder.total_order_amount.setText(String.valueOf(o.getTotal_order_amount()));
        holder.status.setText(o.getStatus());

        if(whichFragment == 2)
        {
            holder.return_id.setText(o.getReturn_id());
            holder.return_amount.setText(String.valueOf(o.getReturn_amount()));
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

}
