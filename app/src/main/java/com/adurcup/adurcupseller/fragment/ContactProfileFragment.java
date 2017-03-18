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

public class ContactProfileFragment extends FormFragment {

    public static FormFragment newInstance() {
        return new ContactProfileFragment();
    }

    @Override
    boolean isErrorActive() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_contact_profile, container, false);

        final RegistrationFormModel r = RegistrationFormModel.getInstance();

        ((TextView) rootView.findViewById(R.id.concerned_name_profile)).setText(r.getConcernPersonFirstName() + " " + r.getConcernPersonSecondName());
        ((TextView) rootView.findViewById(R.id.e_mail_address_profile)).setText(r.getConcernPersonEmail());
        ((TextView) rootView.findViewById(R.id.age_profile)).setText(r.getConcernPersonAge());
        ((TextView) rootView.findViewById(R.id.gender_profile)).setText(r.getConcernPersonGender());
        ((TextView) rootView.findViewById(R.id.nationality_profile)).setText(r.getConcernPersonNationality());
        ((TextView) rootView.findViewById(R.id.primary_phone_number_profile)).setText(r.getConcernPersonPrimaryNumber());
        ((TextView) rootView.findViewById(R.id.secondary_phone_number_profile)).setText(r.getConcernPersonSecondaryNumber());
        ((TextView) rootView.findViewById(R.id.pick_up_address_profile)).setText(r.getRegisterAddress2());


        (rootView.findViewById(R.id.edit_details_contact)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((Button)rootView.findViewById(R.id.edit_details_contact)).getText().equals("Edit Details")) {

                    ((Button) rootView.findViewById(R.id.edit_details_contact)).setText("Update Details");
                    (rootView.findViewById(R.id.concerned_name_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.concerned_name_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.e_mail_address_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.e_mail_address_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.age_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.age_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.gender_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.gender_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.nationality_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.nationality_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.primary_phone_number_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.primary_phone_number_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.secondary_phone_number_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.secondary_phone_number_profile)).setFocusableInTouchMode(true);

                    (rootView.findViewById(R.id.pick_up_address_profile)).setEnabled(true);
                    (rootView.findViewById(R.id.pick_up_address_profile)).setFocusableInTouchMode(true);

                }
                else
                {
                    String name =((EditText) rootView.findViewById(R.id.concerned_name_profile)).getText().toString();
                    String[] splited = name.split("\\s+");
                    r.setConcernPersonFirstName(splited[0]);
                    r.setConcernPersonSecondName(splited[1]);

                    r.setConcernPersonEmail(((EditText) rootView.findViewById(R.id.e_mail_address_profile)).getText().toString());
                    r.setConcernPersonAge(((EditText) rootView.findViewById(R.id.age_profile)).getText().toString());
                    r.setConcernPersonGender(((EditText) rootView.findViewById(R.id.gender_profile)).getText().toString());
                    r.setConcernPersonNationality(((EditText) rootView.findViewById(R.id.nationality_profile)).getText().toString());
                    r.setConcernPersonPrimaryNumber(((EditText) rootView.findViewById(R.id.primary_phone_number_profile)).getText().toString());
                    r.setConcernPersonSecondaryNumber(((EditText) rootView.findViewById(R.id.secondary_phone_number_profile)).getText().toString());

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
