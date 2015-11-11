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

    @SerializedName("race")
    private Race race;

    @SerializedName("smiling")
    private Smiling smiling;

    @SerializedName("glass")
    private Glass glass;

    @SerializedName("pose")
    private Pose pose;

    public void setRace(Race race) {
        this.race = race;
    }

    public void setSmiling(Smiling smiling) {
        this.smiling = smiling;
    }

    public void setGlass(Glass glass) {
        this.glass = glass;
    }

    public void setPose(Pose pose) {
        this.pose = pose;
    }

    public Race getRace() {
        return race;
    }

    public Smiling getSmiling() {
        return smiling;
    }

    public Glass getGlass() {
        return glass;
    }

    public Pose getPose() {
        return pose;
    }

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


    /**
     * 包含眼镜佩戴分析结果，value的值为None/Dark/Normal, confidence表示置信度
     */
    public class Glass {
        @SerializedName("confidence")
        private float confidence;

        @SerializedName("value")
        private String value;

        public float getConfidence() {
            return confidence;
        }

        public String getValue() {
            return value;
        }

        public void setConfidence(float confidence) {
            this.confidence = confidence;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    /**
     * 包含人种分析结果，value的值为Asian/White/Black, confidence表示置信度
     */
    public class Race {
        @SerializedName("confidence")
        private float confidence;

        @SerializedName("value")
        private String race;

        public void setConfidence(float confidence) {
            this.confidence = confidence;
        }

        public void setRace(String race) {
            this.race = race;
        }

        public float getConfidence() {
            return confidence;
        }

        public String getRace() {
            return race;
        }
    }

    /**
     * 包含脸部姿势分析结果，包括pitch_angle, roll_angle, yaw_angle，分别对应抬头，旋转（平面旋转），摇头。单位为角度。
     */
    public class Pose {
        @SerializedName("pitch_angle")
        private float pitchAngle;

        @SerializedName("roll_angle")
        private float rollAngle;

        @SerializedName("yaw_angle")
        private float yewAngle;

        public void setPitchAngle(float pitchAngle) {
            this.pitchAngle = pitchAngle;
        }

        public void setRollAngle(float rollAngle) {
            this.rollAngle = rollAngle;
        }

        public void setYewAngle(float yewAngle) {
            this.yewAngle = yewAngle;
        }

        public float getPitchAngle() {
            return pitchAngle;
        }

        public float getRollAngle() {
            return rollAngle;
        }

        public float getYewAngle() {
            return yewAngle;
        }
    }

    /**
     * 包含微笑程度分析结果，value的值为0－100的实数，越大表示微笑程度越高
     */
    public class Smiling {
        @SerializedName("value")
        private float value;

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }
    }

}
