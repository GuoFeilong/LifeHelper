package com.lifehelper.presenter.impl;

import com.lifehelper.entity.NavMenuDetailEntity;
import com.lifehelper.model.NavMenuDetailModel;
import com.lifehelper.presenter.NavMenuDetailPresenter;
import com.lifehelper.view.NavMenuDetailView;

import java.util.List;

/**
 * Created by jsion on 16/3/13.
 */
public class NavMenuDetailPresenterImpl implements NavMenuDetailPresenter {
    private NavMenuDetailView navMenuDetailView;
    private NavMenuDetailModel navMenuDetailModel;

    public NavMenuDetailPresenterImpl(NavMenuDetailView navMenuDetailView) {
        navMenuDetailModel = new NavMenuDetailModel();
        this.navMenuDetailView = navMenuDetailView;
    }

    @Override
    public void getNavMenuDetail() {
        List<NavMenuDetailEntity> navMenuDetailEntityList = navMenuDetailModel.getNavMenuDetailData();
        navMenuDetailView.bindNavMenusDetail(navMenuDetailEntityList);
    }
}
