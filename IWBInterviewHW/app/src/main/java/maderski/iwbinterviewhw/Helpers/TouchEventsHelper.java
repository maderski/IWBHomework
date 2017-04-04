package maderski.iwbinterviewhw.Helpers;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import maderski.iwbinterviewhw.Models.TouchEventModel;

/**
 * Created by Jason on 4/2/17.
 */

public class TouchEventsHelper {
    private static final String TAG = "OnTouchHelper";

    private List<TouchEventModel> mTouchEventModels = new ArrayList<>();

    public TouchEventsHelper(){}

    // Add Touch Event to list
    public void addTouchEvent(int onClickedPosition, float pressure, double area){
        TouchEventModel touchEventModel = new TouchEventModel(onClickedPosition, pressure, area);
        mTouchEventModels.add(touchEventModel);
        Log.d(TAG, "ADDED: "
            + " Position: " + String.valueOf(onClickedPosition)
            + " Pressure: " + String.valueOf(pressure)
            + " Area: " + String.valueOf(area));
    }

    // Remove touch event from list at a specified onClickedPosition
    public void removeTouchEvents(int onClickedPosition){
        for(TouchEventModel touchEventModel : mTouchEventModels){
            if(touchEventModel.getOnClickedPosition() == onClickedPosition){
                int index = mTouchEventModels.indexOf(touchEventModel);
                mTouchEventModels.remove(index);
                Log.d(TAG, "REMOVED onClickedPosition: " + String.valueOf(onClickedPosition)
                    + " at index: " + String.valueOf(index));
            }
        }
    }

    // Remove touch event from list at a specified pressure
    public void removeTouchEvents(float pressure){
        for(TouchEventModel touchEventModel : mTouchEventModels){
            if(touchEventModel.getPressure() == pressure){
                int index = mTouchEventModels.indexOf(touchEventModel);
                mTouchEventModels.remove(index);
                Log.d(TAG, "REMOVED pressure: " + String.valueOf(pressure)
                        + " at index: " + String.valueOf(index));
            }
        }
    }

    // Remove touch event from list at a specified area
    public void removeTouchEvents(double area){
        for(TouchEventModel touchEventModel : mTouchEventModels){
            if(touchEventModel.getArea() == area){
                int index = mTouchEventModels.indexOf(touchEventModel);
                mTouchEventModels.remove(index);
                Log.d(TAG, "REMOVED area: " + String.valueOf(area)
                        + " at index: " + String.valueOf(index));
            }
        }
    }

    // Remove all touch events from list
    public boolean removeAllTouchEvents(){
        mTouchEventModels.clear();
        return mTouchEventModels.isEmpty();
    }

    // Gets the largest area on the list
    public double getLargestArea(){
        double maxArea = 0.0;
        for(TouchEventModel touchEventModel : mTouchEventModels) {
            double area = touchEventModel.getArea();
            if(area > maxArea){
                maxArea = area;
            }
        }
        return maxArea;
    }

    // Gets the largest pressure on the list
    public float getLargestPressure(){
        float maxPressure = 0.0f;
        for(TouchEventModel touchEventModel : mTouchEventModels) {
            float pressure = touchEventModel.getPressure();
            if(pressure > maxPressure){
                maxPressure = pressure;
            }
        }
        return maxPressure;
    }

    // Gets all OnClickPositions that is on the list
    public List<Integer> getAllOnClickPositions(){
        Set<Integer> positions = new HashSet<>();
        for(int i = 0; i < mTouchEventModels.size(); i++){
            positions.add(mTouchEventModels.get(i).getOnClickedPosition());
        }
        return new ArrayList<>(positions);
    }

    // Gets all OnClickPositions with the largest areas
    public List<Integer> getOnClickPositions(double largestArea){
        Set<Integer> positions = new HashSet<>();
        for(TouchEventModel touchEventModel : mTouchEventModels) {
            double touchEventArea = touchEventModel.getArea();
            if(touchEventArea == largestArea){
                positions.add(touchEventModel.getOnClickedPosition());
            }
        }
        return new ArrayList<>(positions);
    }

    // Gets OnClickPosition with the largest pressure
    public int getOnClickPosition(float largestPressure){
        int position = -1;
        for(TouchEventModel touchEventModel : mTouchEventModels) {
            float touchEventPressure = touchEventModel.getPressure();
            if(touchEventPressure == largestPressure){
                position = touchEventModel.getOnClickedPosition();
            }
        }
        Log.d(TAG, "USED PRESSURE");
        return position;
    }
}
