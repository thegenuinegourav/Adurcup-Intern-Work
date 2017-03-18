package com.adurcup.adurcupseller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.fragment.VendorDetailFormFragment;
import com.adurcup.adurcupseller.misc.VolleyController;


public class BusinessUpdateStatusActivity extends FormActivity{

    private VolleyController volleyController;
    private final String TAG = "busi";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_appbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        volleyController = VolleyController.getInstance(this);

        isRegistrationDetailPending();
    }

    @Override
    public void onStop() {
        super.onStop();
        volleyController.cancelPendingRequests(TAG);
    }

    private void isRegistrationDetailPending() {
        showProgressDialog("Loading..");

        StringRequest request = new StringRequest(Request.Method.GET,
                getString(R.string.url) + getString(R.string.url_vendor_business),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                if (response.length() > 2) {
                    startActivity(new Intent(BusinessUpdateStatusActivity.this,
                            NavigationActivity.class));
                    finish();
                } else {
                    FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                    tx.replace(R.id.fragment, VendorDetailFormFragment.newInstance());
                    tx.commit();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onRequestError(error, null, null);
            }
        }){
            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        request.setShouldCache(false);
        volleyController.addToRequestQueue(request, TAG);
    }
}
