package maderski.iwbinterviewhw;

/**
 * Created by Jason on 4/1/17.
 */

public class TouchEventObject {

    private int mOnClickedPosition;
    private float mPressure;
    private double mArea;

    public TouchEventObject(int onClickedPosition, float pressure, double area) {
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
