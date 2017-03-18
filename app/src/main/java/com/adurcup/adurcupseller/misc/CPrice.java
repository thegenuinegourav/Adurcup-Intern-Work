package com.adurcup.adurcupseller.misc;

import java.io.Serializable;

public class CPrice implements Serializable {
    private Integer minQuantity;
    private Integer incrementValue;
    private String _id;
    private Integer margin;
    private Integer maxQuantity;

    public CPrice(Integer minQuantity, Integer incrementValue, String _id, Integer margin, Integer maxQuantity)
    {
        this.minQuantity = minQuantity;
        this.margin = margin;
        this.maxQuantity = maxQuantity;
        this.incrementValue = incrementValue;
        this._id = _id;
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

    public Integer getMargin() {
        return margin;
    }

    public void setMargin(Integer margin) {
        this.margin = margin;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }
}
