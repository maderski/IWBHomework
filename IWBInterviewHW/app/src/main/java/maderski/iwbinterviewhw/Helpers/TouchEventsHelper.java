package maderski.iwbinterviewhw.Helpers;

import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import maderski.iwbinterviewhw.Models.TouchEventModel;

/**
 * Created by Jason on 4/2/17.
 */

public class TouchEventsHelper {
    private static final String TAG = "OnTouchHelper";

    private HashMap<Integer, TouchEventModel> mTouchEventModels = new HashMap<>();

    public TouchEventsHelper(){}

    // Add Touch Event to list
    public void addTouchEvent(int onClickedPosition, float x, float y, float majorAxis,
                              float minorAxis, float pressure){
        Double area = Math.PI * majorAxis * minorAxis;
        TouchEventModel touchEventModel = new TouchEventModel(x, y, majorAxis, minorAxis, pressure, area);
        mTouchEventModels.put(onClickedPosition, touchEventModel);
        Log.d(TAG, "ADDED: "
            + " Position: " + String.valueOf(onClickedPosition)
            + " Pressure: " + String.valueOf(pressure)
            + " Area: " + String.valueOf(area));
    }

    // Remove touch event from list at a specified onClickedPosition
    public void removeTouchEvents(int onClickedPosition){
        mTouchEventModels.remove(onClickedPosition);
        Log.d(TAG, "REMOVED onClickedPosition: " + String.valueOf(onClickedPosition));
    }

    // Remove touch event from list at a specified pressure
    public void removeTouchEvents(float pressure){
        for (Object object : mTouchEventModels.entrySet()) {
            Map.Entry pair = (Map.Entry) object;
            int positionId = (int) pair.getKey();
            TouchEventModel touchEventModel = (TouchEventModel) pair.getValue();

            if(touchEventModel.getPressure() == pressure){
                mTouchEventModels.remove(positionId);
                Log.d(TAG, "REMOVED pressure: " + String.valueOf(pressure)
                        + " at key: " + String.valueOf(positionId));
            }
        }
    }

    // Remove touch event from list at a specified area
    public void removeTouchEvents(double area){
        for (Object object : mTouchEventModels.entrySet()) {
            Map.Entry pair = (Map.Entry) object;
            int positionId = (int) pair.getKey();
            TouchEventModel touchEventModel = (TouchEventModel) pair.getValue();

            if(touchEventModel.getArea() == area){
                mTouchEventModels.remove(positionId);
                Log.d(TAG, "REMOVED area: " + String.valueOf(area)
                        + " at key: " + String.valueOf(positionId));
            }
        }
    }

    // Remove all touch events from list
    public boolean removeAllTouchEvents(){
        mTouchEventModels.clear();
        return mTouchEventModels.size() == 0;
    }

    // Gets the largest area on the list
    public double getLargestArea(){
        double maxArea = 0.0;

        for (Object object : mTouchEventModels.entrySet()) {
            Map.Entry pair = (Map.Entry) object;
            int positionId = (int) pair.getKey();
            TouchEventModel touchEventModel = (TouchEventModel) pair.getValue();

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

        for (Object object : mTouchEventModels.entrySet()) {
            Map.Entry pair = (Map.Entry) object;
            int positionId = (int) pair.getKey();
            TouchEventModel touchEventModel = (TouchEventModel) pair.getValue();

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

        for (Object object : mTouchEventModels.entrySet()) {
            Map.Entry pair = (Map.Entry) object;
            int positionId = (int) pair.getKey();
            positions.add(positionId);
        }
        return new ArrayList<>(positions);
    }

    // Gets all OnClickPositions with the largest areas
    public List<Integer> getOnClickPositions(double largestArea){
        Set<Integer> positions = new HashSet<>();

        for (Object object : mTouchEventModels.entrySet()) {
            Map.Entry pair = (Map.Entry) object;
            int positionId = (int) pair.getKey();
            TouchEventModel touchEventModel = (TouchEventModel) pair.getValue();

            double touchEventArea = touchEventModel.getArea();
            if(touchEventArea == largestArea){
                positions.add(positionId);
            }
        }
        return new ArrayList<>(positions);
    }

    // Gets OnClickPosition with the largest pressure
    public int getOnClickPosition(float largestPressure){
        int position = -1;

        for (Object object : mTouchEventModels.entrySet()) {
            Map.Entry pair = (Map.Entry) object;
            int positionId = (int) pair.getKey();
            TouchEventModel touchEventModel = (TouchEventModel) pair.getValue();

            float touchEventPressure = touchEventModel.getPressure();
            if(touchEventPressure == largestPressure){
                position = positionId;
            }
        }
        Log.d(TAG, "USED PRESSURE");
        return position;
    }

    public HashMap<Integer, TouchEventModel> getTouchEventModels() {
        return mTouchEventModels;
    }
}
