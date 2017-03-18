package com.adurcup.adurcupseller.misc;

import java.io.Serializable;
import java.util.List;

public class OrderPendingModel implements Serializable {

    private String order_number, order_date, order_from, status, return_id, order_time;
    private Integer quantity;
    private Double price, total_order_amount, return_amount;
    private List<ProductModel> products;

    public OrderPendingModel()
    {

    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public Double getTotal_order_amount() {
        return total_order_amount;
    }

    public void setTotal_order_amount(Double total_order_amount) {
        this.total_order_amount = total_order_amount;
    }

    public Double getReturn_amount() {
        return return_amount;
    }

    public void setReturn_amount(Double return_amount) {
        this.return_amount = return_amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getReturn_id() {
        return return_id;
    }

    public void setReturn_id(String return_id) {
        this.return_id = return_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_from() {
        return order_from;
    }

    public void setOrder_from(String order_from) {
        this.order_from = order_from;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
