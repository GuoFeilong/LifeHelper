package com.lifehelper.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.TaxiInfo;
import com.baidu.mapapi.search.core.VehicleInfo;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
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
import com.lifehelper.ui.customwidget.BottomSheetResultDialogView;
import com.lifehelper.ui.customwidget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
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
    @Bind(R.id.rlv_route_line_bus_result)
    RecyclerView mBusResult;
    private BusRouteResultAdapter mResultAdapter;
    private List<TransitRouteLine> mBusRouteLines;
    private OnRecyclerItemClickListener recylerItemClick;
    private int mPosition;
    private boolean isClickRecyler;
    private TransitRouteLine mCurrentTransitRouteLine;

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
        mBusRouteLines = new ArrayList<>();
    }

    @Override
    public void initEvent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mRoutLinePlanots = bundle.getParcelable(MyConstance.ROUTELINE_PLANNOTES);
            Logger.e("TAG_BUS:" + mRoutLinePlanots.getTargetPlanNode().getCity() + "TAB_TYPE:" + mRoutLinePlanots.getTabType());
        }

        initBMap();
        if (mRoutLinePlanots.getTabType() == RouteLineActivity.TAB_TYPE._BUS) {
            mResultAdapter = new BusRouteResultAdapter();
            mBusResult.setLayoutManager(new LinearLayoutManager(getActivity()));
            mBusResult.setAdapter(mResultAdapter);
            recylerItemClick = new OnRecyclerItemClickListener() {
                @Override
                public void onRecylerItemClick(int postion, TransitRouteLine transitRouteLine) {
                    mPosition = postion;
                    isClickRecyler = true;
                    mCurrentTransitRouteLine = transitRouteLine;
                    differentRoutePlan(mRoutLinePlanots.getTabType());
                }
            };
        }

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
                        if (mRoutLinePlanots.getTabType() == RouteLineActivity.TAB_TYPE._BUS) {
                            mMapView.setVisibility(View.GONE);
                        } else {
                            mMapView.setVisibility(View.VISIBLE);
                        }
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
                mMapView.setVisibility(View.VISIBLE);
                mBusResult.setVisibility(View.GONE);
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

