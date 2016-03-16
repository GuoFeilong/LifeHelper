package com.lifehelper.model;

import com.lifehelper.entity.RoutLineTabEntity;
import com.lifehelper.ui.RouteLineActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jsion on 16/3/16.
 */
public class RoutLineTabModel {
    public List<RoutLineTabEntity> getRoutLineTabs() {
        List<RoutLineTabEntity> routLineTabEntities = new ArrayList<>();
        RoutLineTabEntity temp = new RoutLineTabEntity();
        temp.setTabName("公交");
        temp.setTabType(RouteLineActivity.TAB_TYPE._BUS);
        routLineTabEntities.add(temp);

        temp = new RoutLineTabEntity();
        temp.setTabName("步行");
        temp.setTabType(RouteLineActivity.TAB_TYPE._WALK);
        routLineTabEntities.add(temp);


        temp = new RoutLineTabEntity();
        temp.setTabName("驾车");
        temp.setTabType(RouteLineActivity.TAB_TYPE._CAR);
        routLineTabEntities.add(temp);

        return routLineTabEntities;
    }
}
