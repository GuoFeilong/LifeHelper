package com.lifehelper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifehelper.R;

/**
 * Created by jsion on 16/3/16.
 */
public class ResultLineBusFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_line_bus, container, false);
        init();
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
