package com.lifehelper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.lifehelper.R;
import com.lifehelper.app.MyConstance;
import com.lifehelper.baidumap.MyTransitRouteOverlay;
import com.lifehelper.baidumap.TransitRouteOverlay;
import com.lifehelper.entity.RoutLinePlanots;
import com.lifehelper.tools.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jsion on 16/3/16.
 */
public class ResultLineBusFragment extends BaseFragment {

    private RoutLinePlanots mRoutLinePlanots;
    private RoutePlanSearch mRroutePlanSearch;
    private BaiduMap mBaiduMap;

    @Bind(R.id.fragmnt_bmapView)
    MapView mMapView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_line_bus, container, false);
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
            Logger.e("TAG_BUS:" + mRoutLinePlanots.getStartPlanNode().getCity());
        }
        initBMap();
        testRoutePlan();
    }

    private void initBMap() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setTrafficEnabled(true);
        mBaiduMap.getUiSettings().setCompassEnabled(true);

        mMapView.showZoomControls(false);
        mMapView.showScaleControl(false);
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }


    /**
     * 测试路线规划
     */
    private void testRoutePlan() {
        mRroutePlanSearch = RoutePlanSearch.newInstance();
        mRroutePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                }
                if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//                    result.getSuggestAddrInfo();
                    return;
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaiduMap, false);
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(result.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });

        mRroutePlanSearch.transitSearch((new TransitRoutePlanOption())
                .from(mRoutLinePlanots.getStartPlanNode())
                .city(mRoutLinePlanots.getTargetPlanNode().getCity())
                .to(mRoutLinePlanots.getTargetPlanNode()));
    }

    @Override
    public void onDestroy() {
        mRroutePlanSearch.destroy();
        super.onDestroy();
    }
}
