package com.andy.facedetect;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    // Define the request code of Intent
    private static final int SELECT_FROM_GALLERY = 1;
    private static final int SELECT_FROM_CAMERA = 2;

    // Define the directory of image captured by camera
    private static final String CAMERA_CAPTURE_CACHE_NAME = "temp.jpg";

    private Button fromGallery, fromCamera;
    private String imgPath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromGallery = (Button)findViewById(R.id.from_gallery);
        fromCamera = (Button)findViewById(R.id.from_camera);

        initListener();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("TAG", "request code -->"+requestCode);
        Log.d("TAG", "result code -->"+resultCode);
        if (resultCode != RESULT_OK ) {
            if (resultCode == RESULT_CANCELED) {
                return;
            }
            Toast.makeText(this, "获取图片失败！", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode) {
            case SELECT_FROM_GALLERY:
                Uri imgUri = data.getData();
                if (imgUri != null) {
                    final String[] projection = new String[] {MediaStore.Images.ImageColumns.DATA};
                    Cursor c = getContentResolver().query(imgUri, projection, null, null, null);
                    if (c!=null) {
                        c.moveToFirst();
                        int index = c.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        imgPath = c.getString(index);
                        c.close();
                    }
                }
                break;
            case SELECT_FROM_CAMERA:

                break;
            default:break;
        }

        // Jump to detect activity
        Intent intent = new Intent();
        intent.setClass(this, DetectActivity.class);
        intent.putExtra("image_path", imgPath);
        startActivity(intent);
    }

    private void initListener() {
        fromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, SELECT_FROM_GALLERY);
            }
        });
        fromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPath = getCacheImgPath();
                if (imgPath !=null) {
                    Uri imgUri = Uri.fromFile(new File(imgPath));
                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                    startActivityForResult(i, SELECT_FROM_CAMERA);
                } else {
                    Toast.makeText(MainActivity.this, "获取缓存目录失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 获取通过相机拍照的缓存路径
     * @return  缓存路径
     */
    private String getCacheImgPath() {
        File cacheDir = getExternalCacheDir();
        if (cacheDir == null) {
            cacheDir = getCacheDir();
        }
        return cacheDir == null ? null : cacheDir.getPath() + "/" + CAMERA_CAPTURE_CACHE_NAME;
    }


}
