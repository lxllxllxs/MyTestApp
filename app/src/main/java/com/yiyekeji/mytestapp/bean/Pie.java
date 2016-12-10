package com.yiyekeji.mytestapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lxl on 2016/12/10.
 * 需要：
 * 颜色、占比（用来存计算之后的），数量，标签
 *
 */
public class Pie implements Parcelable {
    private String color;
    private double percent;
    private  String label;
    private  int number;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(int total) {
        this.percent = number/total;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.color);
        dest.writeDouble(this.percent);
        dest.writeString(this.label);
        dest.writeInt(this.number);
    }

    public Pie() {
    }

    protected Pie(Parcel in) {
        this.color = in.readString();
        this.percent = in.readDouble();
        this.label = in.readString();
        this.number = in.readInt();
    }

    public static final Parcelable.Creator<Pie> CREATOR = new Parcelable.Creator<Pie>() {
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
