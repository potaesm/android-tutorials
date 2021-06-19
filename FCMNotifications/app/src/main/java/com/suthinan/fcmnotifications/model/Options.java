package com.suthinan.fcmnotifications.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Options {
    @SerializedName("dryRun")
    @Expose
    private String dryRun;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("timeToLive")
    @Expose
    private String timeToLive;
    @SerializedName("collapseKey")
    @Expose
    private String collapseKey;
    @SerializedName("mutableContent")
    @Expose
    private String mutableContent;
    @SerializedName("contentAvailable")
    @Expose
    private String contentAvailable;
    @SerializedName("restrictedPackageName")
    @Expose
    private String restrictedPackageName;

    public String getDryRun() {
        return dryRun;
    }

    public void setDryRun(String dryRun) {
        this.dryRun = dryRun;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(String timeToLive) {
        this.timeToLive = timeToLive;
    }

    public String getCollapseKey() {
        return collapseKey;
    }

    public void setCollapseKey(String collapseKey) {
        this.collapseKey = collapseKey;
    }

    public String getMutableContent() {
        return mutableContent;
    }

    public void setMutableContent(String mutableContent) {
        this.mutableContent = mutableContent;
    }

    public String getContentAvailable() {
        return contentAvailable;
    }

    public void setContentAvailable(String contentAvailable) {
        this.contentAvailable = contentAvailable;
    }

    public String getRestrictedPackageName() {
        return restrictedPackageName;
    }

    public void setRestrictedPackageName(String restrictedPackageName) {
        this.restrictedPackageName = restrictedPackageName;
    }
}
