package com.suthinan.sharedpreferencesrxdagger.service;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class SharedPreferencesModule {

    @Provides
    static SharedPreferencesRepository provideSharedPreferencesRepository(@Named("context") Context context, @Named("preferenceName") String preferenceName) {
        return SharedPreferencesRepository.createInstance(context, preferenceName);
    }
}
