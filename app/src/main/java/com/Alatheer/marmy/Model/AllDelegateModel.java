package com.Alatheer.marmy.Model;

/**
 * Created by elashry on 2/14/2018.
 */

public class AllDelegateModel {


    private String user_name;
    private String id;

    public AllDelegateModel(String user_name, String id) {
        this.user_name = user_name;
        this.id = id;

    }

    public AllDelegateModel() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
