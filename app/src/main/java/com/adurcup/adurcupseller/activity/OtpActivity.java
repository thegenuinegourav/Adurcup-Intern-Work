package com.adurcup.adurcupseller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.adurcup.adurcupseller.misc.RegistrationDetail;
import com.adurcup.adurcupseller.misc.SmsReceiver;
import com.adurcup.adurcupseller.misc.UserDetail;
import com.adurcup.adurcupseller.misc.UserLocalDatabase;
import com.adurcup.adurcupseller.misc.VolleyController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kshivang on 18/12/16.
 *
 */

public class OtpActivity extends FormActivity {

    private UserLocalDatabase localDatabase;
    private Button btSubmit;
    private TextInputLayout tilOTP;
    private TextHandler otpHandler;
    private VolleyController volleyController;
    private final String TAG = RegistrationActivity.class.getSimpleName();
    private ProgressBar progressBar;
    private TextView tvResendOTP;
    private TextView tvCountDown;
    private CountDownTimer countDownTimer;
    private BroadcastReceiver otpReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String otp = intent.getStringExtra(getString(R.string.key_otp));

            EditText etOtp = tilOTP.getEditText();
            if (etOtp != null) {
                etOtp.setText(otp);
            }
            tilOTP.setErrorEnabled(false);
            submit(null);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_otp);
        localDatabase = new UserLocalDatabase(this);
        btSubmit = (Button) findViewById(R.id.submit);
        progressBar = (ProgressBar) findViewById(android.R.id.progress);
        tilOTP = (TextInputLayout) findViewById(R.id.otpInput);
        tvResendOTP = (TextView) findViewById(R.id.resendOTP);
        tvCountDown = (TextView) findViewById(R.id.count_down);

        volleyController = VolleyController.getInstance(OtpActivity.this);
    }

    @Override
    public void onResume() {
        super.onResume();

        final EditText etOTP;
        etOTP = tilOTP.getEditText();
        if (etOTP != null) {
            etOTP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    String value = etOTP.getText().toString();
                    if (value.length() == 0) {
                        tilOTP.setErrorEnabled(true);
                        tilOTP.setError(getString(R.string.enter_otp_field_here));
                    } else if(value.length() < 6) {
                        tilOTP.setErrorEnabled(true);
                        tilOTP.setError(getString(R.string.otp_linting_error));
                    } else {
                        tilOTP.setErrorEnabled(false);
                    }
                }
            });
            etOTP.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_DONE) {
//                        String value = etOTP.getText().toString();
//                        if (value.length() == 0) {
//                            tilOTP.setErrorEnabled(true);
//                            tilOTP.setError(getString(R.string.enter_otp_field_here));
//                            Toast.makeText(OtpActivity.this,
//                                    R.string.enter_otp_field_here, Toast.LENGTH_SHORT).show();
//                        } else if(value.length() < 6) {
//                            tilOTP.setErrorEnabled(true);
//                            tilOTP.setError(getString(R.string.otp_linting_error));
//                            Toast.makeText(OtpActivity.this,
//                                    R.string.otp_linting_error, Toast.LENGTH_SHORT).show();
//                        } else {
//                            tilOTP.setErrorEnabled(false);
                            submit(btSubmit);
//                        }
                    }
                    return false;
                }
            });
        }
        otpHandler = new TextHandler(tilOTP, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    if (value.length() == 0) {
                        tilOTP.setErrorEnabled(true);
                        tilOTP.setError(getString(R.string.enter_otp_field_here));
                        Toast.makeText(OtpActivity.this,
                                R.string.enter_otp_field_here, Toast.LENGTH_SHORT).show();
                    } else if (value.length() > 6) {
                        tilOTP.setErrorEnabled(true);
                        tilOTP.setError(getString(R.string.otp_cannt_exceed_limit_length));
                    } else {
                        tilOTP.setErrorEnabled(false);
                    }
                }
            }
        });


        registerReceiver(otpReceiver, new IntentFilter(SmsReceiver.class.getSimpleName()));
        onCountDown();
    }

    @Override
    public void onPause() {
        super.onPause();
        otpHandler.onPause();
        if (countDownTimer != null)
            countDownTimer.cancel();
        unregisterReceiver(otpReceiver);
    }

    @Override
    public void onStop() {
        super.onStop();
        volleyController.cancelPendingRequests(TAG);
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localDatabase.clearOTPRequest();
    }

    private void onCountDown() {

        Long requestTime;
        RegistrationDetail registrationDetail = localDatabase.getRegistrationDetail();
        if (registrationDetail != null && registrationDetail.getRequestTime() != null) {
            requestTime = registrationDetail.getRequestTime();
        } else {
            requestTime = 0L;
        }

        Long sinceRequest = Calendar.getInstance().getTimeInMillis() - requestTime;
        if (sinceRequest < 60000) {
            tvResendOTP.setVisibility(View.INVISIBLE);
            countDownTimer = new CountDownTimer(60000 - sinceRequest, 1000) {
                public void onTick(long millisUntilFinished) {
                    tvCountDown.setText(String.valueOf(millisUntilFinished / 1000));
                }

                public void onFinish() {
                    tvCountDown.setText("0");
                    tvResendOTP.setVisibility(View.VISIBLE);
                }
            };
            countDownTimer.start();
        } else {
            tvResendOTP.setVisibility(View.VISIBLE);
        }
    }

    public void backLogin(View view) {
        hideKeyBoard(view);
        onBackPressed();
    }

    public void submit(View view) {
        hideKeyBoard(view);
        RegistrationDetail registrationDetail = localDatabase.getRegistrationDetail();
        String otp = otpHandler.getValue();
        if (otp != null) {
            if (registrationDetail.isForgotPassword()) {
                verifyToken(otp);
            } else {
                String registrationKey = registrationDetail.getRegistrationKey();
                verifyOTP(registrationKey, otp);
            }
        }
    }

    public void resendOTP(View view) {
        hideKeyBoard(view);
        RegistrationDetail registrationDetail = localDatabase.getRegistrationDetail();
        if (registrationDetail.isForgotPassword()) {
            tokenResend(registrationDetail.getMobile());
        } else
            otpResend();
    }

    private void verifyAuth(final String registrationToken) {
        final StringRequest request = new StringRequest(Request.Method.POST,
                getString(R.string.url) + getString(R.string.url_registration_auth),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        btSubmit.setVisibility(View.VISIBLE);
                        Gson gson = new Gson();
                        try {
                            UserDetail userDetail = gson.fromJson(response, UserDetail.class);
                            localDatabase.login(userDetail);
                            startActivity(new Intent(OtpActivity.this, BusinessUpdateStatusActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            Toast.makeText(OtpActivity.this, R.string.welcome
                                    , Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(OtpActivity.this,
                                    R.string.parse_error, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        OtpActivity.super.onRequestError(error, progressBar, btSubmit);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(getString(R.string.key_usertype), getString(R.string.key_vendor));
                params.put(getString(R.string.key_temp_user_id_hash), registrationToken);
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

    private void verifyOTP(final String registrationToken, final String otp) {
        progressBar.setVisibility(View.VISIBLE);
        btSubmit.setVisibility(View.GONE);
        StringRequest request = new StringRequest(Request.Method.POST,
                getString(R.string.url) + getString(R.string.url_otp_confirmation) + registrationToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String message = trimMessage(response, getString(R.string.key_message));
                        if (message != null) {
                            Toast.makeText(OtpActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                        verifyAuth(registrationToken);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        OtpActivity.super.onRequestError(error, progressBar, btSubmit);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(getString(R.string.key_otp), otp);
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

    private void otpResend() {
        final RegistrationDetail registrationDetail = localDatabase.getRegistrationDetail();
        progressBar.setVisibility(View.VISIBLE);
        btSubmit.setVisibility(View.GONE);
        final StringRequest request = new StringRequest(
                Request.Method.POST, getString(R.string.url) + getString(R.string.url_register),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        btSubmit.setVisibility(View.VISIBLE);
                        String registerKey = trimMessage(response,
                                getString(R.string.key_idhash));
                        Toast.makeText(OtpActivity.this,
                                trimMessage(response, "message"), Toast.LENGTH_SHORT).show();
                        registrationDetail.setRegistrationKey(registerKey);
                        registrationDetail.setRequestTime(Calendar.getInstance()
                                .getTimeInMillis());
                        localDatabase.waitingOTP(registrationDetail);
                        onCountDown();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        OtpActivity.super.onRequestError(error, progressBar, btSubmit);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();

                params.put(getString(R.string.key_name), registrationDetail.getName());
                params.put(getString(R.string.key_phone), registrationDetail.getMobile());
                params.put(getString(R.string.key_password), registrationDetail.getPassword());
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


    private void verifyToken(final String token) {
        progressBar.setVisibility(View.VISIBLE);
        btSubmit.setVisibility(View.GONE);
        StringRequest request = new StringRequest(
                Request.Method.GET, getString(R.string.url) + getString(R.string.url_reset_password) + token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        btSubmit.setVisibility(View.VISIBLE);
                        String message = trimMessage(response, getString(R.string.key_message));
                        if (message != null) {
                            Toast.makeText(OtpActivity.this, message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(OtpActivity.this,
                                    ChangePasswordActivity.class)
                                    .putExtra(getString(R.string.key_otp), token));
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        OtpActivity.super.onRequestError(error, progressBar, btSubmit);
                    }
                }
        ){
            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }
        };

        request.setShouldCache(false);
        volleyController.addToRequestQueue(request, TAG);
    }

    private void tokenResend(final String phone) {
        progressBar.setVisibility(View.VISIBLE);
        btSubmit.setVisibility(View.GONE);
        StringRequest request = new StringRequest(
                Request.Method.POST,getString(R.string.url) +  getString(R.string.url_change_password),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        btSubmit.setVisibility(View.VISIBLE);
                        String message = trimMessage(response, "message");
                        if (message != null) {
                            Toast.makeText(OtpActivity.this, message,
                                    Toast.LENGTH_SHORT).show();
                        }
                        RegistrationDetail registrationDetail = new RegistrationDetail();
                        registrationDetail.setForgotPassword(true);
                        registrationDetail.setRequestTime(Calendar.getInstance().getTimeInMillis());
                        localDatabase.waitingOTP(registrationDetail);
                        onCountDown();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        OtpActivity.super.onRequestError(error,
                                progressBar, btSubmit);
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
}
