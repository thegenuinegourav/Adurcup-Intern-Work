package com.adurcup.adurcupseller.misc;

import java.io.Serializable;

public class NewPrice implements Serializable {
    private Integer minQuantity;
    private Integer incrementValue;
    private String _id;
    private Double price;
    private String unit;
    private Integer maxQuantity;

    public NewPrice(Integer minQuantity, Integer incrementValue, String _id, Double price, Integer maxQuantity, String unit)
    {
        this.minQuantity = minQuantity;
        this.price = price;
        this.maxQuantity = maxQuantity;
        this.incrementValue = incrementValue;
        this._id = _id;
        this.unit = unit;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Integer getIncrementValue() {
        return incrementValue;
    }

    public void setIncrementValue(Integer incrementValue) {
        this.incrementValue = incrementValue;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }
}
