package com.adurcup.adurcupseller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.RegistrationDetail;
import com.adurcup.adurcupseller.misc.UserLocalDatabase;
import com.adurcup.adurcupseller.misc.VolleyController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kshivang on 16/12/16.
 *
 */

public class ForgotPasswordActivity extends FormActivity {

    private Button btChangePassword;
    private TextInputLayout tilMobile;
    private TextHandler mobileHandler;
    private ProgressBar progressBar;
    private final String TAG = ForgotPasswordActivity.class.getSimpleName();
    private VolleyController volleyController;
    private UserLocalDatabase localDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);
        btChangePassword = (Button) findViewById(R.id.change_password);
        tilMobile = (TextInputLayout) findViewById(R.id.primaryNumberInput);
        progressBar = (ProgressBar) findViewById(android.R.id.progress);

        volleyController = VolleyController.getInstance(ForgotPasswordActivity.this);
        localDatabase = new UserLocalDatabase(this);
        getSMSPermission(findViewById(R.id.llBase));
    }

    @Override
    public void onResume() {
        super.onResume();

        final EditText etMobile;
        etMobile = tilMobile.getEditText();
        if (etMobile != null) {
            etMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    String value = etMobile.getText().toString();
                    if (value.length() == 0) {
                        tilMobile.setErrorEnabled(true);
                        tilMobile.setError(getString(R.string.empty_necessary_field));
                        tilMobile.setError(value);
                    } else if (value.matches("^(((\\+)?91)?|(0)?)([6-9])[0-9]{9}(?!\\d)")) {
                        tilMobile.setErrorEnabled(false);
                    } else {
                        tilMobile.setErrorEnabled(true);
                        if (!value.matches("^(\\+)?[0-9]+")) {
                            tilMobile.setError(
                                    getString(R.string.allowed_digit_waring));
                        } else if (value.length() < 13) {
                            tilMobile.setError(getString(R.string.incomplete_mobile_number));
                        } else {
                            tilMobile.setError(getString(R.string.check_mobile_number));
                        }
                    }
                }
            });
            etMobile.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_DONE) {
                        changePassword(null);
                    }
                    return false;
                }
            });
        }

        mobileHandler = new TextHandler(tilMobile, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else if (value.matches("^(((\\+)?91)?|(0)?)([6-9])[0-9]{9}(?!\\d)")) {
                    textInputLayout.setErrorEnabled(false);
                } else {
                    if (!value.matches("^(\\+)?[0-9]+")) {
                        textInputLayout.setErrorEnabled(true);
                        textInputLayout.setError(getString(R.string.allowed_digit_waring));
                    }
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mobileHandler.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        volleyController.cancelPendingRequests(TAG);
    }

    private void verifyPhone(final String phone) {
        progressBar.setVisibility(View.VISIBLE);
        btChangePassword.setVisibility(View.GONE);
        StringRequest request = new StringRequest(
                Request.Method.POST, getString(R.string.url) + getString(R.string.url_change_password),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        btChangePassword.setVisibility(View.VISIBLE);
                        String message = trimMessage(response, "message");
                        if (message != null) {
                            Toast.makeText(ForgotPasswordActivity.this, message,
                                    Toast.LENGTH_SHORT).show();
                        }
                        RegistrationDetail registrationDetail = new RegistrationDetail();
                        registrationDetail.setForgotPassword(true);
                        registrationDetail.setMobile(phone);
                        registrationDetail.setRequestTime(Calendar.getInstance().getTimeInMillis());
                        localDatabase.waitingOTP(registrationDetail);
                        startActivity(new Intent(ForgotPasswordActivity.this, OtpActivity.class));
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ForgotPasswordActivity.super.onRequestError(error,
                                progressBar, btChangePassword);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(getString(R.string.key_phone), phone);
                return params;
            }

            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };
        request.setShouldCache(false);
        volleyController.addToRequestQueue(request, TAG);
    }

    public void changePassword(View view) {
        hideKeyBoard(view);
        if (mobileHandler.getValue() != null) {
            String MobileNum = mobileHandler.getValue();
            MobileNum = "+91-" + MobileNum.substring(MobileNum.length() - 10);
            verifyPhone(MobileNum);
        }
    }

    public void backLogin(View view) {
        onBackPressed();
    }
}
