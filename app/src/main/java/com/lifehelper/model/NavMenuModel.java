package com.lifehelper.model;

import com.lifehelper.R;
import com.lifehelper.entity.NavMenuEntity;

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
        navMenuEntities.add(temp);

        temp = new NavMenuEntity();
        temp.setNavMenuName("啪");
        temp.setNavMenuIcon(R.mipmap.icon_nearby_hotel_normal);
        navMenuEntities.add(temp);

        temp = new NavMenuEntity();
        temp.setNavMenuName("玩");
        temp.setNavMenuIcon(R.mipmap.icon_nearby_scenery_normal);
        navMenuEntities.add(temp);

        temp = new NavMenuEntity();
        temp.setNavMenuName("乐");
        temp.setNavMenuIcon(R.mipmap.icon_nearby_film_normal);
        navMenuEntities.add(temp);


        temp = new NavMenuEntity();
        temp.setNavMenuName("谁");
        temp.setNavMenuIcon(R.mipmap.icon_nav_menu_about);
        navMenuEntities.add(temp);

        return navMenuEntities;
    }
}
