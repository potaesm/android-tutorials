package com.suthinan.roomrxdagger.db;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DatabaseModule {

    @Provides
    static UserDatabase provideUserDatabase(@Named("context") Context context, @Named("dbName") String dbName) {
        return Room.databaseBuilder(context, UserDatabase.class, dbName).build();
    }
}
