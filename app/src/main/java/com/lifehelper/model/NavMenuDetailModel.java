package com.lifehelper.model;

import com.lifehelper.R;
import com.lifehelper.entity.NavMenuDetailEntity;
import com.lifehelper.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jsion on 16/3/13.
 */
public class NavMenuDetailModel {
    public List<NavMenuDetailEntity> getNavMenuDetailData() {
        List<NavMenuDetailEntity> navMenuDetailEntityList = new ArrayList<>();
        ArrayList<String> tempDiff = new ArrayList<>();

        NavMenuDetailEntity tempNavDe = new NavMenuDetailEntity();
        tempNavDe.setNavMenuDetailTitle("发现 . 美食");
        tempNavDe.setNavMenuDetailDesc("美食");
        tempNavDe.setNavMenuDetailColor(R.color.skin_colorPrimary_orange);
        tempNavDe.setNavMenuDetailIcon(R.mipmap.icon_nearby_food_normal);
        tempNavDe.setNavMenuDetaType(MainActivity.NAV_MENU_CLICK._EAT);

        tempDiff.add("烤鱼");
        tempDiff.add("火锅");
        tempDiff.add("麻辣小龙虾");
        tempDiff.add("烧烤");

        tempNavDe.setNavMenuDetailList(tempDiff);
        navMenuDetailEntityList.add(tempNavDe);

        tempDiff = new ArrayList<>();
        tempNavDe = new NavMenuDetailEntity();
        tempNavDe.setNavMenuDetailTitle("发现 . 美女");
        tempNavDe.setNavMenuDetailDesc("酒店");
        tempNavDe.setNavMenuDetailColor(R.color.skin_colorPrimary_purple);
        tempNavDe.setNavMenuDetailIcon(R.mipmap.icon_nearby_hotel_normal);
        tempNavDe.setNavMenuDetaType(MainActivity.NAV_MENU_CLICK._PA);

        tempDiff.add("7天连锁");
        tempDiff.add("如家");
        tempDiff.add("布丁酒店");
        tempDiff.add("汉庭酒店");

        tempNavDe.setNavMenuDetailList(tempDiff);
        navMenuDetailEntityList.add(tempNavDe);

        tempDiff = new ArrayList<>();
        tempNavDe = new NavMenuDetailEntity();
        tempNavDe.setNavMenuDetailTitle("发现 . 美景");
        tempNavDe.setNavMenuDetailDesc("景点");
        tempNavDe.setNavMenuDetailColor(R.color.skin_colorPrimary_lGreen);
        tempNavDe.setNavMenuDetailIcon(R.mipmap.icon_nearby_scenery_normal);
        tempNavDe.setNavMenuDetaType(MainActivity.NAV_MENU_CLICK._PLAY);

        tempDiff.add("动物园");
        tempDiff.add("密室逃脱");
        tempDiff.add("公园");
        tempDiff.add("奥运村");
        tempDiff.add("古镇");

        tempNavDe.setNavMenuDetailList(tempDiff);
        navMenuDetailEntityList.add(tempNavDe);

        tempDiff = new ArrayList<>();
        tempNavDe = new NavMenuDetailEntity();
        tempNavDe.setNavMenuDetailTitle("发现 . 美乐");
        tempNavDe.setNavMenuDetailDesc("娱乐");
        tempNavDe.setNavMenuDetailColor(R.color.skin_colorPrimary_mred);
        tempNavDe.setNavMenuDetailIcon(R.mipmap.icon_nearby_film_normal);
        tempNavDe.setNavMenuDetaType(MainActivity.NAV_MENU_CLICK._HAPPY);

        tempDiff.add("KTV");
        tempDiff.add("洗浴");
        tempDiff.add("足疗");
        tempDiff.add("按摩");
        tempDiff.add("酒吧");

        tempNavDe.setNavMenuDetailList(tempDiff);
        navMenuDetailEntityList.add(tempNavDe);

        return navMenuDetailEntityList;
    }
}
