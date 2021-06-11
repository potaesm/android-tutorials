package com.suthinan.retrofitrx.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Doc implements Parcelable {
    protected Doc(Parcel in) {
        version = in.readString();
    }

    public static final Creator<Doc> CREATOR = new Creator<Doc>() {
        @Override
        public Doc createFromParcel(Parcel in) {
            return new Doc(in);
        }

        @Override
        public Doc[] newArray(int size) {
            return new Doc[size];
        }
    };

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private String version;
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(version);
    }
}
