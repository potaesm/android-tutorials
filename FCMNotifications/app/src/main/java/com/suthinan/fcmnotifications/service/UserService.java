package com.suthinan.fcmnotifications.service;

import com.suthinan.fcmnotifications.model.User;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("login")
    Observable<User> login(@Body User user);
}
