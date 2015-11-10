package com.andy.facedetect.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lxn on 2015/10/17.
 */
public class DetectResult {

    @SerializedName("face")
    private List<FaceInfo> face;

    @SerializedName("img_id")
    private String imgId;

    @SerializedName("img_width")
    private int imgWidth;

    @SerializedName("img_height")
    private int imgHeight;

    @SerializedName("url")
    private String url;

    @SerializedName("session_id")
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }



    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getImgId() {
        return imgId;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public String getUrl() {
        return url;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<FaceInfo> getFace() {
        return face;
    }

    public void setFace(List<FaceInfo> face) {
        this.face = face;
    }
}
