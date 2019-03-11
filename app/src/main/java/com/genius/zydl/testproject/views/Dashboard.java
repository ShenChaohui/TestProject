package com.genius.zydl.testproject.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.genius.zydl.testproject.R;

public class Dashboard extends View {
    private float maxValue;
    private float newValue;

    private Bitmap mBitmapBg;
    private Bitmap mBitmapPointer;

    private Paint mPaint;
    private PaintFlagsDrawFilter mDrawFilter; // 为画布设置抗锯齿

    public Dashboard(Context context) {
        this(context, null);
    }

    public Dashboard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Dashboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        maxValue = 0.5f;
        newValue = 0.34f;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        mBitmapBg = decodeResource(getResources(), R.mipmap.dashboadr_bg);
        mBitmapPointer = decodeResource(getResources(), R.mipmap.dashboard_pointer);
    }
    private Bitmap decodeResource(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);
        // 画出背景
        canvas.drawBitmap(mBitmapBg, 0, 0, null);

        Rect textBound = new Rect();
        String text = 0 + "";
        mPaint.getTextBounds(text, 0, text.length(), textBound);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(30);
        canvas.drawText(text, 50, mBitmapBg.getHeight(), mPaint);

        Rect textBoundMax = new Rect();
        String max = maxValue + "";
        mPaint.getTextBounds(max, 0, text.length(), textBoundMax);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(30);
        canvas.drawText(max, mBitmapBg.getWidth() - 80 - textBoundMax.width(), mBitmapBg.getHeight(), mPaint);

        Rect textBoundCenter = new Rect();
        String center = maxValue / 2 + "";
        mPaint.getTextBounds(center, 0, text.length(), textBoundCenter);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(30);
        canvas.drawText(center, mBitmapBg.getWidth() / 2 - textBoundCenter.width(), 50 + textBoundCenter.height(), mPaint);

        canvas.save();

        float angleHour = (newValue / maxValue) * 180;
        canvas.rotate(angleHour, mBitmapBg.getWidth() / 2, mBitmapBg.getHeight());
        Rect rect = new Rect(mBitmapBg.getWidth() / 2 - mBitmapPointer.getWidth() - 30, mBitmapBg.getHeight() - mBitmapPointer.getHeight() / 2, mBitmapBg.getWidth() / 2 - 16, mBitmapBg.getHeight() + mBitmapPointer.getHeight() / 2);
        canvas.drawBitmap(mBitmapPointer, null, rect, null);
    }
}
