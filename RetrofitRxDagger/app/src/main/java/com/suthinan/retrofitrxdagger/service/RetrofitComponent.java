package com.suthinan.retrofitrxdagger.service;

import android.content.Context;

import com.suthinan.retrofitrxdagger.view.MainActivity;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component (modules = RetrofitModule.class)
public interface RetrofitComponent {
    void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(@Named("context") Context context);

        @BindsInstance
        Builder baseUrl(@Named("baseUrl") String baseUrl);

        RetrofitComponent build();
    }
}
