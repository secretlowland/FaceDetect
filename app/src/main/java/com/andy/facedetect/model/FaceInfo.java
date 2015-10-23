package com.andy.facedetect.model;

import com.google.gson.annotations.SerializedName;

/**
 * 面部信息
 * Created by lxn on 2015/10/17.
 */
public class FaceInfo {

    @SerializedName("attribute")
    private FaceAttribute faceAttribute;

    @SerializedName("face_id")
    private String faceId;

    @SerializedName("position")
    private FacePosition facePosition;

    @SerializedName("tag")
    private String tag;

    public FacePosition getFacePosition() {
        return facePosition;
    }

    public String getTag() {
        return tag;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFacePosition(FacePosition facePosition) {
        this.facePosition = facePosition;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public void setFaceAttribute(FaceAttribute faceAttribute) {
        this.faceAttribute = faceAttribute;
    }

    public FaceAttribute getFaceAttribute() {
        return faceAttribute;
    }
}
