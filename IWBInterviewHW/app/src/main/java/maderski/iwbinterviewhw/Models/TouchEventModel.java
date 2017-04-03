package maderski.iwbinterviewhw.Models;

/**
 * Created by Jason on 4/1/17.
 */

public class TouchEventModel {

    private int mOnClickedPosition;
    private float mPressure;
    private double mArea;

    public TouchEventModel(int onClickedPosition, float pressure, double area) {
        mOnClickedPosition = onClickedPosition;
        mPressure = pressure;
        mArea = area;
    }

    public int getOnClickedPosition(){
        return mOnClickedPosition;
    }

    public float getPressure(){
        return mPressure;
    }

    public double getArea(){
        return mArea;
    }
}
