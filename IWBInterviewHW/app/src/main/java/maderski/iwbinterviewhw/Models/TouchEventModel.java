package maderski.iwbinterviewhw.Models;

/**
 * Created by Jason on 4/1/17.
 */

public class TouchEventModel {

    private float mX;
    private float mY;
    private float mMajorAxis;
    private float mMinorAxis;
    private float mPressure;
    private double mArea;

    public TouchEventModel(float x, float y, float majorAxis, float minorAxis,
                           float pressure, double area) {
        mX = x;
        mY = y;
        mMajorAxis = majorAxis;
        mMinorAxis = minorAxis;
        mPressure = pressure;
        mArea = area;
    }

    public float getX() {
        return mX;
    }

    public float getY() {
        return mY;
    }

    public float getMajorAxis() {
        return mMajorAxis;
    }

    public float getMinorAxis() {
        return mMinorAxis;
    }

    public float getPressure(){
        return mPressure;
    }

    public double getArea(){
        return mArea;
    }
}
