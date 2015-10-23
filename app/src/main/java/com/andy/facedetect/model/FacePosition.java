package com.andy.facedetect.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lxn on 2015/10/17.
 */
public class FacePosition {

    @SerializedName("width")
    private float width;

    @SerializedName("height")
    private float height;

    @SerializedName("center")
    private Point center;

    @SerializedName("nose")
    private Point nose;

    @SerializedName("eye_left")
    private Point leftEye;

    @SerializedName("eye_right")
    private Point rightEye;

    @SerializedName("mouth_left")
    private Point leftMouth;

    @SerializedName("mouth_right")
    private Point rightMouth;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public void setNose(Point nose) {
        this.nose = nose;
    }

    public void setLeftEye(Point leftEye) {
        this.leftEye = leftEye;
    }

    public void setRightEye(Point rightEye) {
        this.rightEye = rightEye;
    }

    public void setLeftMouth(Point leftMouth) {
        this.leftMouth = leftMouth;
    }

    public void setRightMouth(Point rightMouth) {
        this.rightMouth = rightMouth;
    }

    public float getHeight() {
        return height;
        
    }

    public Point getCenter() {
        return center;
    }

    public Point getNose() {
        return nose;
    }

    public Point getLeftEye() {
        return leftEye;
    }

    public Point getRightEye() {
        return rightEye;
    }

    public Point getLeftMouth() {
        return leftMouth;
    }

    public Point getRightMouth() {
        return rightMouth;
    }
    /*******************************************内部类****************************************/

    /**
     *面部中一些关键位置（如眼睛，鼻子，嘴唇等）的相对位置
     * x & y 坐标分别表示在图片中的宽度和高度的百分比 (0~100之间的实数)
     */
    public class Point {
        @SerializedName("x")
        private float x;

        @SerializedName("y")
        private float y;

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getY() {
            return y;
        }
    }

}
