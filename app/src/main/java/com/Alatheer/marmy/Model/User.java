package com.Alatheer.marmy.Model;


import com.google.gson.annotations.SerializedName;

/**
 * Created by elashry on 2/6/2018.
 */

public class User {


    @SerializedName("user_name")
    private String user_name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("mobile")
    private String mobile;

    @SerializedName("id")
    private String ID;

    public User() {
    }

    public User(String user_name, String email, String password, String mobile) {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;


    }

    public User(String ID) {
        this.ID = ID;
    }

    public String getID() {

        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
