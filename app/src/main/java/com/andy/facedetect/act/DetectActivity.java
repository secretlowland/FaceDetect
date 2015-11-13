package com.andy.facedetect.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.facedetect.R;
import com.andy.facedetect.func.FaceDetect;
import com.andy.facedetect.model.DetectResult;
import com.andy.facedetect.model.FaceAttribute;
import com.andy.facedetect.model.FaceInfo;
import com.andy.facedetect.model.FacePosition;
import com.andy.facedetect.view.ProfileView;

import java.io.File;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * This is the activity to detect the image.
 * Created by lxn on 2015/10/24.
 */
public class DetectActivity extends AppCompatActivity {

    private ProfileView profile;
    private TextView faceAttr;
    private ImageView imgView;
    private RelativeLayout imgContainer;
    private ProgressBar progressBar;

    private Bitmap imgBitmap;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);

        imgContainer = (RelativeLayout) findViewById(R.id.img_container);
        imgView = (ImageView) findViewById(R.id.img_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        faceAttr = (TextView) findViewById(R.id.face_attr);

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
        imgView.setImageBitmap(imgBitmap);
    }

    private void startDetect() {
        if (imgPath!=null) {
            File file = new File(imgPath);
            FaceDetect faceDetect = new FaceDetect();
            faceDetect.detect(file, detectCallback);
            progressBar.setVisibility(View.VISIBLE);
        }

    }


    /**
     * Generate the face profile.
     * @param faceCenter the center point of the face profile
     * @param widthPer the width percent of the face profile
     * @param heightPer the height percent of the face profile
     */
    private void populateProfile(FacePosition.Point faceCenter, float widthPer, float heightPer) {
        if (faceCenter==null) return;
        int imgWidth = imgView.getWidth();
        int imgHeight = imgView.getHeight();
        int width = (int)(imgWidth*widthPer/100);
        int height = (int)(imgHeight*heightPer/100);
        int leftMargin = (int)(faceCenter.getX() - widthPer/2)*imgWidth/100;
        int topMargin = (int)(faceCenter.getY() - heightPer/2)*imgHeight/100;

        profile = new ProfileView(this);

        // Add animation
        ScaleAnimation anim = new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,  0.5f);
        anim.setDuration(500);
        profile.startAnimation(anim);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.ALIGN_LEFT, imgView.getId());
        params.addRule(RelativeLayout.ALIGN_TOP, R.id.img_view);
        params.setMargins(leftMargin, topMargin, 0, 0);
        imgContainer.addView(profile, params);
    }

    /**
     * Generate the attribute information of the face
     * @param attr the attribute of the face
     */
    private void populateFaceAttr(FaceAttribute attr) {
        if (attr == null) return;
        StringBuilder attribute = new StringBuilder();

        String gender = attr.getGender().getValue();
        int genderConfidence = (int)attr.getGender().getConfidence();
        int age = attr.getAge().getValue();
        int ageRange = attr.getAge().getRange();
        String race = attr.getRace().getRace();
        // Switch race to Chinese
        switch (race) {
            case "Asian":
                race = "亚洲人";
                break;
            case "White":
                race = "白人";
                break;
            case "Black":
                race = "黑人";
                break;
        }
        int raceConfidence = (int)attr.getRace().getConfidence();
        int smiling = (int)attr.getSmiling().getValue();

        attribute.append("性别：").append(gender.equals("Male") ? "男" : "女").append("(准确率").append(genderConfidence).append("%)\n");
        attribute.append("年龄：").append(age).append("岁\n");
        attribute.append("种族：").append(race).append("(准确率").append(raceConfidence).append("%)\n");
        attribute.append("微笑程度：").append(smiling).append("%\n");

        faceAttr.setText(attribute.toString());

    }


    /*********************************************** Callback *************************************/

    /**
     * 进行图像识别后的回调
     */
    private FaceDetect.Callback detectCallback = new FaceDetect.Callback() {
        @Override
        public void onDetectCompleted(DetectResult result, Response response) {
            Log.d("TAG", "response-->"+response.getBody());
            List<FaceInfo> faceList = null;
            if (result!=null) {
                faceList = result.getFace();
            }
            if (faceList == null || faceList.size() <=0) {
                Toast.makeText(getApplicationContext(), "没有识别到人脸，请保持头像端正", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return;
            }

            FaceInfo faceInfo = faceList.get(0);
            FaceAttribute attribute =faceInfo.getFaceAttribute();
            FacePosition position = faceInfo.getFacePosition();

            progressBar.setVisibility(View.GONE);

            populateProfile(position.getCenter(), position.getWidth(), position.getHeight());
            populateFaceAttr(attribute);

        }

        @Override
        public void onDetectFailed(RetrofitError error) {
            Toast.makeText(getApplicationContext(),"识别失败！", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "识别失败-->"+error);
            progressBar.setVisibility(View.GONE);
        }
    };
}
