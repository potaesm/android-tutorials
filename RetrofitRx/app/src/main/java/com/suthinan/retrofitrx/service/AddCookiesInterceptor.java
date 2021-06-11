package com.suthinan.retrofitrx.service;

import android.content.Context;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {
    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    private Context context;
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = (HashSet<String>) context.getSharedPreferences("cookieData", Context.MODE_PRIVATE).getStringSet("cookie", null);
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
            }
        }
        return chain.proceed(builder.build());
    }
}
