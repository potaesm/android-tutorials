package com.suthinan.fcmnotificationsdagger.service;

import android.content.Context;

import com.suthinan.fcmnotificationsdagger.view.MainActivity;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component (modules = { RetrofitModule.class, MyFirebaseMessagingModule.class })
public interface ServiceComponent {
    void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(@Named("context") Context context);

        @BindsInstance
        Builder retrofitBaseUrl(@Named("retrofitBaseUrl") String retrofitBaseUrl);

        ServiceComponent build();
    }
}
