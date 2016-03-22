package com.lifehelper.presenter;

import com.lifehelper.PlanNodeTable;
import com.lifehelper.RouteLineNodeTable;

/**
 * Created by jsion on 16/3/22.
 */
public interface GreenDaoPresenter {
    void insertPlanNode(PlanNodeTable planNodeTable);

    void insertRoutePlanNodes(RouteLineNodeTable routeLineNodeTable);

    void clearPlanNode();

    void clearRoutePlanNodes();

    void queryRoutePlanNodes();

    void queryPlanNodes();

}
