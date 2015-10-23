package com.andy.facedetect.model;

import com.google.gson.annotations.SerializedName;

/**
 * 面部属性，包含一系列人脸的属性分析结果
 * Created by lxn on 2015/10/17.
 */
public class FaceAttribute {

    @SerializedName("age")
    private Age age;

    @SerializedName("gender")
    private Gender gender;

    public Age getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }



    /*******************************************内部类****************************************/

    /**
     * 包含年龄分析结果，value的值为一个非负整数表示估计的年龄, range表示估计年龄的正负区间
     */
    public class Age {
        @SerializedName("range")
        private int range;

        @SerializedName("value")
        private int value;

        public int getRange() {
            return range;
        }

        public void setRange(int range) {
            this.range = range;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {

            return value;
        }
    }



    /**
     * 包含性别分析结果，value的值为Male/Female, confidence表示置信度
     */
    public class Gender {
        @SerializedName("confidence")
        private float confidence;

        @SerializedName("value")
        private String value;

        public void setConfidence(float confidence) {
            this.confidence = confidence;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;

        }

        public float getConfidence() {
            return confidence;
        }
    }
}
