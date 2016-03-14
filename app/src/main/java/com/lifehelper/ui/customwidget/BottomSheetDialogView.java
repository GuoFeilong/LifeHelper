package com.lifehelper.ui.customwidget;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lifehelper.R;
import com.lifehelper.entity.BottomSheetEntity;

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
    }

    public static void bottomSheetShow(Context context, BottomSheetEntity bottomSheetEntity) {
        new BottomSheetDialogView(context, bottomSheetEntity);
    }

}
