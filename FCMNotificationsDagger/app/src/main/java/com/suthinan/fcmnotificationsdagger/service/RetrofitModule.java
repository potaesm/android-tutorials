package com.suthinan.fcmnotificationsdagger.service;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class RetrofitModule {
    @Provides
    static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    static Retrofit provideRetrofit(@Named("retrofitBaseUrl") String retrofitBaseUrl, OkHttpClient okHttpClient) {
        return new Retrofit
                .Builder()
                .baseUrl(retrofitBaseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    static UserService provideUserService(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }
}
