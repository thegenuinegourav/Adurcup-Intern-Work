package com.adurcup.adurcupseller.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.fragment.CompanyProfileFragment;
import com.adurcup.adurcupseller.fragment.ContactProfileFragment;
import com.adurcup.adurcupseller.fragment.PaymentProfileFragment;
import com.adurcup.adurcupseller.misc.RegistrationFormModel;
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

import static android.content.ContentValues.TAG;

public class VendorDetailProfileActivity extends FormActivity {

    private RegistrationFormModel registrationFormModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_detail_profile);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        registrationFormModel = RegistrationFormModel.getInstance();

        VolleyController volleyController = VolleyController.getInstance(this);

        (findViewById(R.id.pager)).setVisibility(View.GONE);

        showProgressDialog("Fetching");

        final StringRequest request = new StringRequest(
                Request.Method.GET, getString(R.string.url) + getString(R.string.url_vendor_business),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String stResponse) {
                        if(stResponse != null)
                        {

                            try {

                                JSONArray jsonArray = new JSONArray(stResponse);
                                for(int i=0; i<jsonArray.length(); i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    registrationFormModel.setBusiness_Id(jsonObject.getString("_id"));
                                    registrationFormModel.setBusinessName(jsonObject.getString("businessName"));
                                    registrationFormModel.setLegalStatus(jsonObject.getString("legalStatus"));

                                    JSONArray businessType = jsonObject.getJSONArray("businessType");
                                    List<String> busType = new ArrayList<>();
                                    for(int j=0; j<businessType.length(); j++)
                                    {
                                        busType.add(j,businessType.getString(j));
                                    }
                                    registrationFormModel.setBusinessType(busType);

                                    JSONArray BusinessPhone = jsonObject.getJSONArray("phone");
                                    registrationFormModel.setBusinessPrimaryNumber(BusinessPhone.getString(0));
                                    registrationFormModel.setBusinessSecondaryNumber(BusinessPhone.getString(1));

                                    registrationFormModel.setEmail(jsonObject.getString("email"));
                                    registrationFormModel.setPanNumber(jsonObject.getString("PANno"));
                                    registrationFormModel.setVatNumber(jsonObject.getString("VATno"));
                                    registrationFormModel.setFaxNumber(jsonObject.getString("faxNo"));
                                    registrationFormModel.setServiceTaxCodeNumber(jsonObject.getString("serviceTaxCodeNo"));
                                    registrationFormModel.setServiceTaxRsgtNumber(jsonObject.getString("serviceTaxRsgtNo"));

                                    JSONArray pickUpAddress = jsonObject.getJSONArray("pickUpAddress");
                                    JSONObject addressObject = pickUpAddress.getJSONObject(0);
                                    String address = addressObject.getString("AddressLine1") +  "\n" + addressObject.getString("city") + ", " + addressObject.getString("state") + ", " + addressObject.getString("pincode");
                                    registrationFormModel.setRegisterAddress(address);

                                    JSONObject addressObject2 = pickUpAddress.getJSONObject(pickUpAddress.length()-1);
                                    String address2 = addressObject2.getString("AddressLine1") +  "\n" + addressObject2.getString("city") + ", " + addressObject2.getString("state") + ", " + addressObject2.getString("pincode");
                                    registrationFormModel.setRegisterAddress2(address2);

                                    for(int j=0; j< pickUpAddress.length(); j++)
                                    {
                                        JSONObject addressObj = pickUpAddress.getJSONObject(j);
                                        registrationFormModel.setAddressName(addressObj.getString("name"),j);
                                        registrationFormModel.setAddressLine1(addressObj.getString("AddressLine1"),j);
                                        registrationFormModel.setAddressCity(addressObj.getString("city"),j);
                                        registrationFormModel.setAddressState(addressObj.getString("state"),j);
                                        registrationFormModel.setAddressPinCode(addressObj.getString("pincode"),j);
                                        registrationFormModel.setAddressPhone(addressObj.getString("phone"));
                                        registrationFormModel.setRegisteredAddress(addressObj.getBoolean("registeredAddress"),j);
                                    }


                                    JSONObject concernPerson = jsonObject.getJSONObject("concernPerson");
                                    registrationFormModel.setConcernPersonFirstName(concernPerson.getString("firstName"));
                                    registrationFormModel.setConcernPersonSecondName(concernPerson.getString("lastName"));
                                    registrationFormModel.setConcernPersonEmail(concernPerson.getString("email"));
                                    registrationFormModel.setConcernPersonAge(concernPerson.getString("age"));
                                    registrationFormModel.setConcernPersonGender(concernPerson.getString("gender"));
                                    registrationFormModel.setConcernPersonNationality(concernPerson.getString("nationality"));

                                    JSONArray phone = concernPerson.getJSONArray("phone");
                                    registrationFormModel.setConcernPersonPrimaryNumber(phone.getString(0));
                                    registrationFormModel.setConcernPersonSecondaryNumber(phone.getString(1));



                                    JSONObject bankDetails = jsonObject.getJSONObject("bankDetails");
                                    registrationFormModel.setBankName(bankDetails.getString("bankName"));
                                    registrationFormModel.setBankAccountNo(bankDetails.getString("accountNo"));
                                    registrationFormModel.setBankChequeInFavourOf(bankDetails.getString("chequeInFavorOf"));
                                    registrationFormModel.setIfscCode(bankDetails.getString("ifscCode"));
                                    registrationFormModel.setMicrCode(bankDetails.getString("micrCode"));
                                    registrationFormModel.setBankSortCode(bankDetails.getString("bankSortCode"));
                                    registrationFormModel.setBankAddress(bankDetails.getString("address"));
                                    registrationFormModel.setBankCity(bankDetails.getString("city"));
                                    registrationFormModel.setBankPinCode(bankDetails.getString("pincode"));
                                    registrationFormModel.setBankState(bankDetails.getString("state"));

                                    String bankaddress = bankDetails.getString("address") + "\n" + bankDetails.getString("city") + ", " + bankDetails.getString("state") + ", " + bankDetails.getString("pincode");
                                    registrationFormModel.setBank_Address(bankaddress);

                                    JSONObject authorizedBankPerson = jsonObject.getJSONObject("authorizedBankPerson");
                                    registrationFormModel.setAuthorizedBankPersonFirstName(authorizedBankPerson.getString("firstName"));
                                    registrationFormModel.setAuthorizedBankPersonSecondName(authorizedBankPerson.getString("lastName"));
                                    registrationFormModel.setAuthorizedBankPersonDesignation(authorizedBankPerson.getString("designation"));
                                }


                                (findViewById(R.id.pager)).setVisibility(View.VISIBLE);
                                dismissProgressDialog();


                            } catch (final JSONException e) {
                                (findViewById(R.id.pager)).setVisibility(View.GONE);
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                                dismissProgressDialog();
                            }
                        } else {
                            (findViewById(R.id.pager)).setVisibility(View.GONE);
                            Log.e(TAG, "Couldn't get json from server.");
                            Toast.makeText(getApplicationContext(),
                                    "Couldn't get json from server. Check LogCat for possible errors!",
                                    Toast.LENGTH_LONG)
                                    .show();
                            dismissProgressDialog();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        (findViewById(R.id.pager)).setVisibility(View.GONE);
                        dismissProgressDialog();
                        Toast.makeText(getApplicationContext(),
                                "Oops, Something went wrong!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

        volleyController.addToRequestQueue(request, TAG);


        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int index) {
                switch (index) {
                    case 0:
                        return CompanyProfileFragment.newInstance();
                    case 1:
                        return ContactProfileFragment.newInstance();
                    default:
                        return PaymentProfileFragment.newInstance();
                }

            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Company";
                    case 1:
                        return "Contact";
                    default:
                        return "Payment";
                }

            }
        };


        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

    }
}