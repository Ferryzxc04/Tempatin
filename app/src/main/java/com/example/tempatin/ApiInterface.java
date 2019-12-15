package com.example.tempatin;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("registerUser")
    Call<responseAuth> postRegister(@Body JsonObject dataRegister);

    @Headers("Content-Type: application/json")
    @POST("loginUser")
    Call<responseAuth> postLogin(@Body JsonObject dataLogin);
}
