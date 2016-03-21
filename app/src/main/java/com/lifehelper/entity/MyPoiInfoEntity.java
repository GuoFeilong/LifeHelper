package com.lifehelper.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.search.core.PoiInfo;

/**
 * Created by jsion on 16/3/14.
 */
public class MyPoiInfoEntity implements Parcelable {
    private PoiInfo poiInfo;
    // UNIT M
    private double distance2MyLocation;

    // FOR GET UI ATTR
    private NavMenuDetailEntity navMenuDetailEntity;

    public PoiInfo getPoiInfo() {
        return poiInfo;
    }

    public void setPoiInfo(PoiInfo poiInfo) {
        this.poiInfo = poiInfo;
    }

    public double getDistance2MyLocation() {
        return distance2MyLocation;
    }

    public void setDistance2MyLocation(double distance2MyLocation) {
        this.distance2MyLocation = distance2MyLocation;
    }

    public NavMenuDetailEntity getNavMenuDetailEntity() {
        return navMenuDetailEntity;
    }

    public void setNavMenuDetailEntity(NavMenuDetailEntity navMenuDetailEntity) {
        this.navMenuDetailEntity = navMenuDetailEntity;
    }


    @Override
    public String toString() {
        return "MyPoiInfoEntity{" +
                "poiInfo=" + poiInfo +
                ", distance2MyLocation=" + distance2MyLocation +
                ", navMenuDetailEntity=" + navMenuDetailEntity +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.poiInfo, flags);
        dest.writeDouble(this.distance2MyLocation);
        dest.writeParcelable(this.navMenuDetailEntity, flags);
    }

    public MyPoiInfoEntity() {
    }

    protected MyPoiInfoEntity(Parcel in) {
        this.poiInfo = in.readParcelable(PoiInfo.class.getClassLoader());
        this.distance2MyLocation = in.readDouble();
        this.navMenuDetailEntity = in.readParcelable(NavMenuDetailEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<MyPoiInfoEntity> CREATOR = new Parcelable.Creator<MyPoiInfoEntity>() {
        @Override
        public MyPoiInfoEntity createFromParcel(Parcel source) {
            return new MyPoiInfoEntity(source);
        }

        @Override
        public MyPoiInfoEntity[] newArray(int size) {
            return new MyPoiInfoEntity[size];
        }
    };
}
