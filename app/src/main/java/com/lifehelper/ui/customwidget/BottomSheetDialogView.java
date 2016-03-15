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

import com.lifehelper.R;
import com.lifehelper.entity.BottomSheetEntity;
import com.lifehelper.entity.MyPoiInfoEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jsion on 16/3/14.
 */
public class BottomSheetDialogView {
    private Context context;
    private BottomSheetEntity bottomSheetEntity;

    @Bind(R.id.tv_bottom_desc)
    TextView bottomDesc;
    @Bind(R.id.rlv_bottom_all)
    RecyclerView bottomAll;

    public BottomSheetDialogView(Context context, BottomSheetEntity bottomSheetEntity) {
        this.context = context;
        this.bottomSheetEntity = bottomSheetEntity;
        View bottomView = LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet, null);
        ButterKnife.bind(this, bottomView);
        initEvent(bottomSheetEntity);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(bottomView);
        bottomSheetDialog.show();
    }

    private void initEvent(BottomSheetEntity bottomSheetEntity) {
        bottomDesc.setText(bottomSheetEntity.getNavMenuDetailEntity().getNavMenuDetailTitle());
        bottomDesc.setBackgroundColor(context.getResources().getColor(bottomSheetEntity.getNavMenuDetailEntity().getNavMenuDetailColor()));

        bottomAll.setLayoutManager(new LinearLayoutManager(context));
        BottomSheetDeAdapter bottomSheetDeAdapter = new BottomSheetDeAdapter(bottomSheetEntity.getPoiInfoEntities());
        bottomAll.setAdapter(bottomSheetDeAdapter);
    }

    public static void bottomSheetShow(Context context, BottomSheetEntity bottomSheetEntity) {
        new BottomSheetDialogView(context, bottomSheetEntity);
    }

    class BottomSheetDeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<MyPoiInfoEntity> recyclerData;

        public BottomSheetDeAdapter(List<MyPoiInfoEntity> recyclerData) {
            this.recyclerData = recyclerData;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            BottomVH bottomVH = new BottomVH(LayoutInflater.from(context).inflate(R.layout.item_bottom_sheet, parent, false));
            return bottomVH;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyPoiInfoEntity currentEntity = recyclerData.get(position);
            BottomVH bottomVH = (BottomVH) holder;
            bottomVH.bottomIcon.setImageResource(currentEntity.getNavMenuDetailEntity().getNavMenuDetailIcon());
            bottomVH.bottomName.setText(currentEntity.getPoiInfo().name);
            bottomVH.bottomAddress.setText(currentEntity.getPoiInfo().address);
            bottomVH.bottomDistance.setText(String.format("%.1f", currentEntity.getDistance2MyLocation() / 1000) + "千米");
            bottomVH.bottomDistance.setTextColor(context.getResources().getColor(currentEntity.getNavMenuDetailEntity().getNavMenuDetailColor()));
        }

        @Override
        public int getItemCount() {
            return recyclerData.size();
        }

        class BottomVH extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_item_bottom_name)
            TextView bottomName;
            @Bind(R.id.tv_item_bottom_address)
            TextView bottomAddress;
            @Bind(R.id.tv_item_bottom_distance)
            TextView bottomDistance;
            @Bind(R.id.iv_item_bottom_sheet)
            ImageView bottomIcon;

            public BottomVH(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
