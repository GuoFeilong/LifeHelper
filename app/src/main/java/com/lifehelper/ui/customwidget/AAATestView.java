package com.lifehelper.ui.customwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.lifehelper.tools.Logger;

/**
 * Created by jsion on 16/3/31.
 */
public class AAATestView extends TextView {
    private static final String TAG = "AAATestView";

    public AAATestView(Context context) {
        super(context);
    }

    public AAATestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AAATestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
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
        return false;
//        return super.dispatchTouchEvent(event);
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

}
