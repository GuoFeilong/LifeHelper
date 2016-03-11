package com.lifehelper.presenter.impl;

import com.lifehelper.entity.NavMenuEntity;
import com.lifehelper.model.NavMenuModel;
import com.lifehelper.presenter.NavMenuPresenter;
import com.lifehelper.view.NavMenuView;

import java.util.List;

/**
 * Created by jsion on 16/3/11.
 */
public class NavMenuPresenterImpl implements NavMenuPresenter {
    private NavMenuView navMenuView;
    private NavMenuModel navMenuModel;


    public NavMenuPresenterImpl(NavMenuView navMenuView) {
        navMenuModel = new NavMenuModel();
        this.navMenuView = navMenuView;
    }

    @Override
    public void getNavMenuData() {
        List<NavMenuEntity> temp = navMenuModel.getNavMenuData();
        navMenuView.bindNavMenus(temp);
    }
}
