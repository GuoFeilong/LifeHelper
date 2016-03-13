package com.lifehelper.entity;

import java.util.List;

/**
 * Created by jsion on 16/3/13.
 */
public class NavMenuDetailEntity {
    private String navMenuDetailTitle;
    private String navMenuDetailDesc;
    private int navMenuDetailIcon;
    private int navMenuDetailColor;
    private List<String> navMenuDetailList;
    private int navMenuDetaType;

    public int getNavMenuDetaType() {
        return navMenuDetaType;
    }

    public void setNavMenuDetaType(int navMenuDetaType) {
        this.navMenuDetaType = navMenuDetaType;
    }

    public String getNavMenuDetailTitle() {
        return navMenuDetailTitle;
    }

    public void setNavMenuDetailTitle(String navMenuDetailTitle) {
        this.navMenuDetailTitle = navMenuDetailTitle;
    }

    public String getNavMenuDetailDesc() {
        return navMenuDetailDesc;
    }

    public void setNavMenuDetailDesc(String navMenuDetailDesc) {
        this.navMenuDetailDesc = navMenuDetailDesc;
    }

    public int getNavMenuDetailIcon() {
        return navMenuDetailIcon;
    }

    public void setNavMenuDetailIcon(int navMenuDetailIcon) {
        this.navMenuDetailIcon = navMenuDetailIcon;
    }

    public int getNavMenuDetailColor() {
        return navMenuDetailColor;
    }

    public void setNavMenuDetailColor(int navMenuDetailColor) {
        this.navMenuDetailColor = navMenuDetailColor;
    }

    public List<String> getNavMenuDetailList() {
        return navMenuDetailList;
    }

    public void setNavMenuDetailList(List<String> navMenuDetailList) {
        this.navMenuDetailList = navMenuDetailList;
    }
}
