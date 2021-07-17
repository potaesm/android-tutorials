package com.suthinan.retrofitrxdagger.service;

import android.content.Context;

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
    static ReceivedCookiesInterceptor provideReceivedCookiesInterceptor(@Named("context") Context context) {
        return new ReceivedCookiesInterceptor(context);
    }

    @Provides
    static AddCookiesInterceptor provideAddCookiesInterceptor(@Named("context") Context context) {
        return new AddCookiesInterceptor(context);
    }

    @Provides
    static OkHttpClient provideOkHttpClient(ReceivedCookiesInterceptor receivedCookiesInterceptor, AddCookiesInterceptor addCookiesInterceptor) {
        return new OkHttpClient().newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(receivedCookiesInterceptor)
                .addInterceptor(addCookiesInterceptor)
                .build();
    }

    @Provides
    static Retrofit provideRetrofit(@Named("baseUrl") String baseUrl, OkHttpClient okHttpClient) {
        return new Retrofit
                .Builder()
                .baseUrl(baseUrl)
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
