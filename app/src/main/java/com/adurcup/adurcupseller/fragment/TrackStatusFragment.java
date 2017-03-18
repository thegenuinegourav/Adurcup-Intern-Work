package com.adurcup.adurcupseller.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.adapter.TrackStatusAdapter;
import com.adurcup.adurcupseller.misc.TrackStatusModel;
import com.adurcup.adurcupseller.misc.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import static android.content.ContentValues.TAG;


public class TrackStatusFragment extends FormFragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<TrackStatusModel> trackStatus;


    public static TrackStatusFragment newInstance() {

        Bundle args = new Bundle();

        TrackStatusFragment fragment = new TrackStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View rootView = inflater.inflate(R.layout.track_status, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.track_status_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            mRecyclerView.setVisibility(View.GONE);
            showProgressDialog("Fetching");

            StringRequest request = new StringRequest(
                    Request.Method.GET, getString(R.string.url) + getString(R.string.url_pickups),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String stResponse) {
                            if(stResponse != null)
                            {
                                try {
                                    trackStatus = new ArrayList<>();
                                    JSONArray jsonArray = new JSONArray(stResponse);
                                    for(int i=0; i < jsonArray.length();i++)
                                    {
                                        TrackStatusModel trackStatusModel = new TrackStatusModel();
                                        JSONObject object = jsonArray.optJSONObject(i);
                                        trackStatusModel.setItems(object.getString("productQuantity"));
                                        trackStatusModel.setWeight(object.getString("productWeight"));
                                        trackStatusModel.setPickupAddress(object.getString("pickupAddress"));
                                        trackStatusModel.setPickupId(object.getString("pickupId"));
                                        trackStatusModel.setStatus(object.getString("status"));
                                        trackStatus.add(trackStatusModel);

                                        mRecyclerView.setVisibility(View.VISIBLE);
                                        dismissProgressDialog();
                                    }

                                    mAdapter = new TrackStatusAdapter(trackStatus);

                                    mRecyclerView.setAdapter(mAdapter);


                                } catch (final JSONException e) {
                                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                                    dismissProgressDialog();
                                    Toast.makeText(getContext(),
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                            } else {
                                dismissProgressDialog();
                                Log.e(TAG, "Couldn't get json from server.");
                                Toast.makeText(getContext(),
                                        "Couldn't get json from server. Check LogCat for possible errors!",
                                        Toast.LENGTH_LONG)
                                        .show();

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dismissProgressDialog();
                            Toast.makeText(getContext(),
                                    "Oops, Something went wrong!",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

            VolleyController.getInstance(getContext()).addToRequestQueue(request, TAG);

        }
        else {
            if(mRecyclerView != null)
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    boolean isErrorActive() {
        return false;
    }
}
