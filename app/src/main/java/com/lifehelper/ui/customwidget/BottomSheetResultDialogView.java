package com.lifehelper.ui.customwidget;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.RouteStep;
import com.baidu.mapapi.search.core.VehicleInfo;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.lifehelper.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jsion on 16/3/24.
 */
public class BottomSheetResultDialogView<T extends RouteLine> {
    private T result;
    private Context context;
    @Bind(R.id.tv_bottom_desc)
    TextView bottomDesc;
    @Bind(R.id.rlv_bottom_all)
    RecyclerView bottomAll;
    private final BottomSheetDialog bottomSheetDialog;
    private List<? extends RouteStep> mAllStep;
    private BusStepAdapter busStepAdapter;
    private WalkStepAdapter walkStepAdapter;
    private DriveStepAdapter driveStepAdapter;

    // construct for bus
    public BottomSheetResultDialogView(T result, Context context) {
        this.context = context;
        this.result = result;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet, null);
        ButterKnife.bind(this, view);
        initEvent(result);
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    private void initEvent(T result) {
        setResultTitle(result);

    }

    private void setResultTitle(T result) {
        String title = "";
        if (result instanceof TransitRouteLine) {
            title = "公交路线";
            bottomDesc.setBackgroundColor(context.getResources().getColor(R.color.skin_colorPrimary_blue));
            mAllStep = ((TransitRouteLine) result).getAllStep();
            busStepAdapter = new BusStepAdapter();
            bottomAll.setLayoutManager(new LinearLayoutManager(context));
            bottomAll.setAdapter(busStepAdapter);

        } else if (result instanceof WalkingRouteLine) {
            title = "步行路线";
            bottomDesc.setBackgroundColor(context.getResources().getColor(R.color.skin_colorPrimary_lGreen));
            mAllStep = ((WalkingRouteLine) result).getAllStep();
            walkStepAdapter = new WalkStepAdapter();
            bottomAll.setLayoutManager(new LinearLayoutManager(context));
            bottomAll.setAdapter(walkStepAdapter);
        } else if (result instanceof DrivingRouteLine) {
            title = "驾车路线";
            bottomDesc.setBackgroundColor(context.getResources().getColor(R.color.skin_colorPrimary_mred));
            mAllStep = ((DrivingRouteLine) result).getAllStep();
            driveStepAdapter = new DriveStepAdapter();
            bottomAll.setLayoutManager(new LinearLayoutManager(context));
            bottomAll.setAdapter(driveStepAdapter);
        }
        bottomDesc.setText(title);
    }

    class BusStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder = null;
            switch (viewType) {
                case TRAN_RESULT_TYPE.WALK:
                    holder = new BusStepWalkVH(LayoutInflater
                            .from(context)
                            .inflate(R.layout.item_bus_result_type_walk, parent, false));
                    break;
                case TRAN_RESULT_TYPE.BUS:
                case TRAN_RESULT_TYPE.SUBWAY:
                    holder = new BusStepBusOrSubWayVH(LayoutInflater
                            .from(context)
                            .inflate(R.layout.item_bus_result_type_subway, parent, false));
                    break;
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (mAllStep != null) {
                TransitRouteLine.TransitStep transitStep = (TransitRouteLine.TransitStep) mAllStep.get(position);
                if (transitStep != null) {
                    RouteNode entrance = transitStep.getEntrance();
                    RouteNode exit = transitStep.getExit();
                    String instructions = transitStep.getInstructions();
                    VehicleInfo vehicleInfo = transitStep.getVehicleInfo();
                    String entranceTitle = "";
                    String exitTitle = "";
                    String vehicleInfoTitle;
                    int passStationNum = 0;
                    int totalPrice;
                    if (entrance != null) {
                        entranceTitle = entrance.getTitle();
                    }

                    if (exit != null) {
                        exitTitle = exit.getTitle();
                    }

                    if (vehicleInfo != null) {
                        vehicleInfoTitle = vehicleInfo.getTitle();
                        passStationNum = vehicleInfo.getPassStationNum();
                        totalPrice = vehicleInfo.getTotalPrice();
                    }

                    BusStepWalkVH busStepWalkVH;
                    BusStepBusOrSubWayVH busStepBusOrSubWayVH;
                    TransitRouteLine.TransitStep.TransitRouteStepType stepType = transitStep.getStepType();
                    if (stepType.equals(TransitRouteLine.TransitStep.TransitRouteStepType.WAKLING)) {
                        busStepWalkVH = (BusStepWalkVH) holder;
                        busStepWalkVH.startWalk.setText(entranceTitle);
                        busStepWalkVH.endWalk.setText(exitTitle);
                        busStepWalkVH.walkStepDesc.setText(instructions);
                        if (position == 0) {
                            busStepWalkVH.walkDashPoint.setShowStartPoint();
                        } else if (position == mAllStep.size() - 1) {
                            busStepWalkVH.walkDashPoint.setShowEndPoint();
                        } else {
                            busStepWalkVH.walkDashPoint.setShowNormal();
                        }
                    } else if (stepType.equals(TransitRouteLine.TransitStep.TransitRouteStepType.BUSLINE)) {
                        busStepBusOrSubWayVH = (BusStepBusOrSubWayVH) holder;
                        setBusOrSubData(instructions, entranceTitle, exitTitle, passStationNum, busStepBusOrSubWayVH);
                        setColorAndIcon(busStepBusOrSubWayVH, R.mipmap.icon_route_incity_list_bus, context.getResources().getColor(R.color.skin_colorPrimary_orange));
                    } else if (stepType.equals(TransitRouteLine.TransitStep.TransitRouteStepType.SUBWAY)) {
                        busStepBusOrSubWayVH = (BusStepBusOrSubWayVH) holder;
                        setColorAndIcon(busStepBusOrSubWayVH, R.mipmap.icon_route_incity_list_subway, context.getResources().getColor(R.color.skin_colorPrimary_blue));
                        setBusOrSubData(instructions, entranceTitle, exitTitle, passStationNum, busStepBusOrSubWayVH);

                    }
                }
            }
        }

