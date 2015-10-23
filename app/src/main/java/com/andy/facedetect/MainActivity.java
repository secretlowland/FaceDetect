package com.andy.facedetect;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andy.facedetect.model.DetectResult;
import com.andy.facedetect.model.FaceAttribute;
import com.andy.facedetect.model.FaceInfo;
import com.andy.facedetect.model.FacePosition;
import com.andy.facedetect.view.FaceView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE_CODE = 1;

    FaceView faceView;
    ProgressBar progressBar;
    Button selectBtn, detectBtn;
    Bitmap imgBitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        faceView = (FaceView) findViewById(R.id.face_view);
        selectBtn = (Button)findViewById(R.id.select_img);
        detectBtn = (Button)findViewById(R.id.start_detect);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);

        initListener();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == SELECT_IMAGE_CODE) {
            try {
                Uri uri = data.getData();
                imgBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                if (imgBitmap!=null) {
                    faceView.clear();
                    faceView.setImage(imgBitmap);
                    faceView.refresh();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initListener() {
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, SELECT_IMAGE_CODE);
            }
        });

        detectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                File cacheDir = getExternalCacheDir();
                String path = null;
                if (cacheDir!=null) {
                    path = cacheDir.getPath();
                }
                File file = new File(path, "image");
                try {
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                FaceDetect faceDetect = new FaceDetect();
                faceDetect.detect(file, detectCallback);
            }
        });
    }

    /*********************************************** Callback *************************************/

    /**
     * 进行图像识别后的回调
     */
    private FaceDetect.Callback detectCallback = new FaceDetect.Callback() {
        @Override
        public void onDetectCompleted(DetectResult result, Response response) {
            List<FaceInfo> faceList = result.getFace();
            if (faceList == null) return;

            FaceInfo faceInfo = faceList.get(0);
            FaceAttribute attribute =faceInfo.getFaceAttribute();
            FacePosition position = faceInfo.getFacePosition();

            String sex =attribute.getGender().getValue();
            int age = attribute.getAge().getValue();

            Log.d("TAG", "age-->"+age);

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
            Log.d("TAG", "error-->"+error);
            Toast.makeText(getApplicationContext(),"识别失败！", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    };

}
