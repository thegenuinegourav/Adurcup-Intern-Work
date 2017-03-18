package com.adurcup.adurcupseller.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.activity.OrderDescriptionActivity;
import com.adurcup.adurcupseller.activity.ProductDescriptionActivity;
import com.adurcup.adurcupseller.adapter.PendingOrderAdapter;
import com.adurcup.adurcupseller.adapter.ProductAdapter;
import com.adurcup.adurcupseller.misc.NewPrice;
import com.adurcup.adurcupseller.misc.OnItemClickListener;
import com.adurcup.adurcupseller.misc.OnItemClickListenerOrder;
import com.adurcup.adurcupseller.misc.OrderPendingModel;
import com.adurcup.adurcupseller.misc.ProductModel;
import com.adurcup.adurcupseller.misc.Shape;
import com.adurcup.adurcupseller.misc.Size;
import com.adurcup.adurcupseller.misc.UnitDescription;
import com.adurcup.adurcupseller.misc.ValueUnit;
import com.adurcup.adurcupseller.misc.VolleyController;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.path;
import static android.content.ContentValues.TAG;
import static com.adurcup.adurcupseller.R.id.product;

public class PendingOrderFragment extends FormFragment {

    private List<OrderPendingModel> orderListPending, orderListToPickUp, orderListReturns;
    private VolleyController volleyController;
    private PendingOrderAdapter adapter;
    private RecyclerView recyclerView;
    private int whichFragment=10;

    public static PendingOrderFragment newInstance(int layoutResId) {

        Bundle bundle = new Bundle();
        PendingOrderFragment pendingOrderFragment = new PendingOrderFragment();
        bundle.putInt("WHICH FRAGMENT",layoutResId);
        pendingOrderFragment.setArguments(bundle);
        return pendingOrderFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pending_order, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        volleyController = VolleyController.getInstance(getContext());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            whichFragment = bundle.getInt("WHICH FRAGMENT", 10);
            switch (whichFragment)
            {
                case 0:
                    parseAllOrders(getString(R.string.url) + getString(R.string.url_orders));
                    break;
                case 1:
                    parseAllOrders(getString(R.string.url) + getString(R.string.url_orders));
                    break;
                case 2:
                    parseAllOrders(getString(R.string.url) + getString(R.string.url_orders));
                    break;
                default:
                    break;
            }
        }

