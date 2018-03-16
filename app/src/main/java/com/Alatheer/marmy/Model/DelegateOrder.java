package com.Alatheer.marmy.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DelegateOrder {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("client_id_fk")
    @Expose
    private String clientIdFk;
    @SerializedName("delegates_id_fk")
    @Expose
    private String delegatesIdFk;
    @SerializedName("reservation_id_fk")
    @Expose
    private String reservationIdFk;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("date_order")
    @Expose
    private String dateOrder;
    @SerializedName("date_order_ar")
    @Expose
    private String dateOrderAr;
    @SerializedName("playground_id_fk")
    @Expose
    private String playgroundIdFk;
    @SerializedName("playground_cost")
    @Expose
    private String playgroundCost;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("playground_name")
    @Expose
    private String playgroundName;
    @SerializedName("playground_id_pk")
    @Expose
    private String playgroundIdPk;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("delegates_name")
    @Expose
    private String delegatesName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientIdFk() {
        return clientIdFk;
    }

    public void setClientIdFk(String clientIdFk) {
        this.clientIdFk = clientIdFk;
    }

    public String getDelegatesIdFk() {
        return delegatesIdFk;
    }

    public void setDelegatesIdFk(String delegatesIdFk) {
        this.delegatesIdFk = delegatesIdFk;
    }

    public String getReservationIdFk() {
        return reservationIdFk;
    }

    public void setReservationIdFk(String reservationIdFk) {
        this.reservationIdFk = reservationIdFk;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getDateOrderAr() {
        return dateOrderAr;
    }

    public void setDateOrderAr(String dateOrderAr) {
        this.dateOrderAr = dateOrderAr;
    }

    public String getPlaygroundIdFk() {
        return playgroundIdFk;
    }

    public void setPlaygroundIdFk(String playgroundIdFk) {
        this.playgroundIdFk = playgroundIdFk;
    }

    public String getPlaygroundCost() {
        return playgroundCost;
    }

    public void setPlaygroundCost(String playgroundCost) {
        this.playgroundCost = playgroundCost;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPlaygroundName() {
        return playgroundName;
    }

    public void setPlaygroundName(String playgroundName) {
        this.playgroundName = playgroundName;
    }

    public String getPlaygroundIdPk() {
        return playgroundIdPk;
    }

    public void setPlaygroundIdPk(String playgroundIdPk) {
        this.playgroundIdPk = playgroundIdPk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDelegatesName() {
        return delegatesName;
    }

    public void setDelegatesName(String delegatesName) {
        this.delegatesName = delegatesName;
    }

}