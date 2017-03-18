package com.adurcup.adurcupseller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.VolleyController;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import static android.content.ContentValues.TAG;


public class ProfileActivity extends FormActivity {

    private VolleyController volleyController;
    private LinearLayout profile_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        volleyController = VolleyController.getInstance(getApplicationContext());
        profile_layout = (LinearLayout)findViewById(R.id.profile_layout);

        showProgressDialog("Fetching");

        StringRequest request = new StringRequest(
                Request.Method.GET, getString(R.string.url) + getString(R.string.url_profile_me),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String stResponse) {
                        if(stResponse != null)
                        {

                            try {
                                JSONObject jsonObj = new JSONObject(stResponse);

                                String phone = jsonObj.getString("phone");
                                String bussiness_name = jsonObj.getString("businessName");

                                ((TextView)findViewById(R.id.mobile_number)).setText(phone);
                                ((TextView)findViewById(R.id.company_name_title)).setText(bussiness_name);

                                profile_layout.setVisibility(View.VISIBLE);
                                dismissProgressDialog();


                            } catch (final JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        } else {
                            Log.e(TAG, "Couldn't get json from server.");
                            Toast.makeText(getApplicationContext(),
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
                        Toast.makeText(getApplicationContext(),
                                "Oops, Something went wrong!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

        volleyController.addToRequestQueue(request, TAG);

        ( findViewById(R.id.change_password_click)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Change Password
            }
        });

    }

    public void LogOut(View view) {;
        //Logout
    }

    public void BussinessDetails(View view) {
        startActivity(new Intent(this,VendorDetailProfileActivity.class));
    }
}
