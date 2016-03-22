package com.lifehelper.entity;

/**
 * Created by jsion on 16/3/22.
 */
public class MyRouteLineTable {
    private MyPlanNodeTable startPlanNode;
    private MyPlanNodeTable targetPlanNode;

    public MyPlanNodeTable getStartPlanNode() {
        return startPlanNode;
    }

    public void setStartPlanNode(MyPlanNodeTable startPlanNode) {
        this.startPlanNode = startPlanNode;
    }

    public MyPlanNodeTable getTargetPlanNode() {
        return targetPlanNode;
    }

    public void setTargetPlanNode(MyPlanNodeTable targetPlanNode) {
        this.targetPlanNode = targetPlanNode;
    }
}
