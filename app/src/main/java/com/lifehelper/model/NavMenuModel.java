package com.lifehelper.model;

import com.lifehelper.R;
import com.lifehelper.entity.NavMenuEntity;
import com.lifehelper.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jsion on 16/3/11.
 */
public class NavMenuModel {
    public List<NavMenuEntity> getNavMenuData() {
        List<NavMenuEntity> navMenuEntities = new ArrayList<>();
        NavMenuEntity temp = new NavMenuEntity();
        temp.setNavMenuName("吃");
        temp.setNavMenuIcon(R.mipmap.icon_nearby_food_normal);
        temp.setClickType(MainActivity.NAV_MENU_CLICK._EAT);
        navMenuEntities.add(temp);

        temp = new NavMenuEntity();
        temp.setNavMenuName("啪");
        temp.setNavMenuIcon(R.mipmap.icon_nearby_hotel_normal);
        temp.setClickType(MainActivity.NAV_MENU_CLICK._PA);
        navMenuEntities.add(temp);

        temp = new NavMenuEntity();
        temp.setNavMenuName("玩");
        temp.setNavMenuIcon(R.mipmap.icon_nearby_scenery_normal);
        temp.setClickType(MainActivity.NAV_MENU_CLICK._PLAY);
        navMenuEntities.add(temp);

        temp = new NavMenuEntity();
        temp.setNavMenuName("乐");
        temp.setIsShowBottomLine(true);
        temp.setNavMenuIcon(R.mipmap.icon_nearby_film_normal);
        temp.setClickType(MainActivity.NAV_MENU_CLICK._HAPPY);
        navMenuEntities.add(temp);


        temp = new NavMenuEntity();
        temp.setNavMenuName("谁");
        temp.setIsBoldText(true);
        temp.setNavMenuIcon(R.mipmap.icon_nav_menu_about);
        temp.setClickType(MainActivity.NAV_MENU_CLICK._WHO);
        navMenuEntities.add(temp);

        return navMenuEntities;
    }
}
