package com.lifehelper.entity;

import java.util.List;

/**
 * Created by jsion on 16/3/14.
 */
public class BottomSheetEntity {
    private NavMenuDetailEntity navMenuDetailEntity;
    private List<MyPoiInfoEntity> poiInfoEntities;

    public NavMenuDetailEntity getNavMenuDetailEntity() {
        return navMenuDetailEntity;
    }

    public void setNavMenuDetailEntity(NavMenuDetailEntity navMenuDetailEntity) {
        this.navMenuDetailEntity = navMenuDetailEntity;
    }

    public List<MyPoiInfoEntity> getPoiInfoEntities() {
        return poiInfoEntities;
    }

    public void setPoiInfoEntities(List<MyPoiInfoEntity> poiInfoEntities) {
        this.poiInfoEntities = poiInfoEntities;
    }
}
