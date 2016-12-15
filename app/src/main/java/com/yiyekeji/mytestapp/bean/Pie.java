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
            return -1;
        } else if (percent>o.getPercent()){
            return 1;
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
    }

    protected Pie(Parcel in) {
        this.color = in.readInt();
        this.percent = in.readFloat();
        this.label = in.readString();
        this.number = in.readFloat();
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
