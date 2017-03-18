package com.adurcup.adurcupseller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.activity.FormActivity;
import com.adurcup.adurcupseller.activity.ProductDescriptionActivity;
import com.adurcup.adurcupseller.adapter.PendingOrderAdapter;
import com.adurcup.adurcupseller.adapter.ProductAdapter;
import com.adurcup.adurcupseller.misc.CPrice;
import com.adurcup.adurcupseller.misc.CustomViewPager;
import com.adurcup.adurcupseller.misc.NewPrice;
import com.adurcup.adurcupseller.misc.OnItemClickListener;
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
import java.util.Map;

import static android.R.attr.shape;
import static android.content.ContentValues.TAG;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.media.CamcorderProfile.get;
import static com.adurcup.adurcupseller.R.string.fill_required_field;
import static com.adurcup.adurcupseller.R.string.volume;

public class ActiveFragment extends FormFragment {

    private List<ProductModel> productsActive, productsInactive;
    private VolleyController volleyController;
    private RecyclerView recyclerView;
    private int whichFragment=10;
    private ProductAdapter adapter;
    private View rootView;

    public static ActiveFragment newInstance(int layoutResId) {
        ActiveFragment activeFragment = new ActiveFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("WhichFragment", layoutResId);
        activeFragment.setArguments(bundle);
        return activeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_active_food_packaging, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        volleyController = VolleyController.getInstance(getContext());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            whichFragment = bundle.getInt("WhichFragment", 10);
            switch (whichFragment)
            {
                case 0:
                    parseAllProducts(getString(R.string.url) + getString(R.string.url_list_current_product) + getString(R.string.url_isapp));
                    break;
                case 1:
                    parseAllProducts(getString(R.string.url) + getString(R.string.url_list_current_product) + getString(R.string.url_isapp));
                    break;
                default:
                    break;
            }
        }

