package com.adurcup.adurcupseller.misc;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.activity.AddProductActivity;
import com.adurcup.adurcupseller.activity.FormActivity;
import com.adurcup.adurcupseller.activity.NavigationActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class UpdateBusinessProfile extends FormActivity {

    private Context context;
    private RegistrationFormModel r;

    public UpdateBusinessProfile(Context applicationContext, RegistrationFormModel r){
        super();
        this.context = applicationContext;
        this.r = r;
    }

    public JSONObject parseJson() {
        JSONObject body = new JSONObject();
        try {
            body.put("businessName", r.getBusinessName());

            JSONArray phone = new JSONArray();
            phone.put(0, r.getBusinessPrimaryNumber());
            phone.put(1, r.getBusinessSecondaryNumber());
            body.put("phone", phone);

            body.put("email", r.getEmail());
            body.put("PANno", r.getPanNumber());
            body.put("faxNo", r.getFaxNumber());
            body.put("VATno", r.getVatNumber());
            body.put("serviceTaxRsgNo", r.getServiceTaxRsgtNumber());
            body.put("serviceTaxCodeNo", r.getServiceTaxCodeNumber());
            body.put("legalStatus", r.getLegalStatus());

            JSONObject concernPerson = new JSONObject();
            concernPerson.put("firstName", r.getConcernPersonFirstName());
            concernPerson.put("lastName", r.getConcernPersonSecondName());
            concernPerson.put("email", r.getConcernPersonEmail());
            concernPerson.put("age", r.getConcernPersonAge());
            concernPerson.put("gender", r.getConcernPersonGender());
            concernPerson.put("nationality", r.getConcernPersonNationality());

            JSONArray concernPersonPhone = new JSONArray();
            concernPersonPhone.put(0, r.getConcernPersonPrimaryNumber());
            concernPersonPhone.put(1, r.getConcernPersonSecondaryNumber());
            concernPerson.put("phone", concernPersonPhone);

            body.put("concernPerson", concernPerson);

            JSONObject bankDetail = new JSONObject();
            bankDetail.put("bankName", r.getBankName());
            bankDetail.put("accountNo", r.getBankAccountNo());
            bankDetail.put("address", r.getBankAddress());
            bankDetail.put("city", r.getBankCity());
            bankDetail.put("state", r.getBankState());
            bankDetail.put("pincode", r.getBankPinCode());
            bankDetail.put("chequeInFavorOf", r.getBankChequeInFavourOf());
            bankDetail.put("ifscCode", r.getIfscCode());
            bankDetail.put("micrCode", r.getMicrCode());
            bankDetail.put("bankSortCode", r.getBankSortCode());

            body.put("bankDetail", bankDetail);

            JSONObject authorizedBankPerson = new JSONObject();
            authorizedBankPerson.put("firstName", r.getAuthorizedBankPersonFirstName());
            authorizedBankPerson.put("lastName", r.getAuthorizedBankPersonSecondName());
            authorizedBankPerson.put("designation", r.getAuthorizedBankPersonDesignation());

            body.put("authorizedBankPerson", authorizedBankPerson);
            List<String> businessTypes =  r.getBusinessType();
            JSONArray businessType = new JSONArray();
            for (int i = 0; i < businessTypes.size(); i++) {
                businessType.put(i, businessTypes.get(i));
            }
            body.put("businessType", businessType);

            JSONArray address = new JSONArray();
            for (int i = 0; i < r.getAddressCity().size(); i++) {
                JSONObject object = new JSONObject();
                object.put("name", r.getAddressName().get(i));
                object.put("AddressLine1", r.getAddressLine1().get(i));
                object.put("city", r.getAddressCity().get(i));
                object.put("state", r.getAddressState().get(i));
                object.put("pincode", r.getAddressPinCode().get(i));
                object.put("phone", r.getAddressPhone().get(i));
                object.put("registeredAddress", r.getRegisteredAddress());
                address.put(i, object);
            }
            body.put("address", address);
            return body;
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error passing json: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(context, "Error passing json", Toast.LENGTH_SHORT).show();
        return null;
    }
}