//                    List<WalkingRouteLine> walkingRouteResultRouteLines = walkingRouteResult.getRouteLines();
//                    for (int i = 0; i < walkingRouteResultRouteLines.size(); i++) {
//                        WalkingRouteLine walkingRouteLine = walkingRouteResultRouteLines.get(i);
//                        List<WalkingRouteLine.WalkingStep> allStep = walkingRouteLine.getAllStep();
//                        for (int j = 0; j < allStep.size(); i++) {
//                            WalkingRouteLine.WalkingStep walkingStep = allStep.get(j);
//
//                        }
//                    }
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
                    if (mRoutLinePlanots.getTabType() == RouteLineActivity.TAB_TYPE._BUS && !isClickRecyler) {
                        mBusResult.setVisibility(View.VISIBLE);
                        mBusRouteLines = result.getRouteLines();
                        mResultAdapter.notifyDataSetChanged();
                        mMapView.setVisibility(View.GONE);

                        mResultAdapter.setRecyclerItemClickListener(recylerItemClick);
                    } else {
                        mMapView.setVisibility(View.VISIBLE);
                        mBusResult.setVisibility(View.GONE);
                        TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaiduMap, false);
                        mBaiduMap.setOnMarkerClickListener(overlay);
                        overlay.setData(result.getRouteLines().get(mPosition));
                        overlay.addToMap();
                        overlay.zoomToSpan();

                        BottomSheetResultDialogView resultDialogView = new BottomSheetResultDialogView(mCurrentTransitRouteLine, getActivity());

                    }
                }

                StringBuffer busResultBuffer = new StringBuffer();


                TaxiInfo taxiInfo = result.getTaxiInfo();
                if (taxiInfo != null) {
                    int taxiInfoDuration = taxiInfo.getDuration();
                    String taxiInfoDesc = taxiInfo.getDesc();
                    int taxiInfoDistance = taxiInfo.getDistance();
                    float taxiInfoPerKMPrice = taxiInfo.getPerKMPrice();
                    float taxiInfoStartPrice = taxiInfo.getStartPrice();
                    float taxiInfoTotalPrice = taxiInfo.getTotalPrice();

                    busResultBuffer.append("taxiInfoDuration=")
                            .append(taxiInfoDuration + "\n")
                            .append("taxiInfoDesc=")
                            .append(taxiInfoDesc + "\n")
                            .append("taxiInfoDistance=")
                            .append(taxiInfoDistance + "\n")
                            .append("taxiInfoPerKMPrice=")
                            .append(taxiInfoPerKMPrice + "\n")
                            .append("taxiInfoStartPrice=")
                            .append(taxiInfoStartPrice + "\n")
                            .append("taxiInfoTotalPrice")
                            .append(taxiInfoTotalPrice + "\n");
                }

                List<TransitRouteLine> routeLines = result.getRouteLines();
                if (routeLines != null && routeLines.size() > 0) {
                    for (int i = 0; i < routeLines.size(); i++) {
                        TransitRouteLine transitRouteLine = routeLines.get(i);
                        if (transitRouteLine != null) {
                            int transitRouteLineDuration = transitRouteLine.getDuration();
                            int transitRouteLineDistance = transitRouteLine.getDistance();

                            busResultBuffer.append("\ntransitRouteLineDuration=")
                                    .append(transitRouteLineDuration + "\n")
                                    .append("transitRouteLineDistance=")
                                    .append(transitRouteLineDistance + "\n");

                            RouteNode transitRouteLineStarting = transitRouteLine.getStarting();
                            RouteNode transitRouteLineTerminal = transitRouteLine.getTerminal();


                            if (transitRouteLineStarting != null) {
                                String transitRouteLineTerminalStartingTitle = transitRouteLineStarting.getTitle();

                                busResultBuffer.append("transitRouteLineTerminalStartingTitle=")
                                        .append(transitRouteLineTerminalStartingTitle + "\n");

                            }
                            if (transitRouteLineTerminal != null) {
                                String transitRouteLineTerminalTerminalTitle = transitRouteLineTerminal.getTitle();
                                busResultBuffer.append("transitRouteLineTerminalTerminalTitle=")
                                        .append(transitRouteLineTerminalTerminalTitle + "\n");
                            }


                            List<TransitRouteLine.TransitStep> allStep = transitRouteLine.getAllStep();
                            if (allStep != null && allStep.size() > 0) {
                                for (int j = 0; j < allStep.size(); j++) {
                                    TransitRouteLine.TransitStep transitStep = allStep.get(j);
                                    if (transitStep != null) {
                                        VehicleInfo vehicleInfo = transitStep.getVehicleInfo();

                                        if (vehicleInfo != null) {
                                            String vehicleInfoTitle = vehicleInfo.getTitle();
                                            int vehicleInfoPassStationNum = vehicleInfo.getPassStationNum();
                                            int vehicleInfoTotalPrice = vehicleInfo.getTotalPrice();
                                            int vehicleInfoZonePrice = vehicleInfo.getZonePrice();

                                            busResultBuffer.append("vehicleInfoTitle=")
                                                    .append(vehicleInfoTitle + "\n")
                                                    .append("vehicleInfoPassStationNum=")
                                                    .append(vehicleInfoPassStationNum + "\n")
                                                    .append("vehicleInfoTotalPrice=")
                                                    .append(vehicleInfoTotalPrice + "\n")
                                                    .append("vehicleInfoZonePrice=")
                                                    .append(vehicleInfoZonePrice + "\n");
                                        }

                                        int transitStepDuration = transitStep.getDuration();

                                        busResultBuffer.append("transitStepDuration=")
                                                .append(transitStepDuration + "\n");

                                        RouteNode transitStepEntrance = transitStep.getEntrance();
                                        RouteNode transitStepExit = transitStep.getExit();

                                        if (transitStepEntrance != null) {
                                            String transitStepEntrancetitle = transitStepEntrance.getTitle();
                                            busResultBuffer.append("transitStepEntrancetitle=")
                                                    .append(transitStepEntrancetitle + "\n");
                                        }

                                        if (transitStepExit != null) {
                                            String transitStepExitTitle = transitStepExit.getTitle();
                                            busResultBuffer.append("transitStepExitTitle=")
                                                    .append(transitStepExitTitle + "\n");
                                        }

                                        String transitStepInstructions = transitStep.getInstructions();
                                        TransitRouteLine.TransitStep.TransitRouteStepType stepType = transitStep.getStepType();

                                        busResultBuffer.append("transitStepInstructions=")
                                                .append(transitStepInstructions + "\n")
                                                .append("stepType=")
                                                .append(stepType + "\n");

                                    }
                                }
                            }
                        }
                    }
                }

                Logger.e(busResultBuffer.toString());
            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                mMapView.setVisibility(View.VISIBLE);
                mBusResult.setVisibility(View.GONE);
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
        isClickRecyler = false;
        mPosition = 0;
        super.onDestroyView();

    }

    public interface OnRecyclerItemClickListener {
        void onRecylerItemClick(int postion, TransitRouteLine transitRouteLine);
    }

    class BusRouteResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private OnRecyclerItemClickListener recyclerItemClickListener;

        public void setRecyclerItemClickListener(OnRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = new BusRouteResultVH(LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_route_line_busresult, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final TransitRouteLine transitRouteLine = mBusRouteLines.get(position);
            BusRouteResultVH busRouteResultVH = (BusRouteResultVH) holder;
            if (transitRouteLine != null) {
                String distance = transitRouteLine.getDistance() + getResources().getString(R.string.m_unit);
                busRouteResultVH.resultDistance.setText(distance);
                String time = String.format("%.1f", transitRouteLine.getDuration() / 60.0f) + "分钟";
                busRouteResultVH.resultTime.setText(time);
                int stationCount = 0;
                StringBuffer routeBusTitle = new StringBuffer();
                List<TransitRouteLine.TransitStep> allStep = transitRouteLine.getAllStep();
                if (allStep != null && allStep.size() > 0) {
                    for (int i = 0; i < allStep.size(); i++) {
                        VehicleInfo vehicleInfo = allStep.get(i).getVehicleInfo();
                        if (vehicleInfo != null) {
                            int passStationNum = vehicleInfo.getPassStationNum();
                            stationCount += passStationNum;
                            String title = vehicleInfo.getTitle();
                            if (allStep.size() > 1 && i != allStep.size() - 1) {
                                routeBusTitle.append(title)
                                        .append(" - ");
                            } else {
                                routeBusTitle.append(title);
                            }
                        }
                    }
                }
                busRouteResultVH.resultStation.setText(stationCount + "站");
                busRouteResultVH.resultTrans.setText(routeBusTitle.toString());

                if (recyclerItemClickListener != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recyclerItemClickListener.onRecylerItemClick(position, transitRouteLine);
                        }
                    });
                }
            }

        }

        @Override
        public int getItemCount() {
            return mBusRouteLines.size();
        }

        class BusRouteResultVH extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_item_route_bus_result_trans)
            TextView resultTrans;
            @Bind(R.id.tv_item_route_bus_result_time)
            TextView resultTime;
            @Bind(R.id.tv_item_route_bus_result_station)
            TextView resultStation;
            @Bind(R.id.tv_item_route_bus_result_walk_distance)
            TextView resultDistance;

            public BusRouteResultVH(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
