package com.suthinan.retrofitget.service;

import com.suthinan.retrofitget.model.Info;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetCountryDataService {
    @GET("country/get/all")
    Call<Info> getResults();
}
