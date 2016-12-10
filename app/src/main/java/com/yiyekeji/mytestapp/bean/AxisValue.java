package com.yiyekeji.mytestapp.bean;


/**
 * Created by lxl on 2016/12/7.
 */
public class AxisValue {
    Object X;
    int Y;

    String XLabel;

    public String getXLabel() {
        return XLabel;
    }

    public AxisValue() {
        XLabel = X + "";

    }

    public AxisValue setXLabel(Object XLabel) {
        this.XLabel = XLabel.toString();
        return this;
    }

    public String getYLabel() {
        return YLabel;
    }

    public AxisValue setYLabel(Object YLabel) {
        this.YLabel = YLabel.toString();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AxisValue axisValue = (AxisValue) o;
        return X != null ? X.equals(axisValue.X) : axisValue.X == null;
    }

    @Override
    public int hashCode() {
        return X != null ? X.hashCode() : 0;
    }

    String YLabel;

    public Object getX() {
        return X;
    }

    public AxisValue setX(Object x) {
        X = x;
        return this;
    }

    public int getY() {
        return Y;
    }

    public AxisValue setY(int y) {
        Y = y;
        return this;
    }


}