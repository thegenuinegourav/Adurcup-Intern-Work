package com.adurcup.adurcupseller.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
import com.adurcup.adurcupseller.activity.LoginActivity;
import com.adurcup.adurcupseller.misc.OnFormDataChangeListener;
import com.adurcup.adurcupseller.misc.UserLocalDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class FormFragment extends Fragment{

    private ProgressDialog progressDialog;

    OnFormDataChangeListener onFormDataChangeListener;
    abstract boolean isErrorActive();

    void hideKeyBoard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)getContext()
                    .getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    class TextHandler {
        private String value;
        private TextInputLayout textInputLayout;
        private TextWatcher textWatcher;
        private EditText editText;
        private OnCheckListener mOnCheckListener;

        TextHandler(final TextInputLayout textInputLayout, OnCheckListener onCheckListener) {
            this.mOnCheckListener = onCheckListener;

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
            if (textInputLayout.isErrorEnabled() || value == null ||value.length() == 0)
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

    public Context getContext() {
        if (super.getContext() != null) {
            return super.getContext();
        }
        Fragment parent = getParentFragment();
        int i = 0;
        while (i < 10) {
            if (parent.getContext() != null) return parent.getContext();
            parent = getParentFragment();
            i++;
        }
        return null;
    }

    void showProgressDialog(String text) {
        Context context = getContext();
        if (context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(text);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private String trimMessage(String json, String key){
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
                Toast.makeText(getContext(), json,
                        Toast.LENGTH_SHORT).show();
                if (json.equals("User is not authorized")){
                    UserLocalDatabase localDatabase = new UserLocalDatabase(getContext());
                    localDatabase.logout();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        } else if (response != null) {
            Toast.makeText(getContext(),
                    R.string.server_error, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), R.string.check_internet_connection,
                    Toast.LENGTH_SHORT).show();
        }

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (button != null) {
            button.setVisibility(View.VISIBLE);
        }
    }
}
