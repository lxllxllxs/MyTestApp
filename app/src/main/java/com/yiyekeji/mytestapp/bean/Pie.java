package com.yiyekeji.mytestapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lxl on 2016/12/10.
 * 需要：
 * 颜色、占比（用来存计算之后的），数量，标签
 *
 */
public class Pie implements Parcelable , Comparable<Pie> {
    private int color;
    private float percent;
    private  String label;
    private  float number;
    private boolean isShadow;

    //在圆弧上的数子的坐标
    private double numPostionX;
    private double numPostionY;


    public double getNumPostionX() {
        return numPostionX;
    }

    public void setNumPostionX(double numPostionX) {
        this.numPostionX = numPostionX;
    }

    public double getNumPostionY() {
        return numPostionY;
    }

    public void setNumPostionY(double numPostionY) {
        this.numPostionY = numPostionY;
    }


    public boolean isShadow() {
        return isShadow;
    }

    public void setShadow(boolean shadow) {
        isShadow = shadow;
    }

    public float getAngleStart() {

        return angleStart;
    }

    public void setAngleStart(float angleStart) {
        this.angleStart = angleStart;
    }

    public float getAngleEnd() {
        return angleEnd;
    }

    public void setAngleEnd(float angleEnd) {
        this.angleEnd = angleEnd;
    }

    private float angleStart;
    private float angleEnd;

    private  float X1;//对应Rect的left
    private  float X2;//对应Rect的right

    public void setNumber(float number) {
        this.number = number;
    }

    public float getX1() {
        return X1;
    }

    public void setX1(float x1) {
        X1 = x1;
    }

    public float getX2() {
        return X2;
    }

    public void setX2(float x2) {
        X2 = x2;
    }

    public float getY1() {
        return Y1;
    }

    public void setY1(float y1) {
        Y1 = y1;
    }

    public float getY2() {
        return Y2;
    }

    public void setY2(float y2) {
        Y2 = y2;
    }

    private  float Y1;//对应Rect的top
    private  float Y2;//对应Rect的bottom



    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float total) {
        this.percent = number/total;
    }

    public void setDirectPercent(float percent) {
        this.percent = percent;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Pie() {
    }

    @Override
    public int compareTo(Pie o) {
        if (percent < o.getPercent()) {
            return 1;
        } else if (percent>o.getPercent()){
            return -1;
        }else {
            return 0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.color);
        dest.writeFloat(this.percent);
        dest.writeString(this.label);
        dest.writeFloat(this.number);
        dest.writeFloat(this.angleStart);
        dest.writeFloat(this.angleEnd);
        dest.writeFloat(this.X1);
        dest.writeFloat(this.X2);
        dest.writeFloat(this.Y1);
        dest.writeFloat(this.Y2);
    }

    protected Pie(Parcel in) {
        this.color = in.readInt();
        this.percent = in.readFloat();
        this.label = in.readString();
        this.number = in.readFloat();
        this.angleStart = in.readFloat();
        this.angleEnd = in.readFloat();
        this.X1 = in.readFloat();
        this.X2 = in.readFloat();
        this.Y1 = in.readFloat();
        this.Y2 = in.readFloat();
    }

    public static final Creator<Pie> CREATOR = new Creator<Pie>() {
        @Override
        public Pie createFromParcel(Parcel source) {
            return new Pie(source);
        }

        @Override
        public Pie[] newArray(int size) {
            return new Pie[size];
        }
    };
}
