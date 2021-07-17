package com.suthinan.retrofitrxdagger.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import io.reactivex.annotations.NonNull;
import okhttp3.Interceptor;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class ReceivedCookiesInterceptor implements Interceptor {
    private final Context context;
    public ReceivedCookiesInterceptor(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            HashSet<String> cookies = new HashSet<>();
            for(String header: originalResponse.headers("Set-Cookie"))
            {
                Log.i(TAG, "Intercept cookie is:"+header);
                cookies.add(header);
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences("cookieData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("cookie", cookies);
            editor.apply();
        }

        return originalResponse;
    }
}
