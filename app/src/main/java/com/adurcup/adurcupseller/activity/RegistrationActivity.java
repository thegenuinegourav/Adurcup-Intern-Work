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

public class RegistrationActivity extends FormActivity {

    private Button btRegister;
    private TextInputLayout tilName, tilConfirmPassword, tilMobile, tilPassword;
    private TextHandler nameHandler, mobileHandler, passwordHandler, confirmPasswordHandler;
    private ProgressBar progressBar;
    private UserLocalDatabase LocalDatabase;
    private final String TAG = RegistrationActivity.class.getSimpleName();
    private VolleyController volleyController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

        btRegister = (Button) findViewById(R.id.register);
        tilName = (TextInputLayout) findViewById(R.id.nameInput);
        tilMobile = (TextInputLayout) findViewById(R.id.primaryNumberInput);
        tilPassword = (TextInputLayout) findViewById(R.id.passwordInput);
        tilConfirmPassword = (TextInputLayout) findViewById(R.id.confirmPasswordInput);
        progressBar = (ProgressBar) findViewById(android.R.id.progress);

        LocalDatabase = new UserLocalDatabase(RegistrationActivity.this);
        volleyController = VolleyController.getInstance(RegistrationActivity.this);
        getSMSPermission(findViewById(R.id.llBase));
    }

    @Override
    public void onResume() {
        super.onResume();
        EditText etConfirmPassword = tilConfirmPassword.getEditText();
        if (etConfirmPassword != null) {
            etConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_DONE) {
                        register(btRegister);
                        return true;
                    }
                    return false;
                }
            });
        }


        final EditText etMobile = tilMobile.getEditText();
        if (etMobile != null) {
            etMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        String value = etMobile.getText().toString();
                        if (value.length() == 0) {
                            tilMobile.setError(getString(R.string.empty_necessary_field));
                        } else if (value.matches("^(((\\+)?91)?|(0)?)([6-9])[0-9]{9}(?!\\d)")) {
                            tilMobile.setErrorEnabled(false);
                        } else {
                            if (!value.matches("^(\\+)?[0-9]+")) {
                                tilMobile.setError(getString(R.string.allowed_digit_waring));
                            } else if (value.length() < 13) {
                                tilMobile.setError(getString(R.string.incomplete_mobile_number));
                            } else {
                                tilMobile.setError(getString(R.string.check_mobile_number));
                            }
                        }
                    }
                }
            });
        }

        nameHandler = new TextHandler(tilName, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0)
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });

        mobileHandler = new TextHandler(tilMobile, new OnCheckListener() {
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

        passwordHandler = new TextHandler(tilPassword, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else if (value.length() < 6) {
                    textInputLayout.setError(getString(R.string.minimum_character));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });

        confirmPasswordHandler = new TextHandler(tilConfirmPassword, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else if (passwordHandler.getValue() == null) {
                    textInputLayout.setError(getString(R.string.minimum_character));
                } else if (!value.equals(passwordHandler.getValue()) ||
                        (value.length() < passwordHandler.getValue().length()
                        && !passwordHandler.getValue().contains(value))) {
                    textInputLayout
                            .setError(getString(R.string.same_confirm_password));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        nameHandler.onPause();
        mobileHandler.onPause();
        passwordHandler.onPause();
        confirmPasswordHandler.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        volleyController.cancelPendingRequests(TAG);
    }

    public void signUp(final String name, final String mobile, final String password) {
        progressBar.setVisibility(View.VISIBLE);
        btRegister.setVisibility(View.GONE);
        StringRequest request = new StringRequest(
                Request.Method.POST, getString(R.string.url) + getString(R.string.url_register),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        btRegister.setVisibility(View.VISIBLE);
                        String registerKey = trimMessage(response,
                                getString(R.string.key_idhash));
                        RegistrationDetail registrationDetail =
                                new RegistrationDetail();
                        registrationDetail.setRegistrationKey(registerKey);
                        registrationDetail.setName(name);
                        registrationDetail.setMobile(mobile);
                        registrationDetail.setPassword(password);
                        registrationDetail.setForgotPassword(false);
                        registrationDetail.setRequestTime(Calendar
                                .getInstance().getTimeInMillis());
                        LocalDatabase.waitingOTP(registrationDetail);
                        startActivity(new Intent(RegistrationActivity.this,
                                OtpActivity.class));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                RegistrationActivity.super.onRequestError(error, progressBar, btRegister);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(getString(R.string.key_name), name);
                params.put(getString(R.string.key_phone), mobile);
                params.put(getString(R.string.key_password), password);
                params.put(getString(R.string.key_usertype), getString(R.string.key_vendor));

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

    public void register(View view) {
        hideKeyBoard(view);
        if (nameHandler.getValue() != null &&
                mobileHandler.getValue() != null &&
                passwordHandler.getValue() != null &&
                confirmPasswordHandler.getValue() != null) {
            if (passwordHandler.getValue()
                    .equals(confirmPasswordHandler.getValue())) {
                String MobileNum = mobileHandler.getValue();
                MobileNum = "+91-" + MobileNum.substring(MobileNum.length() - 10);
                signUp(nameHandler.getValue(),
                        MobileNum,
                        passwordHandler.getValue());
            } else {
                Toast.makeText(this, R.string.warning_confirm_password,
                        Toast.LENGTH_SHORT).show();
                tilConfirmPassword.setError(getString(R.string.same_confirm_password));
            }
        } else {
            Toast.makeText(this, R.string.enter_required_fields_correctly,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void backLogin(View view) {
        onBackPressed();
    }
}
