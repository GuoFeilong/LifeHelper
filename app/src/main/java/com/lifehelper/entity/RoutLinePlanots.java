package com.lifehelper.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.search.route.PlanNode;

/**
 * Created by jsion on 16/3/18.
 */
public class RoutLinePlanots implements Parcelable {
    private PlanNode startPlanNode;
    private PlanNode targetPlanNode;
    private int tabType;

    public int getTabType() {
        return tabType;
    }

    public void setTabType(int tabType) {
        this.tabType = tabType;
    }


    public PlanNode getStartPlanNode() {
        return startPlanNode;
    }

    public void setStartPlanNode(PlanNode startPlanNode) {
        this.startPlanNode = startPlanNode;
    }

    public PlanNode getTargetPlanNode() {
        return targetPlanNode;
    }

    public void setTargetPlanNode(PlanNode targetPlanNode) {
        this.targetPlanNode = targetPlanNode;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.startPlanNode, flags);
        dest.writeParcelable(this.targetPlanNode, flags);
        dest.writeInt(this.tabType);
    }

    public RoutLinePlanots() {
    }

    protected RoutLinePlanots(Parcel in) {
        this.startPlanNode = in.readParcelable(PlanNode.class.getClassLoader());
        this.targetPlanNode = in.readParcelable(PlanNode.class.getClassLoader());
        this.tabType = in.readInt();
    }

    public static final Parcelable.Creator<RoutLinePlanots> CREATOR = new Parcelable.Creator<RoutLinePlanots>() {
        @Override
        public RoutLinePlanots createFromParcel(Parcel source) {
            return new RoutLinePlanots(source);
        }

        @Override
        public RoutLinePlanots[] newArray(int size) {
            return new RoutLinePlanots[size];
        }
    };
}
