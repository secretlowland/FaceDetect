package com.andy.facedetect.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.andy.facedetect.R;


/**
 * 用于显示图片以及识别面部信息后画出的面部轮廓
 * Created by lxn on 2015/10/17.
 */
public class FaceView extends View {

    private Bitmap image;
    private Paint paint;
    private RectF profile;
    private String info;

    private int imgWidth;
    private int imgHeight;

    public FaceView(Context context) {
        this(context, null);
    }

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setMeasuredDimension(10, 10);
        this.setBackgroundColor(Color.DKGRAY );
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(25);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        profile = new RectF();
    }


    /**
     * 清除视图
     */
    public void clear() {
        this.image = null;
        this.profile = null;
        this.info = null;
        this.imgWidth = 0;
        this.imgHeight = 0;
    }
    /**
     * 重绘视图
     */
    public void refresh() {
        invalidate();
    }

    /**
     * 设置识别出来的面部轮廓区域
     * @param rect 轮廓区域
     */
    public void setProfile(RectF rect) {
        this.profile = rect;
    }


    /**
     * 设置显示的图片
     * @param bitmap 要识别的图片
     */
    public void setImage(Bitmap bitmap) {
        this.image = bitmap;
        this.imgWidth = image.getWidth();
        this.imgHeight =image.getHeight();
        Log.d("TAG", "width-->"+imgWidth);
        Log.d("TAG", "height-->"+imgHeight);
        this.setMeasuredDimension(imgWidth, imgHeight);
        this.measure(imgWidth, imgHeight);
    }

    /**
     * 设置识别出来的信息，如年龄，性别等
     * @param info 识别出来的信息
     */
    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("TAG", "m width-->" + getMeasuredWidth());
        Log.d("TAG", "m height-->"+getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawImage(canvas);
        drawProfile(canvas);
        drawText(canvas);
    }

    private void drawImage(Canvas canvas) {
        if (image!=null) {
            int left = (getMeasuredWidth() - imgWidth) / 2;
            int right = (getMeasuredHeight() - imgHeight) / 2;
            canvas.drawBitmap(image, 0, 0, paint);
        }
    }

    private void drawProfile(Canvas canvas) {
        if (profile!=null) {
            int left = (int)(profile.left*imgWidth/100);
            int top = (int)(profile.top*imgHeight/100);
            int right = (int)(profile.right*imgWidth/100);
            int bottom = (int)(profile.bottom*imgHeight/100);
            Rect rect = new Rect(left, top, right, bottom);
            canvas.drawRect(rect, paint);
        }
    }

    private void drawText(Canvas canvas) {
        if (info!=null) {
            int left = (int)(profile.left*imgWidth/100 + 10);
            int top = (int)(profile.top*imgHeight/100 + 30);
            canvas.drawText(info, left, top, paint);
        }
    }

}
