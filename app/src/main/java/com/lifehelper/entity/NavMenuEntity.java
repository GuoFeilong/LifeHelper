package com.lifehelper.entity;

/**
 * Created by jsion on 16/3/11.
 */
public class NavMenuEntity {
    private String navMenuName;
    private int navMenuIcon;

    public String getNavMenuName() {
        return navMenuName;
    }

    public void setNavMenuName(String navMenuName) {
        this.navMenuName = navMenuName;
    }

    public int getNavMenuIcon() {
        return navMenuIcon;
    }

    public void setNavMenuIcon(int navMenuIcon) {
        this.navMenuIcon = navMenuIcon;
    }

    @Override
    public String toString() {
        return "NavMenuEntity{" +
                "navMenuName='" + navMenuName + '\'' +
                ", navMenuIcon=" + navMenuIcon +
                '}';
    }
}
