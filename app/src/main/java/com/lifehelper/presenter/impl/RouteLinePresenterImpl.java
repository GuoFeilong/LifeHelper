package com.lifehelper.presenter.impl;

import com.lifehelper.model.RoutLineTabModel;
import com.lifehelper.presenter.RouteLinePresenter;
import com.lifehelper.view.RouteLineTabView;

/**
 * Created by jsion on 16/3/16.
 */
public class RouteLinePresenterImpl implements RouteLinePresenter {
    private RouteLineTabView routeLineTabView;
    private RoutLineTabModel routLineTabModel;

    public RouteLinePresenterImpl(RouteLineTabView routeLineTabView) {
        routLineTabModel = new RoutLineTabModel();
        this.routeLineTabView = routeLineTabView;
    }

    @Override
    public void getRouteLineEntitys() {
        routeLineTabView.bindRouteLineTabs(routLineTabModel.getRoutLineTabs());
    }
}
