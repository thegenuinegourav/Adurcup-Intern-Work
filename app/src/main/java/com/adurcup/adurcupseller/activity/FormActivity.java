package com.adurcup.adurcupseller.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.UserLocalDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kshivang on 18/12/16.
 *
 */

public class FormActivity extends AppCompatActivity {

    public void hideKeyBoard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private ProgressDialog progressDialog;

    class TextHandler {
        private String value;
        private TextInputLayout textInputLayout;
        private TextWatcher textWatcher;
        private EditText editText;
        private OnCheckListener mOnCheckListener;

        TextHandler(final TextInputLayout textInputLayout, OnCheckListener OnCheckListener) {
            this.mOnCheckListener = OnCheckListener;

            this.textInputLayout = textInputLayout;
            this.editText = textInputLayout.getEditText();

            if (editText != null) {
                textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence,
                                                  int i, int i1, int i2) {
                        value = editText.getText().toString();
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        value = editText.getText().toString();
                        mOnCheckListener.onCheck(textInputLayout, value);

                    }
                };

                editText.addTextChangedListener(textWatcher);
            }
        }

        String getValue() {
            EditText editText = textInputLayout.getEditText();
            if (editText != null) {
                value = editText.getText().toString();
            }
            if (textInputLayout.isErrorEnabled())
                return null;
            return value;
        }

        void onPause() {
            if (textWatcher != null)
                editText.removeTextChangedListener(textWatcher);
        }
    }

    public interface OnCheckListener {
        void onCheck(TextInputLayout textInputLayout, String value);
    }

    public void showProgressDialog(String text) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(text);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public String trimMessage(String json, String key){
        String trimmedString;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    public void onRequestError (VolleyError error, ProgressBar progressBar, Button button) {
        dismissProgressDialog();
        String json;
        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            json = new String(response.data);
            json = trimMessage(json, getString(R.string.key_message));
            if (json != null) {
                Toast.makeText(FormActivity.this, json,
                        Toast.LENGTH_SHORT).show();
                if (json.equals("User is not authorized")){
                    UserLocalDatabase localDatabase = new UserLocalDatabase(this);
                    localDatabase.logout();
                    startActivity(new Intent(FormActivity.this, LoginActivity.class));
                    finish();
                }
            }
        } else if (response != null) {
            Toast.makeText(FormActivity.this,
                    R.string.server_error, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(FormActivity.this, R.string.check_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }

        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        if (button != null)
            button.setVisibility(View.VISIBLE);
    }

    public Boolean getSMSPermission(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS) !=
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_SMS) !=
                        PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECEIVE_SMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_SMS)) {
                Snackbar.make(view, "Permission required to auto detect OTP",
                        Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(FormActivity.this,
                                new String[]{Manifest.permission.RECEIVE_SMS,
                                        Manifest.permission.READ_SMS,
                                }, 1);
                    }
                }).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECEIVE_SMS,
                                Manifest.permission.READ_SMS}, 1);
            }
            return false;
        }
        return true;
    }
}
