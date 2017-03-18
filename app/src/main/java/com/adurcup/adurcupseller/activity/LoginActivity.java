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
import com.google.gson.Gson;
import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.UserDetail;
import com.adurcup.adurcupseller.misc.UserLocalDatabase;
import com.adurcup.adurcupseller.misc.VolleyController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kshivang on 16/12/16.
 *
 */

public class LoginActivity extends FormActivity {

    private Button btLogin;
    private TextInputLayout tilMobile, tilPassword;
    private TextHandler mobileHandler, passwordHandler;
    private ProgressBar progressBar;
    private UserLocalDatabase localDatabase;
    private final String TAG = LoginActivity.class.getSimpleName();
    private VolleyController volleyController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        btLogin = (Button) findViewById(R.id.login);
        tilMobile = (TextInputLayout) findViewById(R.id.primaryNumberInput);
        tilPassword = (TextInputLayout) findViewById(R.id.passwordInput);
        progressBar = (ProgressBar) findViewById(android.R.id.progress);

        localDatabase = new UserLocalDatabase(LoginActivity.this);
        volleyController = VolleyController.getInstance(LoginActivity.this);
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

        }

        EditText etPassword = tilPassword.getEditText();
        if (etPassword != null) {
            etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_DONE) {
                        login(btLogin);
                        return true;
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

        passwordHandler = new TextHandler(tilPassword, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {

                if (value.length() == 0) {
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mobileHandler.onPause();
        passwordHandler.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        volleyController.cancelPendingRequests(TAG);
    }

//    @Override
//    public void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        if (tilPassword != null && tilPassword.getEditText() != null)
//            tilPassword.getEditText().setText(null);
//        if (tilMobile != null && tilMobile.getEditText() != null)
//            tilMobile.getEditText().setText(null);
//    }

    public void signIn(final String mobile, final String password) {
        progressBar.setVisibility(View.VISIBLE);
        btLogin.setVisibility(View.GONE);
        StringRequest request = new StringRequest(
                Request.Method.POST, getString(R.string.url) + getString(R.string.url_signin),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String stResponse) {
                progressBar.setVisibility(View.GONE);
                btLogin.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                try {
                    UserDetail userDetail = gson.fromJson(stResponse, UserDetail.class);
                    localDatabase.login(userDetail);
                    startActivity(new Intent(LoginActivity.this, BusinessUpdateStatusActivity.class));
                    Toast.makeText(LoginActivity.this, getString(R.string.welcome),
                            Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, R.string.parse_error,
                            Toast.LENGTH_SHORT).show();
                }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LoginActivity.super.onRequestError(error, progressBar, btLogin);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put(getString(R.string.key_username), mobile);
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

    public void login(View view) {
        hideKeyBoard(view);
        if (mobileHandler.getValue() != null &&
                passwordHandler.getValue() != null){
            String MobileNum = mobileHandler.getValue();
            MobileNum = "+91-" + MobileNum.substring(MobileNum.length() - 10);
            signIn(MobileNum, passwordHandler.getValue());
        } else {
            Toast.makeText(LoginActivity.this,
                    R.string.warning_check_email_password,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void forgotPassword(View view) {
        hideKeyBoard(view);
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }

    public void signUp(View view) {
        hideKeyBoard(view);
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }
}
