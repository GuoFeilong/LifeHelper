package com.lifehelper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifehelper.R;
import com.lifehelper.app.MyConstance;
import com.lifehelper.entity.RoutLinePlanots;
import com.lifehelper.tools.Logger;

/**
 * Created by jsion on 16/3/16.
 */
public class ResultLineCarFragment extends BaseFragment {

    private RoutLinePlanots mRoutLinePlanots;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_line_car, container, false);
        init(view);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mRoutLinePlanots = bundle.getParcelable(MyConstance.ROUTELINE_PLANNOTES);
            Logger.e("TAG_CAR:" + mRoutLinePlanots.getStartPlanNode().getCity());
        }
    }

    @Override
    public void initView(View view) {

    }
}
