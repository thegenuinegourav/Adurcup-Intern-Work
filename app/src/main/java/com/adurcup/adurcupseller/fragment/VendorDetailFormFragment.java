package com.adurcup.adurcupseller.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.activity.NavigationActivity;
import com.adurcup.adurcupseller.misc.CustomViewPager;
import com.adurcup.adurcupseller.misc.OnFormDataChangeListener;
import com.adurcup.adurcupseller.misc.RegistrationFormModel;
import com.adurcup.adurcupseller.misc.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;
import static com.adurcup.adurcupseller.R.id.container;

/**
 * Created by kshivang on 22/12/16.
 *
 */

public class VendorDetailFormFragment extends FormFragment
        implements OnFormDataChangeListener {

    private static final int MAX_FRAGMENT = 4;
    private RegistrationFormModel formData;
    private List<FormFragment> formFragments = new ArrayList<>();
    public static Fragment newInstance() {
        return new VendorDetailFormFragment();
    }
    private VolleyController volleyController;
    private final String TAG = "Ve";

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        volleyController = VolleyController.getInstance(getContext());

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.business_profile);
        }
        formFragments.add(ServiceFormFragment
                .newInstance(VendorDetailFormFragment.this));
        formFragments.add(CompanyFormFragment
                .newInstance(VendorDetailFormFragment.this));
        formFragments.add(ContactFormFragment
                .newInstance(VendorDetailFormFragment.this));
        formFragments.add(PaymentFormFragment
                .newInstance(VendorDetailFormFragment.this));
        formData = new RegistrationFormModel();
        pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                btPrevious.setVisibility(position > 0 ? VISIBLE : View.INVISIBLE);
                btNext.setText(position < MAX_FRAGMENT - 1 ? getString(R.string.next):
                        getString(R.string.done));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        return inflater.inflate(R.layout.fragment_vendor_detail_form, container, false);

    }

    private Button btNext, btPrevious;
    private CustomViewPager pager;
    private ViewPager.OnPageChangeListener pageChangeListener;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager = (CustomViewPager) view.findViewById(container);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        btNext = (Button) view.findViewById(R.id.next_button);
        btPrevious = (Button) view.findViewById(R.id.prev_button);

        final FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return formFragments.get(position);
            }

            @Override
            public int getCount() {
                return MAX_FRAGMENT;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0: return getString(R.string.service);
                    case 1: return getString(R.string.company);
                    case 2: return getString(R.string.contact);
                    default: return getString(R.string.payment);
                }
            }
        };
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }

        pager.addOnPageChangeListener(pageChangeListener);
        pager.setPagingEnabled(false);

        btPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pager.getCurrentItem() > 0)
                    pager.setCurrentItem(pager.getCurrentItem() - 1, true);
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = pager.getCurrentItem();
                if (currentPage < MAX_FRAGMENT - 1) {
                    if ((formFragments.get(currentPage)).isErrorActive()) {
                        switch (currentPage) {
                            case 0:
                                Toast.makeText(getContext(), R.string.choose_service,
                                        Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getContext(), R.string.fill_required_field,
                                        Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pager.setCurrentItem(currentPage + 1, true);
                    }
                } else {
                    onSubmit();
                }
            }
        });
    }

    @Override
    public void onFormDataChange(RegistrationFormModel formData) {
        this.formData = formData;
    }

    @Override
    public RegistrationFormModel getFormData() {
        return formData;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pager.removeOnPageChangeListener(pageChangeListener);
        volleyController.cancelPendingRequests(TAG);
    }

    private ProgressDialog progress;
    private void onSubmit() {
        progress = new ProgressDialog(getContext());
        progress.setMessage("Updating..");
        progress.setCancelable(false);
        progress.show();

        btNext.setVisibility(View.VISIBLE);
        btPrevious.setVisibility(View.VISIBLE);
        JSONObject body = parseJson();
        if (body != null) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    getString(R.string.url) + getString(R.string.url_vendor_business), body,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progress.dismiss();
                            startActivity(new Intent(getContext(), NavigationActivity.class));
                            getActivity().finish();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress.dismiss();
                    btPrevious.setVisibility(VISIBLE);
                    onRequestError(error, null, btNext);
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

    private JSONObject parseJson() {
        JSONObject body = new JSONObject();
        try {
            body.put("businessName", formData.getBusinessName());

            JSONArray phone = new JSONArray();
            phone.put(0,"+91-" + formData.getBusinessPrimaryNumber());
            phone.put(1,"+91-" + formData.getBusinessSecondaryNumber());
            body.put("phone", phone);

            body.put("email", formData.getEmail());
            body.put("PANno", formData.getPanNumber());
            body.put("faxNo", "-");
            body.put("VATno", formData.getVatNumber());
            body.put("serviceTaxRsgNo", formData.getServiceTaxRsgtNumber());
            body.put("serviceTaxCodeNo", formData.getServiceTaxCodeNumber());
            body.put("legalStatus", formData.getLegalStatus());

            JSONObject concernPerson = new JSONObject();
            concernPerson.put("firstName", formData.getConcernPersonFirstName());
            concernPerson.put("lastName", formData.getConcernPersonSecondName());
            concernPerson.put("email", formData.getConcernPersonEmail());
            concernPerson.put("age", formData.getConcernPersonAge());
            concernPerson.put("gender", formData.getConcernPersonGender());
            concernPerson.put("nationality", formData.getConcernPersonNationality());

            JSONArray concernPersonPhone = new JSONArray();
            concernPersonPhone.put(0,"+91-" +  formData.getConcernPersonPrimaryNumber());
            concernPersonPhone.put(1,"+91-" +  formData.getConcernPersonSecondaryNumber());
            concernPerson.put("phone", concernPersonPhone);

            body.put("concernPerson", concernPerson);

            JSONObject bankDetail = new JSONObject();
            bankDetail.put("bankName", formData.getBankName());
            bankDetail.put("accountNo", formData.getBankAccountNo());
            bankDetail.put("address", formData.getBankAddress());
            bankDetail.put("city", formData.getBankCity());
            bankDetail.put("state", formData.getBankState());
            bankDetail.put("pincode", formData.getBankPinCode());
            bankDetail.put("chequeInFavorOf", formData.getBankChequeInFavourOf());
            bankDetail.put("ifscCode", formData.getIfscCode());
            bankDetail.put("micrCode", formData.getMicrCode());
            bankDetail.put("bankSortCode", formData.getBankSortCode());

            body.put("bankDetail", bankDetail);

            JSONObject authorizedBankPerson = new JSONObject();
            authorizedBankPerson.put("firstName", formData.getAuthorizedBankPersonFirstName());
            authorizedBankPerson.put("lastName", formData.getAuthorizedBankPersonSecondName());
            authorizedBankPerson.put("designation", formData.getAuthorizedBankPersonDesignation());

            body.put("authorizedBankPerson", authorizedBankPerson);

            List<String> businessTypes =  formData.getBusinessType();
            JSONArray businessType = new JSONArray();
            for (int i = 0; i < businessTypes.size(); i++) {
                businessType.put(i, businessTypes.get(i));
            }
            body.put("businessType", businessType);

            JSONArray address = new JSONArray();
            for (int i = 0; i < formData.getAddressCity().size(); i++) {
                JSONObject object = new JSONObject();
                object.put("name", formData.getAddressName().get(i));
                object.put("AddressLine1", formData.getAddressLine1().get(i));
                object.put("city", formData.getAddressCity().get(i));
                object.put("state", formData.getAddressState().get(i));
                object.put("pincode", formData.getAddressPinCode().get(i));
                object.put("registeredAddress", formData.getRegisteredAddress());
                object.put("phone","+91-" +  formData.getConcernPersonPrimaryNumber());
                address.put(i, object);
            }
            body.put("address", address);
            return body;
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error passing json: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        progress.dismiss();
        Toast.makeText(getContext(), "Error passing json", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    boolean isErrorActive() {
        return false;
    }
}
