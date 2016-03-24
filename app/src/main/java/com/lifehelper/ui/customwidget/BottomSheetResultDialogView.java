package com.lifehelper.ui.customwidget;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.lifehelper.R;

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
        } else if (result instanceof WalkingRouteLine) {
            title = "步行路线";
            bottomDesc.setBackgroundColor(context.getResources().getColor(R.color.skin_colorPrimary_lGreen));
        } else if (result instanceof DrivingRouteLine) {
            title = "驾车路线";
            bottomDesc.setBackgroundColor(context.getResources().getColor(R.color.skin_colorPrimary_mred));
        }
        bottomDesc.setText(title);

    }





    private void updateOffsets(View view) {

        // Manually invalidate the view and parent to make sure we get drawn pre-M
        if (Build.VERSION.SDK_INT < 23) {
            tickleInvalidationFlag(view);
            final ViewParent vp = view.getParent();
            if (vp instanceof View) {
                tickleInvalidationFlag((View) vp);
            }
        }
    }

    private static void tickleInvalidationFlag(View view) {
        final float y = ViewCompat.getTranslationY(view);
        ViewCompat.setTranslationY(view, y + 1);
        ViewCompat.setTranslationY(view, y);
    }


}
