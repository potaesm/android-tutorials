package com.suthinan.sharedpreferencesrxdagger.service;

import android.content.Context;

import com.suthinan.sharedpreferencesrxdagger.view.MainActivity;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component (modules = SharedPreferencesModule.class)
public interface SharedPreferencesComponent {
    void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(@Named("context") Context context);

        @BindsInstance
        Builder preferenceName(@Named("preferenceName") String preferenceName);

        SharedPreferencesComponent build();
    }
}
