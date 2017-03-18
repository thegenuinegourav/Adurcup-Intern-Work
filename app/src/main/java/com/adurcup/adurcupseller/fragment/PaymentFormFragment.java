package com.adurcup.adurcupseller.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.OnFormDataChangeListener;
import com.adurcup.adurcupseller.misc.RegistrationFormModel;


public class PaymentFormFragment extends FormFragment {

    private OnFormDataChangeListener onFormDataChangeListener;

    public static FormFragment newInstance (OnFormDataChangeListener callback) {
        FormFragment fragment =  new PaymentFormFragment();
        fragment.onFormDataChangeListener = callback;
        return fragment;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_form, container, false);
        tilBankName = (TextInputLayout) view.findViewById(R.id.bankNameInput);
        tilAccountNumber = (TextInputLayout) view.findViewById(R.id.accountNumberInput);
        tilBankAddress = (TextInputLayout) view.findViewById(R.id.bankAddressInput);
        tilPinCode = (TextInputLayout) view.findViewById(R.id.pinCodeInput);
        tilCity = (TextInputLayout) view.findViewById(R.id.cityInput);
        tilState = (TextInputLayout) view.findViewById(R.id.stateInput);
        tilChequeInFavour = (TextInputLayout) view.findViewById(R.id.chequeInFavourInput);
        tilMicr = (TextInputLayout) view.findViewById(R.id.micrInput);
        tilIFSC = (TextInputLayout) view.findViewById(R.id.ifscInput);
        tilBankShortingCode = (TextInputLayout) view.findViewById(R.id.bankShortingCodeInput);
        tilAuthorisedSignatory = (TextInputLayout) view.findViewById(R.id.authorisedSignatoryInput);
        tilDesignation = (TextInputLayout) view.findViewById(R.id.designationInput);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        thBankName = new TextHandler(tilBankName, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
        thAccountNumber = new TextHandler(tilAccountNumber, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
        thBankAddress = new TextHandler(tilBankAddress, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
        thPinCode = new TextHandler(tilPinCode, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
        thCity = new TextHandler(tilCity, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
        thState = new TextHandler(tilState, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
        thChequeInFavour = new TextHandler(tilChequeInFavour, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
        thMicr = new TextHandler(tilMicr, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
        thIfsc = new TextHandler(tilIFSC, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
        thBankShortingCode = new TextHandler(tilBankShortingCode, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
        thAuthorisedSignatory = new TextHandler(tilAuthorisedSignatory, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
        thDesignation = new TextHandler(tilDesignation, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });
    }

    private TextInputLayout tilBankName, tilAccountNumber,
            tilBankAddress, tilPinCode, tilCity, tilState,
            tilChequeInFavour, tilMicr, tilIFSC, tilBankShortingCode,
            tilAuthorisedSignatory, tilDesignation;
    private TextHandler thBankName, thAccountNumber,
            thBankAddress, thPinCode, thCity, thState,
            thChequeInFavour, thMicr, thIfsc, thBankShortingCode,
            thAuthorisedSignatory, thDesignation;

    @Override
    public void onPause() {
        super.onPause();
        thBankName.onPause();
        thAccountNumber.onPause();
        thBankAddress.onPause();
        thPinCode.onPause();
        thCity.onPause();
        thState.onPause();
        thChequeInFavour.onPause();
        thMicr.onPause();
        thIfsc.onPause();
        thBankShortingCode.onPause();
        thAuthorisedSignatory.onPause();
        thDesignation.onPause();
    }

    @Override
    public boolean isErrorActive() {
        if (thBankName.getValue() == null || thAccountNumber.getValue() == null
                || thBankAddress.getValue() == null || thPinCode.getValue() == null
                || thCity.getValue() == null || thState.getValue() == null
                || thChequeInFavour.getValue() == null || thMicr.getValue() == null
                || thIfsc.getValue() == null || thBankShortingCode.getValue() == null
                || thAuthorisedSignatory.getValue() == null || thDesignation.getValue() == null) {
            return true;
        } else {
            RegistrationFormModel registrationFormModel;
            if (onFormDataChangeListener.getFormData() != null) {
                registrationFormModel = onFormDataChangeListener.getFormData();
            } else {
                registrationFormModel = new RegistrationFormModel();
            }

            registrationFormModel.setBankName(thBankName.getValue());
            registrationFormModel.setBankAccountNo(thAccountNumber.getValue());
            registrationFormModel.setBankAddress(thBankAddress.getValue());
            registrationFormModel.setBankPinCode(thPinCode.getValue());
            registrationFormModel.setBankCity(thCity.getValue());
            registrationFormModel.setBankState(thState.getValue());
            registrationFormModel.setBankChequeInFavourOf(thChequeInFavour.getValue());
            registrationFormModel.setMicrCode(thMicr.getValue());
            registrationFormModel.setIfscCode(thIfsc.getValue());
            registrationFormModel.setBankSortCode(thBankShortingCode.getValue());
            String name = thAuthorisedSignatory.getValue();
            if (name.contains(" ")) {
                int i =  name.indexOf(" ");
                if (i < name.length()) {
                    registrationFormModel.setAuthorizedBankPersonFirstName(name.substring(0, i));
                    registrationFormModel.setAuthorizedBankPersonSecondName(name.substring(i));
                } else {
                    registrationFormModel.setAuthorizedBankPersonFirstName(name);
                    registrationFormModel.setAuthorizedBankPersonSecondName("-");
                }
            } else {
                registrationFormModel.setAuthorizedBankPersonFirstName(name);
                registrationFormModel.setAuthorizedBankPersonSecondName("-");
            }
            registrationFormModel.setAuthorizedBankPersonDesignation(thDesignation.getValue());
            onFormDataChangeListener.onFormDataChange(registrationFormModel);
            return false;
        }
    }
}
