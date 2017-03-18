package com.adurcup.adurcupseller.misc;

import java.io.Serializable;

/**
 * Created by Sourabh on 24/12/2016.
 */
public class Size implements Serializable {
    private String unit;
    private String value;
    private String type;

    public Size(String unit, String value, String type)
    {
        this.unit = unit;
        this.value = value;
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
