package com.thunderlight.sdkDemo.utils;

/**
 * @author Created by M.Moradikia
 * @date 2/13/2023
 * @company Thunder-Light
 */
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class BlurTestView extends View{

    private Paint paint;
    private Paint mShadowPaint;
    private int size = 200;
    private RectF mShadowBounds = new RectF();

    public BlurTestView(Context context) {
        this(context, null, 0);
    }

    public BlurTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurTestView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint(0);
        paint.setColor(0x22ffffff);
        paint.setTextSize(size);
        paint.setMaskFilter(new BlurMaskFilter(25, BlurMaskFilter.Blur.NORMAL));

        mShadowPaint = new Paint(0);
        mShadowPaint.setColor(0x22ffffff);
        mShadowPaint.setMaskFilter(new BlurMaskFilter(25, BlurMaskFilter.Blur.NORMAL));

        mShadowBounds.top = size;
        mShadowBounds.bottom = mShadowBounds.top+(size /2);
        mShadowBounds.left = 0;
        mShadowBounds.right = (int)paint.measureText("|||||");
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        canvas.drawRect(mShadowBounds,mShadowPaint);
        canvas.drawText("|||||", 0, size, paint);
    }

}