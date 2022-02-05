package com.example.tictactoe.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("api/student/get")
    Call<ResponseOTP> getOTP();


}
