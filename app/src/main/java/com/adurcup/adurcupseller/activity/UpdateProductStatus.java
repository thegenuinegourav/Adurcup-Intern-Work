package com.adurcup.adurcupseller.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.ProductModel;
import com.adurcup.adurcupseller.misc.VolleyController;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.adurcup.adurcupseller.R.id.increment_value_per;
import static com.adurcup.adurcupseller.R.id.mini;
import static com.adurcup.adurcupseller.R.id.packet_details_per;
import static com.adurcup.adurcupseller.R.id.product;
import static com.adurcup.adurcupseller.R.id.spinner;
import static com.adurcup.adurcupseller.R.string.address;
import static java.security.AccessController.getContext;

public class UpdateProductStatus extends FormActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private SwitchCompat switchCompat;
    private ProductModel product;
    private TextView price_per, minimum_order_quantity_per, increment_value_per, packet_details_per;
    private EditText packet_details, price, minimum_order_quantity, increment_value;
    private Button changes;
    private String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product_status);

        Intent i = getIntent();
        product = (ProductModel) i.getSerializableExtra("ProductObj");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(Typeface.DEFAULT_BOLD);
        mTitle.setText(product.getProductTitle());

        switchCompat = (SwitchCompat) findViewById(R.id
                .switch_compat);
        switchCompat.setSwitchPadding(40);
        switchCompat.setOnCheckedChangeListener(this);

        packet_details = (EditText) findViewById(R.id.packet_details);
        packet_details_per = (TextView) findViewById(R.id.packet_details_per);
        price = (EditText) findViewById(R.id.price_unit_description);
        minimum_order_quantity = (EditText) findViewById(R.id.minimum_order_quantity);
        increment_value = (EditText)findViewById(R.id.increment_value);
        price_per = (TextView) findViewById(R.id.price_unit_description_per);
        minimum_order_quantity_per = (TextView) findViewById(R.id.minimum_order_quantity_per);
        increment_value_per = (TextView) findViewById(R.id.increment_value_per);
        changes = (Button) findViewById(R.id.changes);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Per Piece");
        categories.add("Per Packet");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        if(product.getNewPrice().size() != 0)
        {
            if(product.getUnitDescription().getPer().equals(""))
            {
                spinner.setSelection(0);
                packet_details.setVisibility(View.GONE);
                (findViewById(R.id.packet_details_label)).setVisibility(View.GONE);
                packet_details_per.setVisibility(View.GONE);
                price_per.setText("per piece");
                increment_value_per.setText("pieces");
                minimum_order_quantity_per.setText("pieces");
            }
            else
            {
                spinner.setSelection(1);
                packet_details.setVisibility(View.VISIBLE);
                (findViewById(R.id.packet_details_label)).setVisibility(View.VISIBLE);
                packet_details.setText(String.valueOf(product.getUnitDescription().getValue()));
                packet_details_per.setVisibility(View.VISIBLE);
                price_per.setText("per packet");
                increment_value_per.setText("packets");
                minimum_order_quantity_per.setText("packets");
            }
            price.setText(Double.toString(product.getNewPrice().get(0).getPrice()));
            minimum_order_quantity.setText(Integer.toString(product.getNewPrice().get(0).getMinQuantity()));
            increment_value.setText(Integer.toString(product.getNewPrice().get(0).getIncrementValue()));
            changes.setText("Update Changes");
        }
        else changes.setText("Save Changes");

        if(product.getActive())
            switchCompat.setChecked(true);
        else
            switchCompat.setChecked(false);


        changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkAllEditText())
                {
                    VolleyController volleyController = VolleyController.getInstance(getApplicationContext());
                    JSONObject body = parseJson();
                    showProgressDialog("Updating");
                    if (body != null) {
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,
                                getString(R.string.url) + getString(R.string.url_list_current_product) + "/"
                                        + product.get_id()
                                , body,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        dismissProgressDialog();
                                        Toast.makeText(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
                                        finish();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dismissProgressDialog();
                                Toast.makeText(getApplicationContext(), "Error passing json: "+ error, Toast.LENGTH_SHORT).show();
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


        (findViewById(R.id.increment_value_info)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(UpdateProductStatus.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_box_incremental_value);
                Button dialogButtonOk = (Button) dialog.findViewById(R.id.ok_got_it_button);
                dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public boolean checkAllEditText()
    {
        if(item.equals("Per Packet") && packet_details.getText().length()==0) {
            Toast.makeText(this, "Fill the packet details correctly!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(price.getText().length()==0)
        {
            Toast.makeText(this, "Fill the price correctly!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(minimum_order_quantity.getText().length()==0)
        {
            Toast.makeText(this, "Fill the MOQ correctly!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(increment_value.getText().length()==0)
        {
            Toast.makeText(this, "Fill the price correctly!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }

    public JSONObject parseJson() {
        JSONObject body = new JSONObject();
        try {
            JSONArray newPriceArray = new JSONArray();
            JSONObject newPriceObj = new JSONObject();

            newPriceObj.put("price",Double.parseDouble(price.getText().toString()));
            if(item.equals("Per Piece"))
                newPriceObj.put("unit","piece");
            else
                newPriceObj.put("unit","packet");
            newPriceObj.put("minQuantity",Integer.parseInt(minimum_order_quantity.getText().toString()));
            newPriceObj.put("incrementValue",Integer.parseInt(increment_value.getText().toString()));
            newPriceObj.put("maxQuantity",product.getNewPrice().get(0).getMaxQuantity());
            newPriceArray.put(0, newPriceObj);
            body.put("newPrice",newPriceArray);


            JSONObject unitDescription = new JSONObject();

            if(item.equals("Per Piece"))
            {
                unitDescription.put("per","");
                unitDescription.put("value",1);
            }

            else
            {
                unitDescription.put("per","packet");
                unitDescription.put("value",Integer.parseInt(packet_details.getText().toString()));
            }

            unitDescription.put("unit","piece");

            body.put("unitDescription",unitDescription);



            if(switchCompat.getText().equals("Active"))
                body.put("isActive", true);
            else
                body.put("isActive", false);

            return body;
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error passing json: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getApplicationContext(), "Error passing json", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        item = parent.getItemAtPosition(position).toString();

        if(item.equals("Per Piece"))
        {
            packet_details.setVisibility(View.GONE);
            (findViewById(R.id.packet_details_label)).setVisibility(View.GONE);
            packet_details_per.setVisibility(View.GONE);
            price_per.setText("per piece");
            increment_value_per.setText("pieces");
            minimum_order_quantity_per.setText("pieces");

        }
        else
        {
            packet_details.setVisibility(View.VISIBLE);
            (findViewById(R.id.packet_details_label)).setVisibility(View.VISIBLE);
            packet_details_per.setVisibility(View.VISIBLE);
            price_per.setText("per packet");
            increment_value_per.setText("packets");
            minimum_order_quantity_per.setText("packets");
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
            switchCompat.setText("Active");
        else
            switchCompat.setText("Inactive");
    }
}



