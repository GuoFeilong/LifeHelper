package com.lifehelper.ui.customwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.lifehelper.R;

/**
 * Created by jsion on 16/3/9.
 */
public class MapStateView extends View {
    private static final float DE_WIDTH = 35.f;
    private static final float DE_HEIGHT = 35.f;
    private static final float DE_RADIO = 3.f;
    private int mBackgroudColor;
    private int strokeColor;
    private int strokeWidth;
    private Drawable normalStateIcon;
    private Drawable stereoStateIcon;
    private Drawable noCurrentLocationIcon;
    private String mapText;
    private int mapTextColor;
    private int mapTextSize;
    private int mWidth;
    private int mHeight;

    private Paint mBackgroudPaint;
    private Paint mStrokePaint;
    private TextPaint mTextPaint;

    private RectF mBackgroudRectF;
    private RectF mStrokeRectF;
    private float mDeRadio;
    private Bitmap mNormalStateIconBitMap;
    private Bitmap mStereoStateIconBitMap;
    private Bitmap mNoCurrentLocationIconBitMap;
    private int mCurrentState = MAP_STATE.NORMAL;

    public interface OnMapStateViewClickListener {
        void mapStateViewClick(int currentState);
    }

    private OnMapStateViewClickListener mOnMapStateViewClickListener;

    public void setmOnMapStateViewClickListener(OnMapStateViewClickListener mOnMapStateViewClickListener) {
        this.mOnMapStateViewClickListener = mOnMapStateViewClickListener;
    }

    public MapStateView(Context context) {
        this(context, null);
    }

    public MapStateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MapStateView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.MapStateView_mapBackgroudColor:
                    mBackgroudColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MapStateView_mapStrokeColor:
                    strokeColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MapStateView_normalStateIcon:
                    normalStateIcon = typedArray.getDrawable(attr);
                    break;
                case R.styleable.MapStateView_stereoStateIcon:
                    stereoStateIcon = typedArray.getDrawable(attr);
                    break;
                case R.styleable.MapStateView_noCurrentLocationIcon:
                    noCurrentLocationIcon = typedArray.getDrawable(attr);
                    break;
                case R.styleable.MapStateView_mapStrokeWidth:
                    strokeWidth = typedArray.getDimensionPixelOffset(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 1, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MapStateView_mapText:
                    mapText = typedArray.getString(attr);
                    break;
                case R.styleable.MapStateView_mapTextColor:
                    mapTextColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MapStateView_mapTextSize:
                    mapTextSize = typedArray.getDimensionPixelOffset(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 11, getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();
        init();
    }

    private void init() {
        mBackgroudPaint = creatPaint(mBackgroudColor);
        mStrokePaint = creatPaint(strokeColor);
        mTextPaint = creatPaint(mapTextColor, mapTextSize);
        mDeRadio = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DE_RADIO, getResources().getDisplayMetrics());

        BitmapDrawable bitmapDrawable;
        if (normalStateIcon != null) {
            bitmapDrawable = (BitmapDrawable) normalStateIcon;
            mNormalStateIconBitMap = bitmapDrawable.getBitmap();
        }
        if (stereoStateIcon != null) {
            bitmapDrawable = (BitmapDrawable) stereoStateIcon;
            mStereoStateIconBitMap = bitmapDrawable.getBitmap();
        }
        if (noCurrentLocationIcon != null) {
            bitmapDrawable = (BitmapDrawable) noCurrentLocationIcon;
            mNoCurrentLocationIconBitMap = bitmapDrawable.getBitmap();
        }
    }

    /**
     * creat new paint
     *
     * @param backgroudColor
     * @return
     */
    private Paint creatPaint(int backgroudColor) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setColor(backgroudColor);
        return paint;
    }

    /**
     * creat new textpaint
     *
     * @param textColor
     * @param textSize
     * @return
     */
    private TextPaint creatPaint(int textColor, int textSize) {
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        return textPaint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize;
        int heightSize;


        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {
            widthSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DE_WIDTH, getResources().getDisplayMetrics());
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }

        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DE_HEIGHT, getResources().getDisplayMetrics());
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();

        mStrokeRectF = new RectF(0, 0, mWidth, mHeight);
        mBackgroudRectF = new RectF(strokeWidth, strokeWidth, mWidth - strokeWidth, mHeight - strokeWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMapStroke(canvas, mStrokeRectF);
        drawMapBackgroud(canvas, mBackgroudRectF);
        drawMapNormalIcon(canvas);
    }

    private void drawMapNormalIcon(Canvas canvas) {
        canvas.drawBitmap(mNormalStateIconBitMap, (mWidth - mNormalStateIconBitMap.getWidth()) / 2, (mHeight - mNormalStateIconBitMap.getHeight()) / 2, mBackgroudPaint);
    }

    private void drawMapBackgroud(Canvas canvas, RectF backgroudRectF) {
        canvas.drawRoundRect(backgroudRectF, mDeRadio, mDeRadio, mBackgroudPaint);
    }

    private void drawMapStroke(Canvas canvas, RectF strokeRectF) {
        canvas.drawRoundRect(strokeRectF, mDeRadio, mDeRadio, mStrokePaint);
    }


    /**
     * mapview different state
     */
    public static class MAP_STATE {
        public static final int NORMAL = 9;
        public static final int STEREO = 10;
        public static final int NO_CURRENT_LOCATION = 11;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mOnMapStateViewClickListener != null) {
                    mOnMapStateViewClickListener.mapStateViewClick(mCurrentState);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(event);
    }

    public void setmCurrentState(int mCurrentState) {
        this.mCurrentState = mCurrentState;
        postInvalidateIcon(mCurrentState);
    }

    // TODO: 16/3/9 different state different icon
    private void postInvalidateIcon(int currentState) {
        switch (currentState) {
            case MAP_STATE.NORMAL:
                break;
            case MAP_STATE.STEREO:
                break;
            case MAP_STATE.NO_CURRENT_LOCATION:
                break;
        }
    }


}
