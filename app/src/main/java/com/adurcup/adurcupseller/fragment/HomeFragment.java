package com.adurcup.adurcupseller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.activity.NavigationActivity;
import com.adurcup.adurcupseller.misc.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class HomeFragment extends FormFragment{

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        final FragmentTransaction tx = getFragmentManager().beginTransaction();

        VolleyController volleyController = VolleyController.getInstance(getContext());

        rootView.findViewById(R.id.orders_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity) getActivity()).onNavigationItemHighlight(R.id.order);
                tx.replace(R.id.fragment,OrderFragment.newInstance());
                tx.commit();
            }
        });

        rootView.findViewById(R.id.payment_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity) getActivity()).onNavigationItemHighlight(R.id.payment);
                tx.replace(R.id.fragment,PaymentFragment.newInstance());
                tx.commit();
            }
        });

        rootView.findViewById(R.id.products_section).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationActivity) getActivity()).onNavigationItemHighlight(R.id.services);
                tx.replace(R.id.fragment,FoodPackagingFragment.newInstance());
                tx.commit();
            }
        });

        showProgressDialog("Fetching");

        StringRequest request = new StringRequest(
                Request.Method.GET, getString(R.string.url) + getString(R.string.url_vendor_overview),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String stResponse) {
                        if(stResponse != null)
                        {
                            try {
                                JSONObject jsonObj = new JSONObject(stResponse);

                                JSONObject orders = jsonObj.getJSONObject("order");
                                Integer toadyPickup = orders.getInt("todayPickUp");
                                Integer totalPickup = orders.getInt("totalPickUp");

                                ((TextView)rootView.findViewById(R.id.todays_pickup)).setText(String.valueOf(toadyPickup));
                                ((TextView)rootView.findViewById(R.id.total_pickups)).setText(String.valueOf(totalPickup));


                                JSONObject product = jsonObj.getJSONObject("product");
                                Integer inStock = product.getInt("inStock");
                                Integer outOfStock = product.getInt("outOfStock");
                                Integer inActive = product.getInt("inActive");

                                ((TextView)rootView.findViewById(R.id.in_stock)).setText(String.valueOf(inStock));
                                ((TextView)rootView.findViewById(R.id.out_of_stock)).setText(String.valueOf(outOfStock));
                                ((TextView)rootView.findViewById(R.id.in_active)).setText(String.valueOf(inActive));


                                JSONObject payment = jsonObj.getJSONObject("payment");
                                Integer inSettlement = payment.getInt("inSettlement");
                                Integer settled = payment.getInt("settled");

                                ((TextView)rootView.findViewById(R.id.in_settlement)).setText(String.valueOf(inSettlement));
                                ((TextView)rootView.findViewById(R.id.settled)).setText(String.valueOf(settled));

                                ( rootView.findViewById(R.id.home_section)).setVisibility(View.VISIBLE);
                                dismissProgressDialog();


                            } catch (final JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                                Toast.makeText(getContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        } else {
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

        volleyController.addToRequestQueue(request, TAG);

        return rootView;
    }

    @Override
    boolean isErrorActive() {
        return false;
    }
}
