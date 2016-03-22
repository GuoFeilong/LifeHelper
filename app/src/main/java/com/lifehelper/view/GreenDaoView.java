package com.lifehelper.view;

import com.lifehelper.PlanNodeTable;
import com.lifehelper.RouteLineNodeTable;

import java.util.List;

/**
 * Created by jsion on 16/3/22.
 */
public interface GreenDaoView {
    void bindRoutePlanNodes(List<RouteLineNodeTable> routeLineNodeTable);
    void bindPlanNode(List<PlanNodeTable> planNodeTable);
    void insertPlanNodes(PlanNodeTable planNodeTable);
    void insertRoutePlanNodes(RouteLineNodeTable routeLineNodeTable);
    void clearTable();
}
