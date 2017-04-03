package maderski.iwbinterviewhw;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jason on 4/2/17.
 */

public class TouchEventsHelper {
    private static final String TAG = "OnTouchHelper";

    private List<TouchEventObject> mTouchEventObjects = new ArrayList<>();

    public TouchEventsHelper(){}

    public void addTouchEvent(int onClickedPosition, float pressure, double area){
        TouchEventObject touchEventObject = new TouchEventObject(onClickedPosition, pressure, area);
        mTouchEventObjects.add(touchEventObject);
        Log.d(TAG, "ADDED: "
            + " Position: " + String.valueOf(onClickedPosition)
            + " Pressure: " + String.valueOf(pressure)
            + " Area: " + String.valueOf(area));
    }

    public void removeTouchEvents(int onClickedPosition){
        for(TouchEventObject touchEventObject : mTouchEventObjects){
            if(touchEventObject.getOnClickedPosition() == onClickedPosition){
                int index = mTouchEventObjects.indexOf(touchEventObject);
                mTouchEventObjects.remove(index);
                Log.d(TAG, "REMOVED onClickedPosition: " + String.valueOf(onClickedPosition)
                    + " at index: " + String.valueOf(index));
            }
        }
    }

    public void removeTouchEvents(float pressure){
        for(TouchEventObject touchEventObject : mTouchEventObjects){
            if(touchEventObject.getPressure() == pressure){
                int index = mTouchEventObjects.indexOf(touchEventObject);
                mTouchEventObjects.remove(index);
                Log.d(TAG, "REMOVED pressure: " + String.valueOf(pressure)
                        + " at index: " + String.valueOf(index));
            }
        }
    }

    public void removeTouchEvents(double area){
        for(TouchEventObject touchEventObject : mTouchEventObjects){
            if(touchEventObject.getArea() == area){
                int index = mTouchEventObjects.indexOf(touchEventObject);
                mTouchEventObjects.remove(index);
                Log.d(TAG, "REMOVED area: " + String.valueOf(area)
                        + " at index: " + String.valueOf(index));
            }
        }
    }

    public boolean removeAllTouchEvents(){
        mTouchEventObjects.clear();
        return mTouchEventObjects.isEmpty();
    }

    public double getLargestArea(){
        double maxArea = 0.0;
        for(TouchEventObject touchEventObject : mTouchEventObjects) {
            double area = touchEventObject.getArea();
            if(area > maxArea){
                maxArea = area;
            }
        }
        return maxArea;
    }

    public float getLargestPressure(){
        float maxPressure = 0.0f;
        for(TouchEventObject touchEventObject : mTouchEventObjects) {
            float pressure = touchEventObject.getPressure();
            if(pressure > maxPressure){
                maxPressure = pressure;
            }
        }
        return maxPressure;
    }

    public List<Integer> getAllOnClickPositions(){
        Set<Integer> positions = new HashSet<>();
        for(int i = 0; i < mTouchEventObjects.size(); i++){
            positions.add(mTouchEventObjects.get(i).getOnClickedPosition());
        }
        return new ArrayList<>(positions);
    }

    public List<Integer> getOnClickPositions(double largestArea){
        Set<Integer> positions = new HashSet<>();
        for(TouchEventObject touchEventObject : mTouchEventObjects) {
            double touchEventArea = touchEventObject.getArea();
            if(touchEventArea == largestArea){
                positions.add(touchEventObject.getOnClickedPosition());
            }
        }
        return new ArrayList<>(positions);
    }

    public int getOnClickPosition(float largestPressure){
        int position = -1;
        for(TouchEventObject touchEventObject : mTouchEventObjects) {
            float touchEventPressure = touchEventObject.getPressure();
            if(touchEventPressure == largestPressure){
                position = touchEventObject.getOnClickedPosition();
            }
        }
        Log.d(TAG, "USED PRESSURE");
        return position;
    }
}
