package com.suthinan.fcmnotifications.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload {
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("notification")
    @Expose
    private Notification notification;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
