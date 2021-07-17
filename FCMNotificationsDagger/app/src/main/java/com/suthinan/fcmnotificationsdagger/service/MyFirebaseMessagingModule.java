package com.suthinan.fcmnotificationsdagger.service;

import dagger.Module;
import dagger.Provides;

@Module
public class MyFirebaseMessagingModule {

    private MyFirebaseMessagingService myFirebaseMessagingService;

    public MyFirebaseMessagingModule() {
        this.myFirebaseMessagingService = new MyFirebaseMessagingService();
    }

    @Provides
    public MyFirebaseMessagingService provideMyFirebaseMessagingService() {
        return this.myFirebaseMessagingService;
    }
}