        rootView.findViewById(R.id.cups_and_glasses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseAllProducts(getString(R.string.url) + getString(R.string.url_list_current_product) +
                        getString(R.string.url_isapp) + "&category=glass,cup");
            }
        });

        rootView.findViewById(R.id.containers_and_bowls).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseAllProducts(getString(R.string.url) + getString(R.string.url_list_current_product) +
                        getString(R.string.url_isapp) + "&category=container,bowl");
            }
        });

        rootView.findViewById(R.id.trays_and_plates).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseAllProducts(getString(R.string.url) + getString(R.string.url_list_current_product) +
                        getString(R.string.url_isapp) + "&category=tray,plate");
            }
        });

        rootView.findViewById(R.id.cutlery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseAllProducts(getString(R.string.url) + getString(R.string.url_list_current_product) +
                        getString(R.string.url_isapp) + "&category=cutlery");
            }
        });

        rootView.findViewById(R.id.boxes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseAllProducts(getString(R.string.url) + getString(R.string.url_list_current_product) +
                        getString(R.string.url_isapp) + "&category=box");
            }
        });

        rootView.findViewById(R.id.bags_and_pouches).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseAllProducts(getString(R.string.url) + getString(R.string.url_list_current_product) +
                        getString(R.string.url_isapp) + "&category=bag,pouch");
            }
        });

        rootView.findViewById(R.id.napkins).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseAllProducts(getString(R.string.url) + getString(R.string.url_list_current_product) +
                        getString(R.string.url_isapp) + "&category=napkin");
            }
        });

        rootView.findViewById(R.id.others).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseAllProducts(getString(R.string.url) + getString(R.string.url_list_current_product) +
                        getString(R.string.url_isapp) + "&category=other");
            }
        });

        return rootView;
    }


    public void parseAllProducts(String url)
    {

        showProgressDialog("Fetching");

        StringRequest request = new StringRequest(
                Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String stResponse) {
                        productsActive = new ArrayList<>();
                        productsInactive = new ArrayList<>();
                        if(stResponse != null)
                        {
                            try {

                                JSONObject jsonObject = new JSONObject(stResponse);
                                JSONArray jsonArray = jsonObject.getJSONArray("products");
                                for(int i=0; i< jsonArray.length(); i++)
                                {
                                    JSONObject productObj = jsonArray.getJSONObject(i);
                                    ProductModel product = new ProductModel();
                                    product.set_id(productObj.getString("_id"));
                                    product.setUpdatedAt(productObj.getString("updatedAt"));
                                    product.setCreatedAt(productObj.getString("createdAt"));
                                    product.setUser(productObj.getString("user"));
                                    product.setProductID(productObj.getString("productID"));
                                    product.setGroupID(productObj.getString("groupID"));
                                    product.setRank(productObj.getInt("rank"));
                                    product.setSubCategory(productObj.getString("subCategory"));
                                    product.setMaterial(productObj.getString("material"));
                                    product.setColour(productObj.getString("colour"));
                                    product.setGsm(productObj.getString("gsm"));
                                    product.setSurfaceTexture(productObj.getString("surfaceTexture"));
                                    product.setSurfaceDesign(productObj.getString("surfaceDesign"));
                                    product.setAdditionalFeatures(productObj.getString("additionalFeatures"));
                                    product.setSealable(productObj.getString("sealable"));
                                    product.setFreezeSafe(productObj.getBoolean("freezeSafe"));
                                    product.setMicrowaveSafe(productObj.getBoolean("microwaveSafe"));
                                    product.setFoodGrade(productObj.getBoolean("foodGrade"));
                                    product.setRecyclable(productObj.getBoolean("recyclable"));
                                    product.setBiodegradable(productObj.getBoolean("biodegradable"));
                                    product.setBestUsedFor(productObj.getString("bestUsedFor"));
                                    product.setBestUsedIn(productObj.getString("bestUsedIn"));
                                    product.setSeller(productObj.getString("seller"));
                                    product.setTax(productObj.getDouble("tax"));

//                                    JSONArray customerPrice = productObj.getJSONArray("customerPrice");
//                                    List<CPrice> cPrices = new ArrayList<>();
//                                    for(int j=0; j< customerPrice.length(); j++)
//                                    {
//                                        JSONObject customerPriceObj = customerPrice.getJSONObject(j);
//                                        CPrice cPrice = new CPrice(customerPriceObj.getInt("minQuantity"),customerPriceObj.getInt("incrementValue"),
//                                                customerPriceObj.getString("_id"),customerPriceObj.getInt("margin"),customerPriceObj.getInt("maxQuantity"));
//                                        cPrices.add(j,cPrice);
//                                    }
//                                    product.setCustomerPrice(cPrices);

                                    product.set__v(productObj.getInt("__v"));
                                  //  product.setParentId(productObj.getString("parentId"));
                                    product.setSlug(productObj.getString("slug"));
                                    product.setGramage(productObj.getString("gramage"));

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

                                    //lid

                                    ValueUnit weight = new ValueUnit();
                                    JSONObject weightObject = productObj.optJSONObject("weight");
                                    if (weightObject != null) {
                                        weight.setUnit(weightObject.optString("unit", "unit"));
                                        weight.setValue(weightObject.optString("value", "value"));
                                    } else {
                                        weight.setUnit("unit");
                                        weight.setValue("value");
                                    }
                                    product.setWeight(weight);

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


                                    JSONObject shapeObj = productObj.getJSONObject("shape");
                                    Shape shape = new Shape(shapeObj.getString("overview"), shapeObj.getString("details"));
                                    product.setShape(shape);

                                    JSONArray new_price = productObj.getJSONArray("newPrice");
                                    List<NewPrice> newPrices = new ArrayList<>();
                                    for(int j=0 ; j<new_price.length(); j++)
                                    {
                                        JSONObject new_price_obj = new_price.getJSONObject(j);
                                        NewPrice newPrice = new NewPrice(new_price_obj.getInt("minQuantity"),new_price_obj.getInt("incrementValue"),
                                                new_price_obj.getString("_id"),new_price_obj.getDouble("price"),new_price_obj.getInt("maxQuantity"),
                                                new_price_obj.getString("unit"));
                                        newPrices.add(j,newPrice);
                                    }

                                    product.setNewPrice(newPrices);

                                    JSONObject unitDescriptionObj = productObj.getJSONObject("unitDescription");
                                    UnitDescription unitDescription = new UnitDescription(unitDescriptionObj.getInt("value"),unitDescriptionObj.getString("unit"),
                                            unitDescriptionObj.getString("per"));
                                    product.setUnitDescription(unitDescription);

                                    //productOptions
                                    //lidOptions
                                    //size

                                    List<String> images = new ArrayList<>();
                                    JSONArray imagesArray = productObj.getJSONArray("imagesURLs");
                                    for(int j=0;j<imagesArray.length();j++)
                                    {
                                        images.add(j,imagesArray.getString(j));
                                    }
                                    product.setImagesURLs(images);

                                    product.setProductTitle(productObj.getString("productTitle"));
                                    product.setManufacturer(productObj.getString("manufacturer"));
                                    product.setCategory(productObj.getString("category"));
                                    product.setActive(productObj.getBoolean("isActive"));

                                    if(product.getActive())
                                        productsActive.add(product);
                                    else
                                        productsInactive.add(product);

                                }

                                dismissProgressDialog();

                                if(whichFragment == 0) {
                                    adapter = new ProductAdapter(getContext(), productsActive, whichFragment);
                                    if(productsActive.size() == 0) {
                                        (rootView.findViewById(R.id.no_result_image)).setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.GONE);
                                    }
                                    else {
                                        (rootView.findViewById(R.id.no_result_image)).setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                    }
                                }
                                else {
                                    adapter = new ProductAdapter(getContext(), productsInactive, whichFragment);
                                    if(productsInactive.size() == 0) {
                                        (rootView.findViewById(R.id.no_result_image)).setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.GONE);
                                    }
                                    else {
                                        (rootView.findViewById(R.id.no_result_image)).setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                    }
                                }

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(adapter);

                                if(adapter!=null)
                                {
                                    adapter.SetOnItemClickListener(new OnItemClickListener() {

                                        @Override
                                        public void onItemClick(View v , int position, ProductModel p) {
                                            Intent intent = new Intent(getActivity(), ProductDescriptionActivity.class);
                                            intent.putExtra("ProductObject", p);
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
