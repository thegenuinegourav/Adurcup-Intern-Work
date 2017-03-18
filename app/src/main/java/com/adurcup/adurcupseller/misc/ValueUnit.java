package com.adurcup.adurcupseller.misc;

import java.io.Serializable;

/**
 * Created by Sourabh on 24/12/2016.
 */
public class ValueUnit implements Serializable {
    private String value;
    private String unit;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
