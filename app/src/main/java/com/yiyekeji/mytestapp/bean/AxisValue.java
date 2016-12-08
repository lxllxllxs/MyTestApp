package com.yiyekeji.mytestapp.bean;

/**
 * Created by lxl on 2016/12/7.
 */
public class AxisValue {
    int X;
    int Y;

    String XLabel;
    public String getXLabel() {
        return XLabel;
    }

    public AxisValue(){
        XLabel=X+"";

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

    String YLabel;

    public int getX() {
        return X;
    }

    public AxisValue setX(int x) {
        X = x;
        XLabel = X+"";
        return this;
    }

    public int getY() {
        return Y;
    }

    public AxisValue setY(int y) {
        Y = y;
        YLabel = Y+"";
        return this;
    }
}