        private void setColorAndIcon(BusStepBusOrSubWayVH busStepBusOrSubWayVH, int icon_route_incity_list_bus, int color) {
            busStepBusOrSubWayVH.busOrSubEndIcon.setImageResource(icon_route_incity_list_bus);
            busStepBusOrSubWayVH.busOrSubStartIcon.setImageResource(icon_route_incity_list_bus);
            busStepBusOrSubWayVH.busOrSubLine.setBackgroundColor(color);
        }

        private void setBusOrSubData(String instructions, String entranceTitle, String exitTitle, int passStationNum, BusStepBusOrSubWayVH busStepBusOrSubWayVH) {
            busStepBusOrSubWayVH.busOrSubGetOnStation.setText(entranceTitle);
            busStepBusOrSubWayVH.busOrSubGetOffStation.setText(exitTitle);
            busStepBusOrSubWayVH.busOrSubOrientation.setText(instructions);
            busStepBusOrSubWayVH.busOrSubStationCount.setText(passStationNum + "站");
        }

        @Override
        public int getItemCount() {
            return mAllStep.size();
        }

        @Override
        public int getItemViewType(int position) {
            int viewType = -1;
            if (mAllStep != null && mAllStep.size() > position) {
                TransitRouteLine.TransitStep transitStep = (TransitRouteLine.TransitStep) mAllStep.get(position);
                if (transitStep != null) {
                    TransitRouteLine.TransitStep.TransitRouteStepType stepType = transitStep.getStepType();
                    if (stepType.equals(TransitRouteLine.TransitStep.TransitRouteStepType.WAKLING)) {
                        viewType = TRAN_RESULT_TYPE.WALK;
                    } else if (stepType.equals(TransitRouteLine.TransitStep.TransitRouteStepType.BUSLINE)) {
                        viewType = TRAN_RESULT_TYPE.BUS;
                    } else if (stepType.equals(TransitRouteLine.TransitStep.TransitRouteStepType.SUBWAY)) {
                        viewType = TRAN_RESULT_TYPE.SUBWAY;
                    }

                    return viewType;
                }
            }
            return super.getItemViewType(position);
        }


        class BusStepWalkVH extends RecyclerView.ViewHolder {
            @Bind(R.id.dash_point_walk)
            DashPointView walkDashPoint;
            @Bind(R.id.tv_item_bus_result_start_walk)
            TextView startWalk;
            @Bind(R.id.tv_item_bus_result_end_walk)
            TextView endWalk;
            @Bind(R.id.tv_item_bus_result_start_walk_steps)
            TextView walkStepDesc;

