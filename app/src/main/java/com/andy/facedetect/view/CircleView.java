package com.andy.facedetect.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Describe the important point of the face
 * Created by andy on 2015/11/13.
 */
public class CircleView extends View {

    private final int DEFAULT_CIRCLE_COLOR = 0xffffffff;
    private Paint paint;
    private int color;
    private int radius;


    public CircleView(Context context) {
        super(context);
        init();
    }


    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    private void init() {
        paint = new Paint();
        color = DEFAULT_CIRCLE_COLOR;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawCircle(radius, radius, radius, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        radius = Math.min(getMeasuredWidth(), getMeasuredHeight())/2;
    }
}