        return rootView;
    }

    public void parseAllOrders(String url)
    {
        showProgressDialog("Fetching");

        StringRequest request = new StringRequest(
                Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String stResponse) {
                        orderListPending = new ArrayList<>();
                        orderListToPickUp = new ArrayList<>();
                        orderListReturns = new ArrayList<>();
                        if(stResponse != null)
                        {
                            try {

                                JSONObject jsonObject = new JSONObject(stResponse);
                                JSONObject data = jsonObject.getJSONObject("data");
                                JSONArray disposables = data.getJSONArray("disposables");
                                for(int i=0; i< disposables.length(); i++)
                                {
                                    JSONObject orderObj = disposables.getJSONObject(i);

                                    OrderPendingModel o = new OrderPendingModel();
                                    o.setOrder_number(orderObj.getString("_id"));

//                                    o.setTotal_order_amount(orderObj.getDouble("subtotal"));
//                                    o.setReturn_amount(orderObj.getDouble("subtotal"));

//                                    o.setPrice(orderObj.getDouble("price"));
//                                    o.setQuantity(orderObj.getInt("quantity"));

                                    o.setOrder_from(orderObj.getJSONArray("businessName").getString(0));
                                    String[] date = orderObj.getJSONArray("orderDate").getString(0).split("[T.]");
                                    o.setOrder_date(date[0]);
                                    o.setOrder_time(date[1]);

                                    //TODO Parse total order amount, return amount, return_id from API
                                    o.setTotal_order_amount(0.0);
                                    o.setReturn_amount(0.0);
                                    o.setReturn_id("Not coming from API");

                                    JSONArray orders = orderObj.getJSONArray("orders");
                                    List<ProductModel> ordersList = new ArrayList<>();
                                    for(int j=0;j<orders.length();j++)
                                    {
                                        JSONObject order = orders.getJSONObject(j);

                                        JSONObject productObj = order.getJSONObject("disposable");
                                        ProductModel product = new ProductModel();

                                        product.setGsm(productObj.getString("gsm"));

                                        JSONObject sizeSide = productObj.getJSONObject("sizeSide");
                                        Size sSide = new Size(sizeSide.getString("unit"),sizeSide.getString("value")
                                                ,sizeSide.getString("type"));
                                        product.setSizeSide(sSide);

                                        JSONObject sizeBottom = productObj.getJSONObject("sizeBottom");
                                        Size sBottom = new Size(sizeBottom.getString("unit"),sizeBottom.getString("value")
                                                ,sizeBottom.getString("type"));
                                        product.setSizeBottom(sBottom);

                                        JSONObject sizeTop = productObj.getJSONObject("sizeTop");
                                        Size sTop = new Size(sizeTop.getString("unit"),sizeTop.getString("value")
                                                ,sizeTop.getString("type"));
                                        product.setSizeTop(sTop);

                                        ValueUnit volume = new ValueUnit();
                                        JSONObject volumeObject = productObj.optJSONObject("volume");
                                        if (volumeObject != null) {
                                            volume.setUnit(volumeObject.optString("unit", "unit"));
                                            volume.setValue(volumeObject.optString("value", "value"));
                                        } else {
                                            volume.setUnit("unit");
                                            volume.setValue("value");
                                        }
                                        product.setVolume(volume);

                                        List<String> images = new ArrayList<>();
                                        JSONArray imagesArray = productObj.getJSONArray("imagesURLs");
                                        for(int k=0;k<imagesArray.length();k++)
                                        {
                                            images.add(k,imagesArray.getString(k));
                                        }
                                        product.setImagesURLs(images);

                                        product.setProductTitle(productObj.getString("productTitle"));
                                        product.setManufacturer(productObj.getString("manufacturer"));
                                        product.setCategory(productObj.getString("category"));

                                        //TODO Parse from Api
                                        product.setOrder_price(order.getDouble("price"));
                                        product.setOrder_quantity(order.getInt("quantity"));

                                        ordersList.add(product);

                                    }

                                    o.setProducts(ordersList);
                                    o.setStatus(orders.getJSONObject(0).getString("statusMessage"));


                                    if(orders.getJSONObject(0).getString("status").equals("pending"))
                                        orderListPending.add(o);
                                    else if(orders.getJSONObject(0).getString("status").equals("topickup")) //TODO check topickup in API
                                        orderListToPickUp.add(o);
                                    else
                                        orderListReturns.add(o);

                                }

                                recyclerView.setVisibility(View.VISIBLE);
                                dismissProgressDialog();

                                if(whichFragment == 0)
                                    adapter = new PendingOrderAdapter(getContext(), orderListPending, whichFragment);
                                else if(whichFragment == 1)
                                    adapter = new PendingOrderAdapter(getContext(), orderListToPickUp, whichFragment);
                                else
                                    adapter = new PendingOrderAdapter(getContext(), orderListReturns, whichFragment);

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(mLayoutManager);

                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(adapter);

                                if(adapter!=null)
                                {
                                    adapter.SetOnItemClickListener(new OnItemClickListenerOrder() {
                                        @Override
                                        public void onItemClick(View v , int position, OrderPendingModel o) {
                                            Intent intent = new Intent(getActivity(), OrderDescriptionActivity.class);
                                            intent.putExtra("OrderObject", o);
                                            startActivity(intent);
                                        }
                                    });
                                }


                            } catch (final JSONException e) {
                                dismissProgressDialog();
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
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

        volleyController.addToRequestQueue(request, TAG);

    }

    @Override
    boolean isErrorActive() {
        return false;
    }
}
