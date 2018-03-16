package com.Alatheer.marmy.Model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by elashry on 2/6/2018.
 */

public class Book {


    @SerializedName("user_id_fk")
    private String UserID;
    @SerializedName("reservation_date_ar")
    private String Date;
    @SerializedName("playground_id_fk")
    private String GroundID;


    public Book(String UserID, String Date, String GroundID) {
        this.UserID = UserID;
        this.Date = Date;
        this.GroundID = GroundID;


    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getGroundID() {
        return GroundID;
    }

    public void setGroundID(String groundID) {
        GroundID = groundID;
    }


}
