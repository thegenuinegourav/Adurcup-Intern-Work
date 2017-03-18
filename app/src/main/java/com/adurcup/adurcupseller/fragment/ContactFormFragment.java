package com.adurcup.adurcupseller.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.OnFormDataChangeListener;
import com.adurcup.adurcupseller.misc.RegistrationFormModel;

public class ContactFormFragment extends FormFragment{

    public static FormFragment newInstance(OnFormDataChangeListener callback) {
        FormFragment fragment =  new ContactFormFragment();
        fragment.onFormDataChangeListener = callback;
        return fragment;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_contact_form, container, false);
        tilName = (TextInputLayout) view.findViewById(R.id.concernedPersonInput);
        tilPrimaryNumber = (TextInputLayout) view.findViewById(R.id.primaryNumberInput);
        tilSecondaryNumber = (TextInputLayout) view.findViewById(R.id.secondaryNumberInput);
        tilEmail = (TextInputLayout) view.findViewById(R.id.emailInput);
        tilAge = (TextInputLayout) view.findViewById(R.id.ageInput);
        tilNationality = (TextInputLayout) view.findViewById(R.id.nationalityInput);
        tilPickupAddress = (TextInputLayout) view.findViewById(R.id.pickupInput);
        tilPincode = (TextInputLayout) view.findViewById(R.id.pinCodeInput);
        tilCity = (TextInputLayout) view.findViewById(R.id.cityInput);
        tilState = (TextInputLayout) view.findViewById(R.id.stateInput);
        spGender = (Spinner) view.findViewById(R.id.gender);
        spAssertion = (Spinner) view.findViewById(R.id.addressAssertion);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        thName = new TextHandler(tilName, new OnCheckListener() {
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

        thPrimaryNumber =  new TextHandler(tilPrimaryNumber, new OnCheckListener() {
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

        thSecondaryNumber = new TextHandler(tilSecondaryNumber, new OnCheckListener() {
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

        thEmail = new TextHandler(tilEmail, new OnCheckListener() {
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

        thAge = new TextHandler(tilAge, new OnCheckListener() {
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

        thNationality = new TextHandler(tilNationality, new OnCheckListener() {
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

        thPickupAddress = new TextHandler(tilPickupAddress, new OnCheckListener() {
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

        thPincode =  new TextHandler(tilPincode, new OnCheckListener() {
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

        spAssertion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RegistrationFormModel registrationFormModel;
                if (onFormDataChangeListener.getFormData() != null) {
                    registrationFormModel =
                            onFormDataChangeListener.getFormData();
                } else {
                    registrationFormModel = new RegistrationFormModel();
                }
                if (registrationFormModel.getAddressCity().size() > 0) {
                    EditText etPickupAddress = tilPickupAddress.getEditText();
                    EditText etCity = tilCity.getEditText();
                    EditText etState = tilState.getEditText();
                    EditText etPincode = tilPincode.getEditText();
                    if (i == 1) {
                        if (etPickupAddress != null) {
                            etPickupAddress.setText(registrationFormModel
                                    .getAddressLine1().get(0));
                            etPickupAddress.setEnabled(false);
                        }
                        if (etCity != null) {
                            etCity.setText(registrationFormModel
                                    .getAddressCity().get(0));
                            etCity.setEnabled(false);
                        }
                        if (etState != null) {
                            etState.setText(registrationFormModel
                                    .getAddressState().get(0));
                            etState.setEnabled(false);
                        }
                        if (etPincode != null) {
                            etPincode.setText(registrationFormModel
                                    .getAddressPinCode().get(0));
                            etPincode.setEnabled(false);
                        }
                    } else {
                        if (etPickupAddress != null) {
                            etPickupAddress.setText("");
                            etPickupAddress.setEnabled(true);
                        }
                        if (etCity != null) {
                            etCity.setText("");
                            etCity.setEnabled(true);
                        }
                        if (etState != null) {
                            etState.setText("");
                            etState.setEnabled(true);
                        }
                        if (etPincode != null) {
                            etPincode.setText("");
                            etPincode.setEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        thName.onPause();
        thPrimaryNumber.onPause();
        thSecondaryNumber.onPause();
        thEmail.onPause();
        thAge.onPause();
        thNationality.onPause();
        thPincode.onPause();
        thCity.onPause();
        thState.onPause();
        thPickupAddress.onPause();
    }

    private TextInputLayout tilName, tilPrimaryNumber, tilSecondaryNumber,
            tilEmail, tilAge, tilNationality, tilPickupAddress, tilPincode, tilCity, tilState;
    private TextHandler thName, thPrimaryNumber, thSecondaryNumber,
            thEmail, thAge, thNationality, thPickupAddress, thPincode, thCity, thState;
    private Spinner spGender, spAssertion;

    @Override
    public boolean isErrorActive() {
        if (thName.getValue() == null || thPrimaryNumber.getValue() == null
                || thSecondaryNumber.getValue() == null || thEmail.getValue() == null
                || thAge.getValue() == null || thNationality.getValue() == null
                || thPincode.getValue() == null || thCity.getValue() == null
                || thState.getValue() == null || thPickupAddress.getValue() == null) {
            return true;
        } else {
            RegistrationFormModel registrationFormModel;
            if (onFormDataChangeListener.getFormData() != null) {
                registrationFormModel = onFormDataChangeListener.getFormData();
            } else {
                registrationFormModel = new RegistrationFormModel();
            }
            registrationFormModel.setConcernPersonPrimaryNumber(thPrimaryNumber.getValue());
            registrationFormModel.setConcernPersonSecondaryNumber(thSecondaryNumber.getValue());
            registrationFormModel.setConcernPersonEmail(thEmail.getValue());
            registrationFormModel.setConcernPersonAge(thAge.getValue());
            registrationFormModel.setConcernPersonNationality(thNationality.getValue());

            registrationFormModel.setConcernPersonGender(getResources()
                        .getStringArray(R.array.gender)[spGender.getSelectedItemPosition()]);

            if (!(spAssertion.getSelectedItemPosition() == 1)) {
                registrationFormModel.setAddressLine1(thPickupAddress.getValue(), 1);
                registrationFormModel.setAddressCity(thCity.getValue(), 1);
                registrationFormModel.setAddressState(thState.getValue(), 1);
                registrationFormModel.setAddressPinCode(thPincode.getValue(), 1);
                Toast.makeText(getContext(), thPrimaryNumber.getValue(), Toast.LENGTH_SHORT).show();
                registrationFormModel.setAddressPhone(thPrimaryNumber.getValue());
                registrationFormModel.setRegisteredAddress(false, 1);
            }

            String name = thName.getValue();
            registrationFormModel.setAddressName(name, 1);
            if (name.contains(" ")) {
                int i = name.indexOf(" ");
                if (i < name.length()) {
                    registrationFormModel.setConcernPersonFirstName(name.substring(0, i));
                    registrationFormModel.setConcernPersonSecondName(name.substring(i));
                } else {
                    registrationFormModel.setConcernPersonFirstName(name);
                    registrationFormModel.setConcernPersonSecondName("-");
                }
            } else {
                registrationFormModel.setConcernPersonFirstName(name);
                registrationFormModel.setConcernPersonSecondName("-");
            }
            onFormDataChangeListener.onFormDataChange(registrationFormModel);
            return false;
        }
    }
}
