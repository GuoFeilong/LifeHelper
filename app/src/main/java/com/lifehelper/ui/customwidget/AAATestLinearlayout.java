package com.lifehelper.ui.customwidget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.lifehelper.tools.Logger;

/**
 * Created by jsion on 16/3/31.
 */
public class AAATestLinearlayout extends LinearLayout {
    private static final String TAG = "AAATestLinearlayout";

    public AAATestLinearlayout(Context context) {
        super(context);
    }

    public AAATestLinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Logger.e(TAG, TAG + ">>>>构造设置onTouch.ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Logger.d(TAG, TAG + ">>>>构造设置onTouch.ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Logger.w(TAG, TAG + ">>>>构造设置onTouch.ACTION_UP");
                        break;
                }

                return false;
            }
        });
    }

    public AAATestLinearlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.e(TAG, TAG + ">>>>dispatchTouchEvent.ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d(TAG, TAG + ">>>>dispatchTouchEvent.ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Logger.w(TAG, TAG + ">>>>dispatchTouchEvent.ACTION_UP");
                break;
        }

//        return true;
//        return false;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.e(TAG, TAG + ">>>>onInterceptTouchEvent.ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d(TAG, TAG + ">>>>onInterceptTouchEvent.ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Logger.w(TAG, TAG + ">>>>onInterceptTouchEvent.ACTION_UP");
                break;
        }

//        return true;
//        return false;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.e(TAG, TAG + ">>>>onTouchEvent.ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d(TAG, TAG + ">>>>onTouchEvent.ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Logger.w(TAG, TAG + ">>>>onTouchEvent.ACTION_UP");
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
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
