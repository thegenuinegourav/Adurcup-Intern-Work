package com.adurcup.adurcupseller.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.OnFormDataChangeListener;
import com.adurcup.adurcupseller.misc.RegistrationFormModel;

public class CompanyFormFragment extends FormFragment{

    public static FormFragment newInstance(OnFormDataChangeListener callback) {
        FormFragment fragment =  new CompanyFormFragment();
        fragment.onFormDataChangeListener = callback;
        return fragment;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_form, container, false);
        spLegalStatus = (Spinner) view.findViewById(R.id.legal_status);
        tilCompanyName = (TextInputLayout) view.findViewById(R.id.companyNameInput);
        tilAddress = (TextInputLayout) view.findViewById(R.id.addressInput);
        tilPinCode = (TextInputLayout) view.findViewById(R.id.pinCodeInput);
        tilCity = (TextInputLayout) view.findViewById(R.id.cityInput);
        tilState = (TextInputLayout) view.findViewById(R.id.stateInput);
        tilPrimaryNumber = (TextInputLayout) view.findViewById(R.id.primaryNumberInput);
        tilSecondaryNumber = (TextInputLayout) view.findViewById(R.id.secondaryNumberInput);
        tilEmail = (TextInputLayout) view.findViewById(R.id.emailInput);
        tilPanNumber = (TextInputLayout) view.findViewById(R.id.panNumberInput);
        tilVatNumber = (TextInputLayout) view.findViewById(R.id.vatNumberInput);
        tilServiceTax = (TextInputLayout) view.findViewById(R.id.serviceTaxInput);
        tilServiceTaxCode = (TextInputLayout) view.findViewById(R.id.serviceTaxCodeInput);


        RegistrationFormModel registrationFormModel;
        if (onFormDataChangeListener.getFormData() != null) {
            registrationFormModel = onFormDataChangeListener.getFormData();
        } else {
            registrationFormModel = new RegistrationFormModel();
        }
        registrationFormModel.setLegalStatus("Individual");
//        registrationFormModel.put("legalStatus", getResources()
//                .getStringArray(R.array.legal_status)[0]);
        spLegalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                registrationFormModel.put("legalStatus", getResources()
//                        .getStringArray(R.array.legal_status)[i]);
                RegistrationFormModel registrationFormModel;
                if (onFormDataChangeListener.getFormData() != null) {
                    registrationFormModel = onFormDataChangeListener.getFormData();
                } else {
                    registrationFormModel = new RegistrationFormModel();
                }
                registrationFormModel.setLegalStatus(getResources()
                        .getStringArray(R.array.legal_status)[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        thCompanyName = new TextHandler(tilCompanyName, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
//                    registrationFormModel.put("businessName", "");
//                    address.put("name", "");
                } else {
                    textInputLayout.setErrorEnabled(false);
//                    registrationFormModel.put("businessName", value);
//                    address.put("name", value);

                }
            }
        });
        thAddress = new TextHandler(tilAddress, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
//                    address.put("AddressLine1", "");
                } else {
                    textInputLayout.setErrorEnabled(false);
//                    address.put("AddressLine1", value);

                }
            }
        });
        thCity = new TextHandler(tilCity, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
//                    address.put("city", "");
                } else {
                    textInputLayout.setErrorEnabled(false);
//                    address.put("city", value);

                }
            }
        });
        thState = new TextHandler(tilState, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
//                    address.put("state", "");
                } else {
                    textInputLayout.setErrorEnabled(false);

//                    address.put("state", value);
                }
            }
        });
        thPinCode = new TextHandler(tilPinCode, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
//                    address.put("pincode", "");
                } else {
                    textInputLayout.setErrorEnabled(false);

//                    address.put("pincode", value);
                }
            }
        });
        thPrimaryNumber = new TextHandler(tilPrimaryNumber, new OnCheckListener() {
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
                    } else {
                        textInputLayout.setErrorEnabled(false);

//                        phone[0] = "+91-" + value;
                    }
                }
            }
        });
        thSecondaryNumber = new TextHandler(tilSecondaryNumber, new OnCheckListener() {
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
                    } else {
                        textInputLayout.setErrorEnabled(false);

//                        phone[1] =  "+91-" + value;
                    }
                }
            }
        });
        thEmail = new TextHandler(tilEmail, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);

