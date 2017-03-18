package com.adurcup.adurcupseller.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.activity.AddProductActivity;
import com.adurcup.adurcupseller.misc.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class PickupScheduleFragment extends FormFragment {

    private TextInputLayout pickup_address, pin, concerned_name, weight_of_package, number_of_products, secondary_mobile_number, primary_mobile_number;
    private EditText date;
    private TextHandler primaryMobileHandler,secondaryMobileHandler,addresssHandler,concernedNameHandler,pinHandler,weightHandler,numberProductsHandler;
    private VolleyController volleyController;
    private final String TAG = "PickupSchedule";

    public static FormFragment newInstance() {
        return new PickupScheduleFragment();
    }

    @Override
    public void onPause() {
        super.onPause();

        primaryMobileHandler.onPause();
        secondaryMobileHandler.onPause();
        addresssHandler.onPause();
        concernedNameHandler.onPause();
        pinHandler.onPause();
        weightHandler.onPause();
        numberProductsHandler.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        volleyController.cancelPendingRequests(TAG);
    }

    @Override
    public void onResume() {
        super.onResume();

        final EditText pMobile = primary_mobile_number.getEditText();
        if (pMobile != null) {
            pMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        String value = pMobile.getText().toString();
                        if (value.length() == 0) {
                            primary_mobile_number.setError(getString(R.string.empty_necessary_field));
                        } else if (value.matches("^(((\\+)?91)?|(0)?)([6-9])[0-9]{9}(?!\\d)")) {
                            primary_mobile_number.setErrorEnabled(false);
                        } else {
                            if (!value.matches("^(\\+)?[0-9]+")) {
                                primary_mobile_number.setError(getString(R.string.allowed_digit_waring));
                            } else if (value.length() < 13) {
                                primary_mobile_number.setError(getString(R.string.incomplete_mobile_number));
                            } else {
                                primary_mobile_number.setError(getString(R.string.check_mobile_number));
                            }
                        }
                    }
                }
            });
        }

        final EditText sMobile = secondary_mobile_number.getEditText();
        if (sMobile != null) {
            sMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        String value = sMobile.getText().toString();
                        if (value.length() != 0) {
                            if (value.matches("^(((\\+)?91)?|(0)?)([6-9])[0-9]{9}(?!\\d)")) {
                                secondary_mobile_number.setErrorEnabled(false);
                            } else {
                                if (!value.matches("^(\\+)?[0-9]+")) {
                                    secondary_mobile_number.setError(getString(R.string.allowed_digit_waring));
                                } else if (value.length() < 13 && value.length()!=0) {
                                    secondary_mobile_number.setError(getString(R.string.incomplete_mobile_number));
                                } else {
                                    secondary_mobile_number.setError(getString(R.string.check_mobile_number));
                                }
                            }
                        }
                    }
                }
            });
        }

        primaryMobileHandler = new TextHandler(primary_mobile_number, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else if (value.matches("^(((\\+)?91)?|(0)?)([6-9])[0-9]{9}(?!\\d)")) {
                    textInputLayout.setErrorEnabled(false);
                } else {
                    if (!value.matches("^(\\+)?[0-9]+")) {
                        textInputLayout.setError(getString(R.string.allowed_digit_waring));
                    } else {

                        textInputLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        secondaryMobileHandler = new TextHandler(secondary_mobile_number, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {

                if(value.length() !=0) {

                    if (value.matches("^(((\\+)?91)?|(0)?)([6-9])[0-9]{9}(?!\\d)")) {
                        textInputLayout.setErrorEnabled(false);
                    } else {
                        if (!value.matches("^(\\+)?[0-9]+")) {
                            textInputLayout.setError(getString(R.string.allowed_digit_waring));
                        } else {
                            textInputLayout.setErrorEnabled(false);
                        }
                    }
                }
            }
        });


        concernedNameHandler = new TextHandler(concerned_name, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0)
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });


        addresssHandler = new TextHandler(pickup_address, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0)
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });

        pinHandler = new TextHandler(pin, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0)
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });


        weightHandler = new TextHandler(weight_of_package, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0)
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });


        numberProductsHandler = new TextHandler(number_of_products, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0)
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_schedule_pickup, container, false);

        date = (EditText) rootView.findViewById(R.id.choose_pickup_date);

        final Calendar myCalendar = Calendar.getInstance();

        volleyController = VolleyController.getInstance(getContext());

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                ((EditText) rootView.findViewById(R.id.choose_pickup_date)).setText(sdf.format(myCalendar.getTime()));
            }

        };

        ( rootView.findViewById(R.id.choose_pickup_date)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Button btSchedule = (Button) rootView.findViewById(R.id.schedule_pickup);
        pickup_address = (TextInputLayout) rootView.findViewById(R.id.pickup_address);

        pin = (TextInputLayout) rootView.findViewById(R.id.pin);
        primary_mobile_number = (TextInputLayout) rootView.findViewById(R.id.primary_mobile_number);
        secondary_mobile_number = (TextInputLayout) rootView.findViewById(R.id.secondary_mobile_number);

        concerned_name = (TextInputLayout) rootView.findViewById(R.id.concerned_name);

        weight_of_package = (TextInputLayout) rootView.findViewById(R.id.weight_of_package);

        number_of_products = (TextInputLayout) rootView.findViewById(R.id.number_of_products);


        btSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyBoard(v);

                if (addresssHandler.getValue() != null && concernedNameHandler.getValue() != null && numberProductsHandler.getValue() != null
                        && pinHandler.getValue() != null && primaryMobileHandler.getValue() != null && weightHandler.getValue() != null
                        && secondaryMobileHandler.getValue() != null)
                {

                    showProgressDialog("Scheduling");


                    final JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST,
                            getString(R.string.url) + getString(R.string.url_pickups), setJSONObject(),
                            new Response.Listener<JSONObject>() {
                                @Override

                                public void onResponse(JSONObject response) {

                                    dismissProgressDialog();

                                    if (response != null) {
                                        try {
                                            boolean error = response.getBoolean("error");
                                            String message = response.getString("message");

                                            if (error) {
                                                Toast.makeText(getContext(), "Oops, Something went wrong!", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                                ((AddProductActivity) getActivity()).switchToNext();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                                            Toast.makeText(getContext(),
                                                    "Json parsing error: " + e.getMessage(),
                                                    Toast.LENGTH_LONG)
                                                    .show();
                                        }

                                    } else {
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
                                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                        @Override
                        public Priority getPriority() {
                            return Priority.HIGH;
                        }
                    };

                    strReq.setShouldCache(false);
                    volleyController.addToRequestQueue(strReq, TAG);

                }
                else {
                    Toast.makeText(getContext(), R.string.enter_required_fields_correctly,
                            Toast.LENGTH_SHORT).show();
                }
            }

        });


        return rootView;

    }


    private JSONObject setJSONObject()
    {
        JSONObject request = new JSONObject();
        try
        {
            request.put("pickupAddress", pickup_address.getEditText().getText() );
            request.put("pincode", pin.getEditText().getText());
            request.put("concernedPerson", concerned_name.getEditText().getText() );
            request.put("primaryMobile",primary_mobile_number.getEditText().getText());
            if(secondary_mobile_number.getEditText() == null)
                request.put("secondaryMobile", null);
            else
                request.put("secondaryMobile", secondary_mobile_number.getEditText().getText());
            request.put("productQuantity", number_of_products.getEditText().getText() + " units");
            request.put("productWeight", weight_of_package.getEditText().getText() + " kgs");
            request.put("pickupDate", date.getText());
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }

        return request;
    }


    @Override
    boolean isErrorActive() {
        return false;
    }

}

