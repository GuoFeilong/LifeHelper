package com.lifehelper.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jsion on 16/3/17.
 */
public class MyStartAndTarget implements Parcelable {
    private String startAddName;
    private String targetAddName;

    public String getStartAddName() {
        return startAddName;
    }

    public void setStartAddName(String startAddName) {
        this.startAddName = startAddName;
    }

    public String getTargetAddName() {
        return targetAddName;
    }

    public void setTargetAddName(String targetAddName) {
        this.targetAddName = targetAddName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.startAddName);
        dest.writeString(this.targetAddName);
    }

    public MyStartAndTarget() {
    }

    protected MyStartAndTarget(Parcel in) {
        this.startAddName = in.readString();
        this.targetAddName = in.readString();
    }

    public static final Parcelable.Creator<MyStartAndTarget> CREATOR = new Parcelable.Creator<MyStartAndTarget>() {
        @Override
        public MyStartAndTarget createFromParcel(Parcel source) {
            return new MyStartAndTarget(source);
        }

        @Override
        public MyStartAndTarget[] newArray(int size) {
            return new MyStartAndTarget[size];
        }
    };
}
