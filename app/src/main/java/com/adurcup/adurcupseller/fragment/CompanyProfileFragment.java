package com.adurcup.adurcupseller.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.activity.NavigationActivity;
import com.adurcup.adurcupseller.misc.RegistrationFormModel;
import com.adurcup.adurcupseller.misc.UpdateBusinessProfile;
import com.adurcup.adurcupseller.misc.VolleyController;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class CompanyProfileFragment extends FormFragment {

    public static FormFragment newInstance() {
        return new CompanyProfileFragment();
    }

    @Override
    boolean isErrorActive() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_company_profile, container, false);

        final RegistrationFormModel r = RegistrationFormModel.getInstance();

        ((TextView) rootView.findViewById(R.id.company_name_profile)).setText(r.getBusinessName());
        ((TextView) rootView.findViewById(R.id.legal_status_profile)).setText(r.getLegalStatus());
        ((TextView) rootView.findViewById(R.id.primary_mobile_number_profile)).setText(r.getBusinessPrimaryNumber());
        ((TextView) rootView.findViewById(R.id.secondary_mobile_number_profile)).setText(r.getBusinessSecondaryNumber());
        ((TextView) rootView.findViewById(R.id.email_address_profile)).setText(r.getEmail());
        ((TextView) rootView.findViewById(R.id.pan_number_profile)).setText(r.getPanNumber());
        ((TextView) rootView.findViewById(R.id.vat_number_profile)).setText(r.getVatNumber());
        ((TextView) rootView.findViewById(R.id.service_tax_code_number)).setText(r.getServiceTaxCodeNumber());
        ((TextView) rootView.findViewById(R.id.service_tax_registered_number_profile)).setText(r.getServiceTaxRsgtNumber());
        ((TextView) rootView.findViewById(R.id.registered_address_profile)).setText(r.getRegisterAddress());

        (rootView.findViewById(R.id.edit_details_company)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((Button)rootView.findViewById(R.id.edit_details_company)).getText().equals("Edit Details")) {

                    ((Button) rootView.findViewById(R.id.edit_details_company)).setText("Update Details");
                    (rootView.findViewById(R.id.company_name_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.company_name_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.primary_mobile_number_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.primary_mobile_number_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.secondary_mobile_number_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.secondary_mobile_number_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.email_address_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.email_address_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.pan_number_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.pan_number_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.vat_number_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.vat_number_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.service_tax_code_number)).setEnabled(true);
                    (rootView.findViewById(R.id.service_tax_code_number)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.service_tax_registered_number_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.service_tax_registered_number_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.registered_address_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.registered_address_profile)).setFocusableInTouchMode(true);

                }
                else
                {
                    r.setBusinessName(((EditText) rootView.findViewById(R.id.company_name_profile)).getText().toString());
                    r.setBusinessPrimaryNumber(((EditText) rootView.findViewById(R.id.primary_mobile_number_profile)).getText().toString());
                    r.setBusinessSecondaryNumber(((EditText) rootView.findViewById(R.id.secondary_mobile_number_profile)).getText().toString());
                    r.setEmail(((EditText) rootView.findViewById(R.id.email_address_profile)).getText().toString());
                    r.setPanNumber(((EditText) rootView.findViewById(R.id.pan_number_profile)).getText().toString());
                    r.setVatNumber(((EditText) rootView.findViewById(R.id.vat_number_profile)).getText().toString());
                    r.setServiceTaxCodeNumber(((EditText) rootView.findViewById(R.id.service_tax_code_number)).getText().toString());
                    r.setServiceTaxRsgtNumber(((EditText) rootView.findViewById(R.id.service_tax_registered_number_profile)).getText().toString());

                    UpdateBusinessProfile updateBusinessProfile = new UpdateBusinessProfile(getActivity().getApplicationContext(),r);

                    VolleyController volleyController = VolleyController.getInstance(getContext());
                    JSONObject body = updateBusinessProfile.parseJson();
                    showProgressDialog("Updating");
                    if (body != null) {
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,
                                getString(R.string.url) + getString(R.string.url_vendor_business) + "/"
                                        + r.getBusiness_Id()
                                , body,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        dismissProgressDialog();
                                        Toast.makeText(getContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getContext(), NavigationActivity.class));
                                        getActivity().finish();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dismissProgressDialog();
                                Toast.makeText(getContext(), "Error passing json: "+ error, Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            public Priority getPriority() {
                                return Priority.HIGH;
                            }
                        };
                        request.setShouldCache(false);
                        volleyController.addToRequestQueue(request, TAG);
                    }
                }

            }
        });

        return rootView;
    }
}
