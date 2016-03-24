package com.lifehelper.ui.customwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.lifehelper.R;

/**
 * Created by jsion on 16/3/24.
 */
public class DashPointView extends View {
    private static final float POINT_RADIO_SIZE = 1 / 6F;
    private static final float BIG_POINT_RADIO_SIZE = 1 / 4F;
    private static final float POINT_DISTANCE = 1 / 6F;
    private static final float BIG_POINT_DISTANCE = 1 / 4F;

    private int commonPointColor;
    private int startPointColor;
    private int endPointColor;
    private boolean showStartPoint;
    private boolean showEndPoint;
    private boolean showBoth;
    private boolean showNormal;
    private int commonRadioSize;
    private int bigRadioSize;
    private int pointDistance;
    private int bigPointDistance;
    private int pointCount;
    private int cX;
    private int cY;
    private int height;
    private DisplayMetrics metrics;
    private Paint paint;

    public DashPointView(Context context) {
        this(context, null);
    }

    public DashPointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DashPointView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.DashPointView_pointColor:
                    commonPointColor = typedArray.getColor(attr, getResources().getColor(R.color.common_line));
                    break;
                case R.styleable.DashPointView_startPointColor:
                    startPointColor = typedArray.getColor(attr, getResources().getColor(R.color.common_line));
                    break;
                case R.styleable.DashPointView_endPointColor:
                    endPointColor = typedArray.getColor(attr, getResources().getColor(R.color.common_line));
                    break;
                case R.styleable.DashPointView_showStartPoint:
                    showStartPoint = typedArray.getBoolean(attr, true);
                    break;
                case R.styleable.DashPointView_showEndPoint:
                    showEndPoint = typedArray.getBoolean(attr, false);
                    break;
                case R.styleable.DashPointView_showBothPoint:
                    showBoth = typedArray.getBoolean(attr, false);
                case R.styleable.DashPointView_showNormalPoint:
                    showNormal = typedArray.getBoolean(attr, false);
                    break;
            }
        }
        typedArray.recycle();
        init();
    }

    private void init() {
        metrics = getContext().getResources().getDisplayMetrics();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(commonPointColor);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int withMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthMeasureSize;
        int heightMeasureSize;

        if (withMeasureMode == MeasureSpec.AT_MOST || withMeasureMode == MeasureSpec.UNSPECIFIED) {
            widthMeasureSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, metrics);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeasureSize, MeasureSpec.EXACTLY);
        }

        if (heightMeasureMode == MeasureSpec.AT_MOST || heightMeasureMode == MeasureSpec.UNSPECIFIED) {
            heightMeasureSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, metrics);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeasureSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        commonRadioSize = (int) (w * POINT_RADIO_SIZE);
        bigRadioSize = (int) (w * BIG_POINT_RADIO_SIZE);
        pointDistance = (int) (w * POINT_DISTANCE) * 2;
        bigPointDistance = (int) (w * BIG_POINT_DISTANCE) * 2;
        if (showStartPoint) {
            pointCount = 1 + (h - bigPointDistance - bigRadioSize * 2) / (commonRadioSize * 2 + pointDistance);
        }
        if (showEndPoint) {
            pointCount = (h - bigPointDistance - bigRadioSize * 2) / (commonRadioSize * 2 + pointDistance) + 1;
        }
        if (showBoth) {
            pointCount = (h - (bigPointDistance + bigRadioSize * 2) * 2) / (commonRadioSize * 2 + pointDistance) + 2;
        }
        if (showNormal) {
            pointCount = h / (commonRadioSize * 2 + pointDistance);
        }
        cX = w / 2;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDashPoint(canvas);
    }

    private void drawDashPoint(Canvas canvas) {
        for (int i = 0; i < pointCount; i++) {
            if (showNormal) {
                cY = (i + 1) * pointDistance + (i + 1) * 2 * commonRadioSize - commonRadioSize;
                paint.setColor(commonPointColor);
                canvas.drawCircle(cX, cY, commonRadioSize, paint);
            }
            if (showStartPoint) {
                if (i == 0) {
                    cY = bigRadioSize;
                    paint.setColor(startPointColor);
                    canvas.drawCircle(cX, cY, bigRadioSize, paint);
                } else {
                    cY = bigRadioSize * 2 + bigPointDistance + i * 2 * commonRadioSize - commonRadioSize + (i - 1) * pointDistance;
                    paint.setColor(commonPointColor);
                    canvas.drawCircle(cX, cY, commonRadioSize, paint);
                }
            }
            if (showEndPoint) {
                if (i != pointCount - 1) {
                    cY = (i + 1) * pointDistance + (i + 1) * 2 * commonRadioSize - commonRadioSize;
                    paint.setColor(commonPointColor);
                    canvas.drawCircle(cX, cY, commonRadioSize, paint);
                } else {
                    cY = height - bigRadioSize;
                    paint.setColor(endPointColor);
                    canvas.drawCircle(cX, cY, bigRadioSize, paint);
                }
            }
        }
    }
}
