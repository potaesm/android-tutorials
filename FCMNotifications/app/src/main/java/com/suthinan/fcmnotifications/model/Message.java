package com.suthinan.fcmnotifications.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("registrationToken")
    @Expose
    private String[] registrationToken;
    @SerializedName("payload")
    @Expose
    private Payload payload;
    @SerializedName("options")
    @Expose
    private Options options;

    public String[] getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String[] registrationToken) {
        this.registrationToken = registrationToken;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }
}
