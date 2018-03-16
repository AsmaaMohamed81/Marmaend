package com.Alatheer.marmy.Model;

/**
 * Created by elashry on 2/21/2018.
 */

public class ClientOrderModel {


    private String order_id;
    private String playground_cost;
    private String playground_name;
    private String delegates_name;
    private String delegates_id_fk;
    private String reservation_id_fk;
    private Integer success;

    public ClientOrderModel(String order_id, String playground_cost, String playground_name, String delegates_name ) {
        this.order_id = order_id;
        this.playground_cost = playground_cost;
        this.playground_name = playground_name;
        this.delegates_name = delegates_name;
    }

    public ClientOrderModel(String order_id, String delegates_id_fk, String reservation_id_fk) {
        this.order_id = order_id;
        this.delegates_id_fk = delegates_id_fk;
        this.reservation_id_fk = reservation_id_fk;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPlayground_cost() {
        return playground_cost;
    }

    public void setPlayground_cost(String playground_cost) {
        this.playground_cost = playground_cost;
    }

    public String getPlayground_name() {
        return playground_name;
    }

    public void setPlayground_name(String playground_name) {
        this.playground_name = playground_name;
    }

    public String getDelegates_name() {
        return delegates_name;
    }

    public void setDelegates_name(String delegates_name) {
        this.delegates_name = delegates_name;
    }


    public String getDelegates_id_fk() {
        return delegates_id_fk;
    }

    public void setDelegates_id_fk(String delegates_id_fk) {
        this.delegates_id_fk = delegates_id_fk;
    }

    public String getReservation_id_fk() {
        return reservation_id_fk;
    }

    public void setReservation_id_fk(String reservation_id_fk) {
        this.reservation_id_fk = reservation_id_fk;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
