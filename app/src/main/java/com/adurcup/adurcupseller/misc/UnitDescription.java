package com.adurcup.adurcupseller.misc;

import java.io.Serializable;

public class UnitDescription implements Serializable {
    private Integer value;
    private String unit;
    private String per;

    public UnitDescription(Integer value, String unit, String per)
    {
        this.value = value;
        this.unit = unit;
        this.per = per;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }
}
