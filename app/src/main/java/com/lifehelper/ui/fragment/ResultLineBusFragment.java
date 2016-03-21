package com.lifehelper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.lifehelper.R;
import com.lifehelper.app.MyConstance;
import com.lifehelper.baidumap.DrivingRouteOverlay;
import com.lifehelper.baidumap.MyTransitRouteOverlay;
import com.lifehelper.baidumap.TransitRouteOverlay;
import com.lifehelper.baidumap.WalkingRouteOverlay;
import com.lifehelper.entity.RoutLinePlanots;
import com.lifehelper.tools.Logger;
import com.lifehelper.ui.RouteLineActivity;
import com.lifehelper.ui.customwidget.LoadingDialog;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jsion on 16/3/16.
 */
public class ResultLineBusFragment extends BaseFragment {

    private RoutLinePlanots mRoutLinePlanots;
    private RoutePlanSearch mRroutePlanSearch;
    private BaiduMap mBaiduMap;
    private LoadingDialog mLoadingDialog;
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
        mLoadingDialog = new LoadingDialog(getActivity(), false);
    }

    @Override
    public void initEvent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mRoutLinePlanots = bundle.getParcelable(MyConstance.ROUTELINE_PLANNOTES);
            Logger.e("TAG_BUS:" + mRoutLinePlanots.getTargetPlanNode().getCity() + "TAB_TYPE:" + mRoutLinePlanots.getTabType());
        }
        initBMap();
        differentRoutePlan(mRoutLinePlanots.getTabType());
    }


    private void initBMap() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setTrafficEnabled(true);
        mBaiduMap.getUiSettings().setCompassEnabled(true);

        mMapView.showZoomControls(false);
        mMapView.showScaleControl(false);

        MapStatus mMapStatus = new MapStatus.Builder()
                .zoom(17)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        mMapView.setVisibility(View.INVISIBLE);
        mLoadingDialog.show();
        Observable.interval(1, 5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mLoadingDialog.dismiss();
                        mMapView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (0 == aLong.intValue()) {
                            onCompleted();
                            this.unsubscribe();
                        }
                    }
                });
    }


    /**
     * 路线规划
     */
    public void differentRoutePlan(int tabType) {
        mLoadingDialog.show();
        mBaiduMap.clear();
        mRroutePlanSearch = RoutePlanSearch.newInstance();
        mRroutePlanSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                mLoadingDialog.dismiss();
                if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                }
                if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//                    result.getSuggestAddrInfo();
                    return;
                }
                if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    WalkingRouteOverlay walkingRouteOverlay = new WalkingRouteOverlay(mBaiduMap);
                    mBaiduMap.setOnMarkerClickListener(walkingRouteOverlay);
                    walkingRouteOverlay.setData(walkingRouteResult.getRouteLines().get(0));
                    walkingRouteOverlay.addToMap();
                    walkingRouteOverlay.zoomToSpan();
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult result) {
                mLoadingDialog.dismiss();
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
                mLoadingDialog.dismiss();
                if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                    //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//                    result.getSuggestAddrInfo();
                    return;
                }
                if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(drivingRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                }
            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });

        switch (tabType) {
            case RouteLineActivity.TAB_TYPE._BUS:
                mRroutePlanSearch.transitSearch((new TransitRoutePlanOption())
                        .from(mRoutLinePlanots.getStartPlanNode())
                        .city(mRoutLinePlanots.getTargetPlanNode().getCity())
                        .to(mRoutLinePlanots.getTargetPlanNode()));
                break;
            case RouteLineActivity.TAB_TYPE._CAR:
                mRroutePlanSearch.drivingSearch((new DrivingRoutePlanOption())
                        .from(mRoutLinePlanots.getStartPlanNode())
                        .to(mRoutLinePlanots.getTargetPlanNode()));
                break;
            case RouteLineActivity.TAB_TYPE._WALK:
                mRroutePlanSearch.walkingSearch(new WalkingRoutePlanOption()
                        .from(mRoutLinePlanots.getStartPlanNode())
                        .to(mRoutLinePlanots.getTargetPlanNode()));
                break;
        }

    }

    @Override
    public void onPause() {
        mMapView.setVisibility(View.INVISIBLE);
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        mMapView.setVisibility(View.INVISIBLE);
        mRroutePlanSearch.destroy();
        mMapView.onDestroy();
        mBaiduMap = null;
        mMapView = null;
        super.onDestroyView();
    }
}
