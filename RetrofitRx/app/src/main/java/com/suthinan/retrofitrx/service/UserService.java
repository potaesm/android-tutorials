package com.suthinan.retrofitrx.service;

import com.suthinan.retrofitrx.model.Doc;
import com.suthinan.retrofitrx.model.Jwt;
import com.suthinan.retrofitrx.model.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @GET("doc")
    Observable<Doc> getDoc();

    @POST("login")
    Observable<Jwt> login(@Body User user);

    @POST("user")
    Observable<User> postUser(@Body User user);

    @GET("user")
    Observable<ArrayList<User>> getUsers();

    @GET("user/{id}")
    Observable<User> getUserById(@Path("id") String id);

    @PATCH("user/{id}")
    Observable<User> patchUserById(@Path("id") String id);

    @DELETE("user")
    Observable<User> deleteUsers();

    @DELETE("user/{id}")
    Observable<User> deleteUserById(@Path("id") String id);
}
