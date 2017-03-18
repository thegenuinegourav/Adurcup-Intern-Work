package com.adurcup.adurcupseller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.TrackStatusModel;

import java.util.List;


public class TrackStatusAdapter extends RecyclerView.Adapter<TrackStatusAdapter.ViewHolder> {

    private List<TrackStatusModel> trackStatusList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView pickup_id,items,pickup_address,status;
        ViewHolder(View view) {
            super(view);
            pickup_id = (TextView) view.findViewById(R.id.pickup_id);
            items = (TextView) view.findViewById(R.id.items);
            pickup_address = (TextView) view.findViewById(R.id.pick_up_address);
            status = (TextView) view.findViewById(R.id.status);
        }
    }

    public TrackStatusAdapter(List<TrackStatusModel> trackStatusList) {
        this.trackStatusList = trackStatusList;
    }

    @Override
    public TrackStatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_track_status, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new TrackStatusAdapter.ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //TODO set Text to corresponding data coming from Api
        TrackStatusModel trackStatusModel = trackStatusList.get(position);

        holder.pickup_id.setText(trackStatusModel.getPickupId());
        holder.items.setText(trackStatusModel.getItems() + " (" + trackStatusModel.getWeight() + ")");
        holder.pickup_address.setText(trackStatusModel.getPickupAddress());
        holder.status.setText(trackStatusModel.getStatus());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return trackStatusList.size();
    }

}
