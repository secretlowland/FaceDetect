package com.andy.facedetect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andy.facedetect.model.DetectResult;
import com.andy.facedetect.model.FaceAttribute;
import com.andy.facedetect.model.FaceInfo;
import com.andy.facedetect.model.FacePosition;
import com.andy.facedetect.view.FaceView;

import java.io.File;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * This is the activity to detect the image.
 * Created by lxn on 2015/10/24.
 */
public class DetectActivity extends AppCompatActivity {

    private FaceView faceView;
    private ProgressBar progressBar;

    private Bitmap imgBitmap;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);

        faceView = (FaceView) findViewById(R.id.face_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        init();
        showBitmap();
        startDetect();
    }


    void init() {
        Intent intent = getIntent();
        if (intent!=null) {
            imgPath = intent.getStringExtra("image_path");
            prepareBitmap(imgPath);
        }
    }


    /**
     * Create a bitmap with a proper size according to given image path
     * @param path The path of the image
     */
    private void prepareBitmap (String path) {
        if (path == null) return;
        BitmapFactory.Options options = new BitmapFactory.Options();

        // Just to get the size and type info of the bitmap
        options.inJustDecodeBounds = true;
        imgBitmap = BitmapFactory.decodeFile(path, options);

        // Scale the bitmap to a proper size
        options.inSampleSize = Math.max(1, (int) Math.ceil(Math.max((double) options.outWidth / 1024f, (double) options.outHeight / 1024f)));
        options.inJustDecodeBounds = false;
        imgBitmap = BitmapFactory.decodeFile(path, options);
    }

    private void showBitmap() {
        if (imgBitmap !=null) {
            faceView.setImage(imgBitmap);
        }
    }

    private void startDetect() {
        if (imgPath!=null) {
            File file = new File(imgPath);
            FaceDetect faceDetect = new FaceDetect();
            faceDetect.detect(file, detectCallback);
            progressBar.setVisibility(View.VISIBLE);
        }

    }


    /*********************************************** Callback *************************************/

    /**
     * 进行图像识别后的回调
     */
    private FaceDetect.Callback detectCallback = new FaceDetect.Callback() {
        @Override
        public void onDetectCompleted(DetectResult result, Response response) {
            List<FaceInfo> faceList = result.getFace();
            if (faceList == null || faceList.size() <=0) {
                Toast.makeText(getApplicationContext(), "没有识别到人脸", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            FaceInfo faceInfo = faceList.get(0);
            FaceAttribute attribute =faceInfo.getFaceAttribute();
            FacePosition position = faceInfo.getFacePosition();

            String sex =attribute.getGender().getValue();
            int age = attribute.getAge().getValue();

            int imgWidth = result.getImgWidth();
            int imgHeight = result.getImgHeight();
            float profileWidth = position.getWidth();
            float profileHeight = position.getHeight();
            FacePosition.Point center = position.getCenter();

            // 此处传的值均为相对图片的比例
            RectF rectF = new RectF();
            rectF.left = center.getX() - profileWidth/2;
            rectF.top = center.getY() - profileHeight/2;
            rectF.right = center.getX() + profileWidth/2;
            rectF.bottom = center.getY() + profileHeight/2;

            faceView.setInfo(sex + " " + age + "岁");
            faceView.setProfile(rectF);
            faceView.refresh();
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onDetectFailed(RetrofitError error) {
            Toast.makeText(getApplicationContext(),"识别失败！", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    };
}
