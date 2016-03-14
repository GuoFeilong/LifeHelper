package com.lifehelper.entity;

import com.baidu.mapapi.search.core.PoiInfo;

/**
 * Created by jsion on 16/3/14.
 */
public class MyPoiInfoEntity {
    private PoiInfo poiInfo;
    // UNIT M
    private double distance2MyLocation;

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
}
