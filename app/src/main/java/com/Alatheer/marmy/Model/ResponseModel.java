package com.Alatheer.marmy.Model;

/**
 * Created by elashry on 2/21/2018.
 */

public class ResponseModel {


    private String order_id;
    private String cost;
    private Integer success;

    public ResponseModel(String order_id, String cost, Integer success) {
        this.order_id = order_id;
        this.cost = cost;
        this.success = success;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
