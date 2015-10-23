package com.andy.facedetect.api;


import com.andy.facedetect.model.DetectResult;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * 获取图片识别信息的接口
 * Created by lxn on 2015/10/17.
 */
public interface FaceDetectAPI {

    String API_URL = "http://api.cn.faceplusplus.com";
    String API_KEY = "d91b4f39e0e021d5bf20e1e373e03b66";
    String API_SECRET = "p2y6wflq5lFLad58_wiFFw1bIdms3a7t";

    @GET("/detection/detect")
    public void getFaceInfo(
            @Query("api_key") String apiKey,
            @Query("api_secret") String apkSecret,
            @Query("url") String url,
            Callback<DetectResult> callback
    );

    @Multipart
    @POST("/detection/detect")
    public void getFaceInfo(
            @Query("api_key") String apiKey,
            @Query("api_secret") String apiSecret,
            @Part("img") TypedFile file,
            Callback<DetectResult> callback
    );


}
