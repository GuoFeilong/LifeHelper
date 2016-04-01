package com.lifehelper.ui.customwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.lifehelper.tools.Logger;

/**
 * Created by jsion on 16/4/1.
 */
public class AAATestMView extends View {
    private static final String TAG = "AAATestMView";

    public AAATestMView(Context context) {
        super(context);
    }

    public AAATestMView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AAATestMView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Logger.e(TAG, TAG + ">onLayout>>>>>>>changed=" + changed + ">>>left=" + l + ">>>top=" + t + ">>>right=" + r + ">>>bottom=" + b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Logger.d(TAG, TAG + ">onMeasure>>>>>>widthMode=" + widthMode + ">>>heightMode=" + heightMode + ">>>widthMeasureSize=" + widthMeasureSize + ">>>heightMeasureSize=" + heightMeasureSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Logger.w(TAG, TAG + ">onSizeChanged>>>>>width=" + w + ">>>height=" + h + ">>>oldw=" + oldw + ">>>oldh=" + oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Logger.wtf(TAG, TAG + ">onDraw");
    }
}
