package com.adurcup.adurcupseller.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.gson.Gson;
import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.UserDetail;
import com.adurcup.adurcupseller.misc.UserLocalDatabase;
import com.adurcup.adurcupseller.misc.VolleyController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kshivang on 21/12/16.
 *
 */

public class ChangePasswordActivity extends FormActivity {

    private Button btChangePassword;
    private String token;
    private TextInputLayout tilPassword, tilConfirmPassword;
    private TextHandler passwordHandler, confirmPasswordHandler;
    private ProgressBar progressBar;
    private final String TAG =  ChangePasswordActivity.class.getSimpleName();
    private VolleyController volleyController;
    private UserLocalDatabase localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_change_password);
        btChangePassword = (Button) findViewById(R.id.change_password);
        tilConfirmPassword = (TextInputLayout) findViewById(R.id.confirmPasswordInput);
        tilPassword = (TextInputLayout) findViewById(R.id.passwordInput);
        progressBar = (ProgressBar) findViewById(android.R.id.progress);

        token = getIntent().getStringExtra(getString(R.string.key_otp));

        volleyController = VolleyController.getInstance(ChangePasswordActivity.this);
        localDatabase = new UserLocalDatabase(this);
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
                        changePassword(btChangePassword);
                        return true;
                    }
                    return false;
                }
            });
        }

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
                } else if (value.length() < passwordHandler.getValue().length()
                        && !passwordHandler.getValue().contains(value)) {
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
        passwordHandler.onPause();
        confirmPasswordHandler.onPause();
    }

    private void passwordChange(final String newPassword, final String confirmPassword) {
        progressBar.setVisibility(View.VISIBLE);
        btChangePassword.setVisibility(View.GONE);
        StringRequest request = new StringRequest(
                Request.Method.POST, getString(R.string.url) + getString(R.string.url_reset_password) + token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        btChangePassword.setVisibility(View.VISIBLE);
                        Gson gson = new Gson();
                        try {
                            UserDetail userDetail = gson.fromJson(response, UserDetail.class);
                            localDatabase.login(userDetail);
                            startActivity(new Intent(ChangePasswordActivity.this,
                                    BusinessUpdateStatusActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            Toast.makeText(ChangePasswordActivity.this,
                                    getString(R.string.welcome),
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(ChangePasswordActivity.this, R.string.parse_error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ChangePasswordActivity.super
                                .onRequestError(error, progressBar, btChangePassword);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(getString(R.string.key_new_password), newPassword);
                params.put(getString(R.string.key_verify_password), confirmPassword);

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
        if (passwordHandler.getValue() != null &&
                confirmPasswordHandler.getValue() != null) {
            if (passwordHandler.getValue().equals(confirmPasswordHandler.getValue())) {
                passwordChange(passwordHandler.getValue(), confirmPasswordHandler.getValue());
            } else {
                Toast.makeText(this, R.string.warning_confirm_password,
                        Toast.LENGTH_SHORT).show();
                tilConfirmPassword.setErrorEnabled(true);
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
