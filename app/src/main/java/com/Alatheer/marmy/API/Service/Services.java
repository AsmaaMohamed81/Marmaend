package com.Alatheer.marmy.API.Service;


import com.Alatheer.marmy.Model.AllDelegateModel;
import com.Alatheer.marmy.Model.ClientOrderModel;
import com.Alatheer.marmy.Model.DelegateOrder;
import com.Alatheer.marmy.Model.Model;
import com.Alatheer.marmy.Model.MSG;
import com.Alatheer.marmy.Model.ResponseModel;
import com.Alatheer.marmy.Model.User;
import com.Alatheer.marmy.Model.gallry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by elashry on 2/6/2018.
 */

public interface Services {


    @GET("AllStadium")
    Call<List<Model>> getgroundData();

    @GET("AllDelegate")
    Call<List<AllDelegateModel>> getDelegate();


    @FormUrlEncoded
    @POST("InsertRegistration")
    Call<MSG> userSignUp(@Field("user_name") String name,
                         @Field("email") String email,
                         @Field("password") String password,
                         @Field("mobile") String mobile,
                         @Field("token_id") String token_id);



    @FormUrlEncoded
    @POST("Login")
    Call<MSG> userLogIn(@Field("user_name") String user_name,
                        @Field("password") String password);


    @FormUrlEncoded
    @POST("AddOred")
    Call<MSG> BookGround(@Field("time_reservation") String time_reservation,
                         @Field("user_id") String UserID,
                         @Field("date_reservation") String Date,
                         @Field("playground_id") String GroundID,
                         @Field("delegates[]") ArrayList<String> delegates_id_fk);



    @FormUrlEncoded
    @POST("AddOrederAdmin")
    Call<MSG> BookGroundAdmin(@Field("time_reservation") String time_reservation,
                         @Field("user_id") String UserID,
                         @Field("date_reservation") String Date,
                         @Field("playground_id") String GroundID
                         );


    @GET("StadiumImages/{id}")
    Call<List<gallry>> gallry(@Path("id") String id);


    @GET("OneDelegateOrder/{id}")
    Call<List<DelegateOrder>> getOrders(@Path("id") String id);


    @FormUrlEncoded
    @POST("OrderCost")
    Call<ResponseModel> response(@Field("id") String id,
                                 @Field("client_id_fk") String client_id_fk,
                                 @Field("playground_cost") String playground_cost);


    @POST("ClientDelegatesOrders/{id}")
    Call<List<ClientOrderModel>> getresponse(@Path("id") String id);


    @FormUrlEncoded
    @POST("SelectDelegates")
    Call<ClientOrderModel> SelectDelegates(@Field("order_id") String order_id,
                         @Field("delegates_id_fk") String delegates_id_fk,
                         @Field("reservation_id_fk") String reservation_id_fk);



    @POST("UpdateRegistration/{id}")
    Call<User> getuserdata(@Path("id") String id);

    @FormUrlEncoded
    @POST("UpdateRegistration/{id}")
    Call<User> update(@Path("id") String id,
                      @Field("user_name") String name,
                      @Field("email") String email,
                      @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("UpdatePassWord")
    Call<User> update_pass(@Field("id") String id,
                      @Field("password") String password);


    @FormUrlEncoded
    @POST("ReservationConfirmation/{client_id}")
    Call<User> ReservationConfirmation(@Path("client_id") String client_id,
                      @Field("transfer_name") String transfer_name,
                      @Field("transfer_phone") String transfer_phone,
                      @Field("transfer_img") String transfer_img,
                      @Field("reservation_id_fk")String reservation_id_fk);


    @GET("ReservationConfirmation/{client_id}")
    Call<User>getData(@Path("client_id") String client_id);


}
