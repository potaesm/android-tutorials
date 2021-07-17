package com.suthinan.roomrxdagger.db;

import android.content.Context;

import com.suthinan.roomrxdagger.view.MainActivity;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component (modules = DatabaseModule.class)
public interface DatabaseComponent {
    void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(@Named("context") Context context);

        @BindsInstance
        Builder dbName(@Named("dbName") String dbName);

        DatabaseComponent build();
    }
}
