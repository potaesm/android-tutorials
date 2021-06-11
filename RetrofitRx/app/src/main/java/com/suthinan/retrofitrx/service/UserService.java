package com.suthinan.retrofitrx.service;

import com.suthinan.retrofitrx.model.Jwt;
import com.suthinan.retrofitrx.model.User;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("login")
    Observable<Jwt> login(@Body User user);

    @POST("user")
    Observable<User> postUser(@Body User user);

    @GET("user")
    Observable<ArrayList<User>> getUsers();

    @PATCH("user/{id}")
    Completable patchUserById(@Path("id") String id, @Body User user);

    @DELETE("user/{id}")
    Completable deleteUserById(@Path("id") String id);
}