            public BusStepWalkVH(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

        }

        class BusStepBusOrSubWayVH extends RecyclerView.ViewHolder {
            @Bind(R.id.iv_subway_start_icon)
            ImageView busOrSubStartIcon;
            @Bind(R.id.iv_subway_end_icon)
            ImageView busOrSubEndIcon;
            @Bind(R.id.iv_subway_line)
            ImageView busOrSubLine;
            @Bind(R.id.tv_subway_get_on_station)
            TextView busOrSubGetOnStation;
            @Bind(R.id.tv_subway_get_off_station)
            TextView busOrSubGetOffStation;
            @Bind(R.id.tv_subway_orientation)
            TextView busOrSubOrientation;
            @Bind(R.id.tv_subway_station_count)
            TextView busOrSubStationCount;

            public BusStepBusOrSubWayVH(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

    static class TRAN_RESULT_TYPE {
        public static final int WALK = 55;
        public static final int BUS = 56;
        public static final int SUBWAY = 57;

    }


    class WalkStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = new WalkSetpVH(LayoutInflater.from(context)
                    .inflate(R.layout.item_walk_result_type_point, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            WalkingRouteLine.WalkingStep walkingStep = (WalkingRouteLine.WalkingStep) mAllStep.get(position);

            WalkSetpVH walkSetpVH = (WalkSetpVH) holder;
            if (walkingStep != null) {
                String entranceInstructions = walkingStep.getEntranceInstructions();
                String exitInstructions = walkingStep.getExitInstructions();

                walkSetpVH.entranceText.setText(entranceInstructions);
                walkSetpVH.exitText.setText(exitInstructions);

                if (position == 0) {
                    walkSetpVH.walkStart.setShowStartPoint();
                    walkSetpVH.walkEnd.setShowNormal();
                } else if (position == mAllStep.size() - 1) {
                    walkSetpVH.walkStart.setShowNormal();
                    walkSetpVH.walkEnd.setShowEndPoint();
                } else {
                    walkSetpVH.walkStart.setShowNormal();
                    walkSetpVH.walkEnd.setShowNormal();
                }
            }

        }

        @Override
        public int getItemCount() {
            return mAllStep == null ? 0 : mAllStep.size();
        }

        class WalkSetpVH extends RecyclerView.ViewHolder {
            @Bind(R.id.dash_point_walk_start)
            DashPointView walkStart;
            @Bind(R.id.dash_point_walk_end)
            DashPointView walkEnd;
            @Bind(R.id.tv_walk_entrance_text)
            TextView entranceText;
            @Bind(R.id.tv_walk_exit_text)
            TextView exitText;

            public WalkSetpVH(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


    class DriveStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = new WalkSetpVH(LayoutInflater.from(context)
                    .inflate(R.layout.item_drive_result_type_point, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            DrivingRouteLine.DrivingStep drivingStep = (DrivingRouteLine.DrivingStep) mAllStep.get(position);

            WalkSetpVH walkSetpVH = (WalkSetpVH) holder;
            if (drivingStep != null) {
                String entranceInstructions = drivingStep.getEntranceInstructions();
                String exitInstructions = drivingStep.getExitInstructions();

                walkSetpVH.entranceText.setText(entranceInstructions);
                walkSetpVH.exitText.setText(exitInstructions);

                if (position == 0) {
                    walkSetpVH.walkStart.setShowStartPoint();
                    walkSetpVH.walkEnd.setShowNormal();
                } else if (position == mAllStep.size() - 1) {
                    walkSetpVH.walkStart.setShowNormal();
                    walkSetpVH.walkEnd.setShowEndPoint();
                } else {
                    walkSetpVH.walkStart.setShowNormal();
                    walkSetpVH.walkEnd.setShowNormal();
                }
            }

        }

        @Override
        public int getItemCount() {
            return mAllStep == null ? 0 : mAllStep.size();
        }

        class WalkSetpVH extends RecyclerView.ViewHolder {
            @Bind(R.id.dash_point_walk_start)
            DashPointView walkStart;
            @Bind(R.id.dash_point_walk_end)
            DashPointView walkEnd;
            @Bind(R.id.tv_walk_entrance_text)
            TextView entranceText;
            @Bind(R.id.tv_walk_exit_text)
            TextView exitText;

            public WalkSetpVH(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


}
