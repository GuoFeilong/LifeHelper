package com.lifehelper.view;

import com.lifehelper.entity.RoutLineTabEntity;

import java.util.List;

/**
 * Created by jsion on 16/3/16.
 */
public interface RouteLineTabView {
    // menu data from local so,only one method
    void bindRouteLineTabs(List<RoutLineTabEntity> routLineTabEntities);
}
