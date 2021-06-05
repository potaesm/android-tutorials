package com.suthinan.fragment;

public class AndroidVersion {
    public double getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(double versionNum) {
        this.versionNum = versionNum;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    private double versionNum;
    private String versionName;
}