//                    registrationFormModel.put("email", value);
                }
            }
        });
        thPanNumber = new TextHandler(tilPanNumber, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);

//                    registrationFormModel.put("PANno", value);
                }
            }
        });
        thVatNumber = new TextHandler(tilVatNumber, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);

//                    registrationFormModel.put("VATno", value);
                }
            }
        });
        thServiceTax = new TextHandler(tilServiceTax, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);

//                    registrationFormModel.put("serviceTaxRsgtNo", value);
                }
            }
        });
        thServiceTaxCode = new TextHandler(tilServiceTaxCode, new OnCheckListener() {
            @Override
            public void onCheck(TextInputLayout textInputLayout, String value) {
                if (value.length() == 0) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.empty_necessary_field));
                } else {
                    textInputLayout.setErrorEnabled(false);

//                    registrationFormModel.put("serviceTaxCodeNo", value);
                }
            }
        });
    }

    private TextHandler thCompanyName, thAddress, thPinCode,
        thCity, thState, thPrimaryNumber, thSecondaryNumber, thEmail,
        thPanNumber, thVatNumber, thServiceTax, thServiceTaxCode;
    private TextInputLayout tilCompanyName, tilAddress,
            tilPinCode, tilCity, tilState, tilPrimaryNumber,
            tilSecondaryNumber, tilEmail, tilPanNumber,
            tilVatNumber, tilServiceTax, tilServiceTaxCode;
    private Spinner spLegalStatus;

    @Override
    public boolean isErrorActive() {
        if (thCompanyName.getValue() == null || thAddress.getValue() == null
                && thPinCode.getValue() == null || thCity.getValue() == null
                && thState.getValue() == null || thPrimaryNumber.getValue() == null
                && thSecondaryNumber.getValue() == null || thEmail.getValue() == null
                && thPanNumber.getValue() == null || thVatNumber.getValue() == null
                && thServiceTax.getValue() == null || thServiceTaxCode.getValue() == null) {
            return true;
        } else {
            RegistrationFormModel registrationFormModel;
            if (onFormDataChangeListener.getFormData() != null) {
                registrationFormModel = onFormDataChangeListener.getFormData();
            } else {
                registrationFormModel = new RegistrationFormModel();
            }

            registrationFormModel.setBusinessName(thCompanyName.getValue());
            registrationFormModel.setAddressName(thCompanyName.getValue(), 0);
            registrationFormModel.setAddressLine1(thAddress.getValue(), 0);
            registrationFormModel.setAddressCity(thCity.getValue(), 0);
            registrationFormModel.setAddressState(thState.getValue(), 0);
            registrationFormModel.setAddressPinCode(thPinCode.getValue(), 0);
            registrationFormModel.setRegisteredAddress(true, 0);
            registrationFormModel.setBusinessPrimaryNumber(thPrimaryNumber.getValue());
            registrationFormModel.setBusinessSecondaryNumber(thSecondaryNumber.getValue());
            registrationFormModel.setEmail(thEmail.getValue());
            registrationFormModel.setPanNumber(thPanNumber.getValue());
            registrationFormModel.setVatNumber(thVatNumber.getValue());
            registrationFormModel.setServiceTaxRsgtNumber(thServiceTax.getValue());
            registrationFormModel.setServiceTaxCodeNumber(thServiceTaxCode.getValue());
            registrationFormModel.setLegalStatus(spLegalStatus.getSelectedItem() != null?
                    spLegalStatus.getSelectedItem().toString() : "Individual");

            onFormDataChangeListener.onFormDataChange(registrationFormModel);

            return false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        thCompanyName.onPause();
        thPrimaryNumber.onPause();
        thSecondaryNumber.onPause();
        thEmail.onPause();
        thPanNumber.onPause();
        thVatNumber.onPause();
        thServiceTax.onPause();
        thServiceTaxCode.onPause();
        thAddress.onPause();
        thCity.onPause();
        thState.onPause();
        thPinCode.onPause();
    }
}
