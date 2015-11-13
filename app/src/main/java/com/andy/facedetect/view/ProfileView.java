package com.andy.facedetect.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import retrofit.http.PATCH;

/**
 * The view do describe the profile of face
 * Created by andy on 2015/11/13.
 */
public class ProfileView extends View {

    private static final int PROFILE_BORDER_COLOR = 0xffffffff;
    private static final int PROFILE_BORDER_WIDTH = 10;
    private Paint paint;
    private RectF profileBounds;

    private int borderWidth;


    public ProfileView(Context context) {
        super(context);
        init();
    }

    public ProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setProfileBounds(RectF bounds) {
        this.profileBounds = bounds;
    }

    private void init() {
        paint = new Paint();
        profileBounds = new RectF();
        borderWidth = PROFILE_BORDER_WIDTH;
    }

    private void drawProfile(Canvas canvas) {
        paint.setColor(PROFILE_BORDER_COLOR);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        canvas.drawRect(profileBounds, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        profileBounds.left = 0;
        profileBounds.top = 0;
        profileBounds.right = getMeasuredWidth();
        profileBounds.bottom = getMeasuredHeight();
        borderWidth = getMeasuredWidth()/20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProfile(canvas);
    }
}
