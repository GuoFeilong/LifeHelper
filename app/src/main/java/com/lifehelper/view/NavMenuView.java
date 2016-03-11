package com.lifehelper.view;

import com.lifehelper.entity.NavMenuEntity;

import java.util.List;

/**
 * Created by jsion on 16/3/11.
 */
public interface NavMenuView {
    // menu data from local so,only one method
    void bindNavMenus(List<NavMenuEntity> navMenus);
}
