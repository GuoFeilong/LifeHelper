package com.lifehelper.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.location.BDLocation;

/**
 * Created by jsion on 16/3/21.
 */
public class MyPoiInfroWithLocationEntity implements Parcelable {
    private BDLocation mLocation;
    private MyPoiInfoEntity mPoiInfoEntity;

    public BDLocation getmLocation() {
        return mLocation;
    }

    public void setmLocation(BDLocation mLocation) {
        this.mLocation = mLocation;
    }

    public MyPoiInfoEntity getmPoiInfoEntity() {
        return mPoiInfoEntity;
    }

    public void setmPoiInfoEntity(MyPoiInfoEntity mPoiInfoEntity) {
        this.mPoiInfoEntity = mPoiInfoEntity;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mLocation, flags);
        dest.writeParcelable(this.mPoiInfoEntity, flags);
    }

    public MyPoiInfroWithLocationEntity() {
    }

    protected MyPoiInfroWithLocationEntity(Parcel in) {
        this.mLocation = in.readParcelable(BDLocation.class.getClassLoader());
        this.mPoiInfoEntity = in.readParcelable(MyPoiInfoEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<MyPoiInfroWithLocationEntity> CREATOR = new Parcelable.Creator<MyPoiInfroWithLocationEntity>() {
        @Override
        public MyPoiInfroWithLocationEntity createFromParcel(Parcel source) {
            return new MyPoiInfroWithLocationEntity(source);
        }

        @Override
        public MyPoiInfroWithLocationEntity[] newArray(int size) {
            return new MyPoiInfroWithLocationEntity[size];
        }
    };
}
