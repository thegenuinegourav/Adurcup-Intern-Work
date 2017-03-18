package com.adurcup.adurcupseller.misc;

public class PickupScheduleModel {

    private String pickup_addresss, pin, concerned_name, p_mobile, s_mobile, date;
    private Integer weight;

    public Integer getNumber_of_products() {
        return number_of_products;
    }

    public void setNumber_of_products(Integer number_of_products) {
        this.number_of_products = number_of_products;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getS_mobile() {
        return s_mobile;
    }

    public void setS_mobile(String s_mobile) {
        this.s_mobile = s_mobile;
    }

    public String getP_mobile() {
        return p_mobile;
    }

    public void setP_mobile(String p_mobile) {
        this.p_mobile = p_mobile;
    }

    public String getConcerned_name() {
        return concerned_name;
    }

    public void setConcerned_name(String concerned_name) {
        this.concerned_name = concerned_name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPickup_addresss() {
        return pickup_addresss;
    }

    public void setPickup_addresss(String pickup_addresss) {
        this.pickup_addresss = pickup_addresss;
    }

    private Integer number_of_products;

}
