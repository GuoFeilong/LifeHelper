package com.lifehelper.view;

import com.lifehelper.entity.NavMenuDetailEntity;

import java.util.List;

/**
 * Created by jsion on 16/3/11.
 */
public interface NavMenuDetailView {
    // menu data from local so,only one method
    void bindNavMenusDetail(List<NavMenuDetailEntity> navMenuDetails);
}
