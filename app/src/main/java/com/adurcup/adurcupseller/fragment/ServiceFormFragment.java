package com.adurcup.adurcupseller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.adurcup.adurcupseller.R;
import com.adurcup.adurcupseller.misc.OnFormDataChangeListener;
import com.adurcup.adurcupseller.misc.RegistrationFormModel;

import java.util.ArrayList;
import java.util.List;


public class ServiceFormFragment extends FormFragment {

    private CheckBox[] checkBoxes = new CheckBox[6];

    public static FormFragment newInstance(OnFormDataChangeListener callback) {
        FormFragment fragment =  new ServiceFormFragment();
        fragment.onFormDataChangeListener = callback;
        return fragment;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_type_form, container, false);
        checkBoxes[0] = (CheckBox) view.findViewById(R.id.pest_check);
        checkBoxes[1] = (CheckBox) view.findViewById(R.id.packaging_check);
        checkBoxes[2] = (CheckBox) view.findViewById(R.id.wifi_check);
        checkBoxes[3] = (CheckBox) view.findViewById(R.id.breads_check);
        checkBoxes[4] = (CheckBox) view.findViewById(R.id.payment_check);
        checkBoxes[5] = (CheckBox) view.findViewById(R.id.house_check);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public boolean isErrorActive() {
        return !isCheckedAny();
    }

    private boolean isCheckedAny() {
        boolean checked = false;
        List<String> businessType = new ArrayList<>();
        for (CheckBox checkBox: checkBoxes) {
            if (checkBox != null && checkBox.isChecked()) {
                businessType.add(checkBox.getText().toString());
                checked = true;
            }
        }

        if (businessType.size() > 0) {
            RegistrationFormModel registrationFormModel;
            if (onFormDataChangeListener.getFormData() != null)
                registrationFormModel = onFormDataChangeListener.getFormData();
            else registrationFormModel = new RegistrationFormModel();
            registrationFormModel.setBusinessType(businessType);
        }
        return checked;
    }
}
