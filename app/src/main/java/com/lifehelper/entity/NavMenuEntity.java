package com.lifehelper.entity;

/**
 * Created by jsion on 16/3/11.
 */
public class NavMenuEntity {
    private String navMenuName;
    private int navMenuIcon;
    private boolean isShowBottomLine;
    private boolean isBoldText;
    private int clickType;

    public boolean isBoldText() {
        return isBoldText;
    }

    public void setIsBoldText(boolean isBoldText) {
        this.isBoldText = isBoldText;
    }

    public int getClickType() {
        return clickType;
    }

    public void setClickType(int clickType) {
        this.clickType = clickType;
    }

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

    public boolean isShowBottomLine() {
        return isShowBottomLine;
    }

    public void setIsShowBottomLine(boolean isShowBottomLine) {
        this.isShowBottomLine = isShowBottomLine;
    }
}
