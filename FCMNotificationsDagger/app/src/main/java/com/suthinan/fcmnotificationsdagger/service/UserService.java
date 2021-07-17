package com.suthinan.fcmnotificationsdagger.service;

import com.suthinan.fcmnotificationsdagger.model.User;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("login")
    Observable<User> login(@Body User user);
}
