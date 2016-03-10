package com.lifehelper.ui.customwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
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
    private static final float ZOOM_P = 1.4f;
    private static final float ZOOM_P_T = 1.6f;
    private static final int COMMON_MARGIN = -5;

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
    private boolean isMapIconMode;
    private Path mTextPath;
    private RectF textRectF;
    private float textH;
    private float textW;
    private int mCurrentIconAndTextState = MAP_TEXT_STATE.MAP_ICON_ON;

    /**
     * for map icon mode
     */
    public interface OnMapStateViewClickListener {
        void mapStateViewClick(int currentState);
    }

    private OnMapStateViewClickListener mOnMapStateViewClickListener;

    public void setmOnMapStateViewClickListener(OnMapStateViewClickListener mOnMapStateViewClickListener) {
        this.mOnMapStateViewClickListener = mOnMapStateViewClickListener;
    }

    /**
     * for map icon and text
     */
    public interface OnMapIconAndTextClickListener {
        void mapIconAndTextClick(int iconAndTextSate);
    }

    private OnMapIconAndTextClickListener mOnMapIconAndTextClickListener;

    public void setmOnMapIconAndTextClickListener(OnMapIconAndTextClickListener mOnMapIconAndTextClickListener) {
        this.mOnMapIconAndTextClickListener = mOnMapIconAndTextClickListener;
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
                case R.styleable.MapStateView_mapIconMode:
                    isMapIconMode = typedArray.getBoolean(attr, true);
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

        if (isMapIconMode) {
            // three icon mode
            BitmapDrawable bitmapDrawable;
            if (normalStateIcon != null) {
                bitmapDrawable = (BitmapDrawable) normalStateIcon;
                mNormalStateIconBitMap = bitmapDrawable.getBitmap();
                mNormalStateIconBitMap = zoomImage(mNormalStateIconBitMap, mNormalStateIconBitMap.getWidth() * ZOOM_P, mNormalStateIconBitMap.getHeight() * ZOOM_P);
            }
            if (stereoStateIcon != null) {
                bitmapDrawable = (BitmapDrawable) stereoStateIcon;
                mStereoStateIconBitMap = bitmapDrawable.getBitmap();
                mStereoStateIconBitMap = zoomImage(mStereoStateIconBitMap, mStereoStateIconBitMap.getWidth() * ZOOM_P, mStereoStateIconBitMap.getHeight() * ZOOM_P);

            }
            if (noCurrentLocationIcon != null) {
                bitmapDrawable = (BitmapDrawable) noCurrentLocationIcon;
                mNoCurrentLocationIconBitMap = bitmapDrawable.getBitmap();
                mNoCurrentLocationIconBitMap = zoomImage(mNoCurrentLocationIconBitMap, mNoCurrentLocationIconBitMap.getWidth() * ZOOM_P, mNoCurrentLocationIconBitMap.getHeight() * ZOOM_P);
            }
        } else {
            // tow icon or text
            BitmapDrawable bitmapDrawable;
            if (normalStateIcon != null) {
                bitmapDrawable = (BitmapDrawable) normalStateIcon;
                mNormalStateIconBitMap = bitmapDrawable.getBitmap();
                mNormalStateIconBitMap = zoomImage(mNormalStateIconBitMap, mNormalStateIconBitMap.getWidth() * ZOOM_P_T, mNormalStateIconBitMap.getHeight() * ZOOM_P_T);

            }
            if (stereoStateIcon != null) {
                bitmapDrawable = (BitmapDrawable) stereoStateIcon;
                mStereoStateIconBitMap = bitmapDrawable.getBitmap();
                mStereoStateIconBitMap = zoomImage(mStereoStateIconBitMap, mStereoStateIconBitMap.getWidth() * ZOOM_P_T, mStereoStateIconBitMap.getHeight() * ZOOM_P_T);
            }
        }

    }


    /***
     * zoom bitmap
     *
     * @param bgimage
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
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

        if (!isMapIconMode) {
            textW = mTextPaint.measureText(mapText);
            textH = Math.abs(mTextPaint.getFontMetrics().ascent) + Math.abs(mTextPaint.getFontMetrics().descent);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMapStroke(canvas, mStrokeRectF);
        drawMapBackgroud(canvas, mBackgroudRectF);
        if (isMapIconMode) {
            switch (mCurrentState) {
                case MAP_STATE.NORMAL:
                    drawMapIcon(canvas, mNormalStateIconBitMap);
                    break;
                case MAP_STATE.STEREO:
                    drawMapIcon(canvas, mStereoStateIconBitMap);
                    break;
                case MAP_STATE.NO_CURRENT_LOCATION:
                    drawMapIcon(canvas, mNoCurrentLocationIconBitMap);
                    break;
            }

        } else {
            // icon and text mode
            switch (mCurrentIconAndTextState) {
                case MAP_TEXT_STATE.MAP_ICON_ON:
                    if (!TextUtils.isEmpty(mapText)){
                        drawTextModeIcon(canvas, mNormalStateIconBitMap);
                        drawTextModeText(canvas, mNormalStateIconBitMap);
                    }else {
                        // only draw icon
                        drawMapIcon(canvas, mNormalStateIconBitMap);
                    }
                    break;
                case MAP_TEXT_STATE.MAP_ICON_OFF:
                    if (!TextUtils.isEmpty(mapText)){
                        drawTextModeIcon(canvas, mStereoStateIconBitMap);
                        drawTextModeText(canvas, mStereoStateIconBitMap);
                    }else {
                        // only draw icon
                        drawMapIcon(canvas, mStereoStateIconBitMap);
                    }
                    break;
            }

        }

    }

    private void drawTextModeText(Canvas canvas, Bitmap bitmapIcon) {

        if (!isMapIconMode) {
            textRectF = new RectF((mWidth - textW) / 2, bitmapIcon.getHeight() + COMMON_MARGIN + textH + (mHeight - bitmapIcon.getHeight() - textH - COMMON_MARGIN) / 2, (mWidth - textW) / 2 + textW, (mHeight - bitmapIcon.getHeight() - textH) / 2 + bitmapIcon.getHeight() + textH);
            mTextPath = new Path();
            mTextPath.addRect(textRectF, Path.Direction.CW);
        }

        canvas.drawTextOnPath(mapText, mTextPath, 0, 0, mTextPaint);
    }

    private void drawTextModeIcon(Canvas canvas, Bitmap bitmapIcon) {
        canvas.drawBitmap(bitmapIcon, (mWidth - bitmapIcon.getWidth()) / 2, (mHeight - bitmapIcon.getHeight() - textH - COMMON_MARGIN) / 2, mBackgroudPaint);
    }

    private void drawMapIcon(Canvas canvas, Bitmap bitmapIcon) {
        canvas.drawBitmap(bitmapIcon, (mWidth - bitmapIcon.getWidth()) / 2, (mHeight - bitmapIcon.getHeight()) / 2, mBackgroudPaint);
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

    /**
     * icon and text state
     */
    public static class MAP_TEXT_STATE {
        public static final int MAP_ICON_ON = 12;
        public static final int MAP_ICON_OFF = 13;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isMapIconMode) {
                    if (mOnMapStateViewClickListener != null) {
                        mOnMapStateViewClickListener.mapStateViewClick(mCurrentState);
                    }
                } else {
                    if (mOnMapIconAndTextClickListener != null) {
                        mOnMapIconAndTextClickListener.mapIconAndTextClick(mCurrentIconAndTextState);
                    }
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

    /**
     * notify view draw different icon
     *
     * @param currentState
     */
    private void postInvalidateIcon(int currentState) {
        if (isMapIconMode) {
            mCurrentState = currentState;
        } else {
            mCurrentIconAndTextState = currentState;
        }
        postInvalidate();
    }

    public void setmCurrentIconAndTextState(int mCurrentIconAndTextState) {
        this.mCurrentIconAndTextState = mCurrentIconAndTextState;
        postInvalidateIcon(mCurrentIconAndTextState);

    }
}
