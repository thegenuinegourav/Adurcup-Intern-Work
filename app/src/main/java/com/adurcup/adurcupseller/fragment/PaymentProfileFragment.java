package com.adurcup.adurcupseller.fragment;

import android.content.Intent;
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

public class PaymentProfileFragment extends FormFragment {

    public static FormFragment newInstance() {
        return new PaymentProfileFragment();
    }

    @Override
    boolean isErrorActive() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_payment_profile, container, false);

        final RegistrationFormModel r = RegistrationFormModel.getInstance();

        ((TextView) rootView.findViewById(R.id.bank_name_profile)).setText(r.getBankName());
        ((TextView) rootView.findViewById(R.id.account_number_profile)).setText(r.getBankAccountNo());
        ((TextView) rootView.findViewById(R.id.cheque_profile)).setText(r.getBankChequeInFavourOf());
        ((TextView) rootView.findViewById(R.id.ifsc_code_profile)).setText(r.getIfscCode());
        ((TextView) rootView.findViewById(R.id.micr_code_profile)).setText(r.getMicrCode());
        ((TextView) rootView.findViewById(R.id.bank_sort_code_profile)).setText(r.getBankSortCode());
        ((TextView) rootView.findViewById(R.id.bank_address_profile)).setText(r.getBank_Address());
        ((TextView) rootView.findViewById(R.id.authorized_person_profile)).setText(r.getAuthorizedBankPersonFirstName() + " " + r.getAuthorizedBankPersonSecondName());
        ((TextView) rootView.findViewById(R.id.designation_profile)).setText(r.getAuthorizedBankPersonDesignation());

        (rootView.findViewById(R.id.edit_details_payment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((Button)rootView.findViewById(R.id.edit_details_payment)).getText().equals("Edit Details")) {

                    ((Button) rootView.findViewById(R.id.edit_details_payment)).setText("Update Details");

                    (rootView.findViewById(R.id.authorized_person_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.authorized_person_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.designation_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.designation_profile)).setFocusableInTouchMode(true);

                }
                else
                {
                    String name =((EditText) rootView.findViewById(R.id.authorized_person_profile)).getText().toString();
                    String[] splited = name.split("\\s+");
                    r.setAuthorizedBankPersonFirstName(splited[0]);
                    r.setAuthorizedBankPersonSecondName(splited[1]);
                    r.setAuthorizedBankPersonDesignation(((EditText) rootView.findViewById(R.id.designation_profile)).getText().toString());

                    UpdateBusinessProfile updateBusinessProfile = new UpdateBusinessProfile(getActivity().getApplicationContext(),r);

                    VolleyController volleyController = VolleyController.getInstance(getContext());
                    JSONObject body = updateBusinessProfile.parseJson();
                    showProgressDialog("Updating");
                    if (body != null) {
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,
                                getString(R.string.url) + getString(R.string.url_vendor_business) +"/"
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
