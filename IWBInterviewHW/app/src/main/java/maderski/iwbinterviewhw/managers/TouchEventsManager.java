package maderski.iwbinterviewhw.managers;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maderski.iwbinterviewhw.helpers.CaptureTouchEventsHelper;
import maderski.iwbinterviewhw.helpers.ViewRectHelper;
import maderski.iwbinterviewhw.models.TouchEventModel;
import maderski.iwbinterviewhw.models.ViewRectModel;

/**
 * Created by Jason on 4/8/17.
 */

public class TouchEventsManager implements CaptureTouchEventsHelper.OnTouchListener {
    private static final String TAG = "TouchEventsManager";

    public interface PositionCallbacks{
        void choosenPosition(int position);
        void pressedPositions(List<Integer> positions);
    }

    private ViewRectHelper mRectangleHelper;
    private PositionCallbacks mPositionCallbacks;
    private HashMap<Integer, RectangleTouchEvent> mRectangleTouchEvents = new HashMap<>();

    public TouchEventsManager(ViewRectHelper rectangleHelper,
                              PositionCallbacks positionCallback ){
        mRectangleHelper = rectangleHelper;
        mPositionCallbacks = positionCallback;
    }

    // Called when a touch event occurs
    @Override
    public void currentTouchEvents(HashMap<Integer, TouchEventModel> touchEvents) {
        for (Object object : touchEvents.entrySet()) {
            Map.Entry pair = (Map.Entry) object;
            int pointerPositionId = (int) pair.getKey();
            TouchEventModel touchEvent = (TouchEventModel) pair.getValue();

            List<RectangleTouchEvent> rectangleTouchEvents = getTouchedRectangles(touchEvent);
            List<Integer> touchingRectangles = getPositionsList(rectangleTouchEvents);
            mPositionCallbacks.pressedPositions(touchingRectangles);

            for(RectangleTouchEvent rectangleTouchEvent : rectangleTouchEvents) {
                mRectangleTouchEvents.put(pointerPositionId, rectangleTouchEvent);
            }

        }
        searchforChoosenPosition();
    }

    // Adds delay before looking for the position
    private void searchforChoosenPosition(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int choosenPosition = findChoosenPosition(mRectangleTouchEvents);
                if(choosenPosition != -1) {
                    mPositionCallbacks.choosenPosition(choosenPosition);
                }
            }
        };
        handler.postDelayed(runnable, 750);
    }

    // Converts the list with RectangleTouchEvents to a list with Rectangle Positions
    private List<Integer> getPositionsList(List<RectangleTouchEvent> list){
        List<Integer> positionList = new ArrayList<>();
        for(RectangleTouchEvent rectangleTouchEvent : list){
            positionList.add(rectangleTouchEvent.getRectanglePosition());
        }

        return positionList;
    }

    // Finds the position based on the largest area or largest combined areas if two or more touches
    // occurred within the same rectangle
    private int findChoosenPosition(HashMap<Integer, RectangleTouchEvent> rectangleTouchEventList){
        HashMap<Integer, Double> areas = new HashMap<>();
        Log.d(TAG, "Get AREAS...");
        for(Object object : rectangleTouchEventList.entrySet()){
            Map.Entry pair = (Map.Entry) object;
            RectangleTouchEvent rectangleTouchEvent = (RectangleTouchEvent) pair.getValue();
            int position = rectangleTouchEvent.getRectanglePosition();
            int pointerPositionId = (int) pair.getKey();
            TouchEventModel touchEvent = rectangleTouchEvent.getTouchEvent();
            double area = touchEvent.getArea();

            if(areas.containsKey(position)){
                double storedArea = areas.get(position);
                area += storedArea;
            }
            areas.put(position, area);

            Log.d(TAG, "AREA ID: " + String.valueOf(pointerPositionId)
                    + " P: " + String.valueOf(position)
                    + " A: " + String.valueOf(area));

        }
        int choosenPosition = getLargestAreaPosition(areas);
        mRectangleTouchEvents.clear();
        return choosenPosition;
    }

    // Gets the largest area on the list
    private int getLargestAreaPosition(HashMap<Integer, Double> areas){
        double maxArea = 0.0;
        int maxAreaPosition = -1;
        for (Object object : areas.entrySet()) {
            Map.Entry pair = (Map.Entry) object;

            double area = (Double)pair.getValue();
            if(area > maxArea){
                maxAreaPosition = (int) pair.getKey();
                maxArea = area;
            }
        }
        return maxAreaPosition;
    }

    // Gets touched rectangles
    private List<RectangleTouchEvent> getTouchedRectangles(TouchEventModel touchEvent) {
        List<RectangleTouchEvent> rectangleTouchEventList = new ArrayList<>();
        HashMap<Integer, ViewRectModel> Rectangles = mRectangleHelper.getViewRectangles();
        for(Object object : Rectangles.entrySet()){
            Map.Entry pair = (Map.Entry) object;
            ViewRectModel rectangle = (ViewRectModel) pair.getValue();
            boolean doesTouchRectangle = doesTouchRectangle(touchEvent, rectangle);
            if(doesTouchRectangle){
                int position = (int) pair.getKey();
                rectangleTouchEventList.add(new RectangleTouchEvent(position, touchEvent));
            }
        }
        return rectangleTouchEventList;
    }

    // Checks if a touch event falls inside of a rectangle
    private boolean doesTouchRectangle(TouchEventModel touchEvent, ViewRectModel rectangle){
        float x = touchEvent.getX();
        float y = touchEvent.getY();
        float semiMajorAxis = touchEvent.getMajorAxis()/2;
        float semiMinorAxis = touchEvent.getMinorAxis()/2;
        float ellipseVertexTop = y + semiMajorAxis;
        float ellipseVertexBottom = y - semiMajorAxis;
        float ellipseCovertexRight = x + semiMinorAxis;
        float ellipseCovertexLeft = x - semiMinorAxis;

        boolean isTouching = false;

        // Checks the top part of the ellipse
        if(ellipseVertexTop > rectangle.getTop() && ellipseVertexTop < rectangle.getBottom()){
            if(ellipseCovertexLeft > rectangle.getLeft() && ellipseCovertexRight < rectangle.getRight()){
                isTouching = true;
            }
        }
        // Checks the bottom part of the ellipse
        if(ellipseVertexBottom > rectangle.getTop() && ellipseVertexBottom < rectangle.getBottom()){
            if(ellipseCovertexLeft > rectangle.getLeft() && ellipseCovertexRight < rectangle.getRight()){
                isTouching = true;
            }
        }
        // Checks the left part of the ellipse
        if(ellipseCovertexLeft > rectangle.getLeft() && ellipseCovertexLeft < rectangle.getRight()){
            if(ellipseVertexTop > rectangle.getTop() && ellipseVertexTop < rectangle.getBottom()){
                isTouching = true;
            }
        }
        // Checks the right part of the ellipse
        if(ellipseCovertexRight > rectangle.getLeft() && ellipseCovertexRight < rectangle.getRight()){
            if(ellipseVertexTop > rectangle.getTop() && ellipseVertexTop < rectangle.getBottom()){
                isTouching = true;
            }
        }

        return isTouching;
    }

    // Associate a rectangle position with a touch event
    private class RectangleTouchEvent {
        private int rectanglePosition;
        private TouchEventModel touchEvent;

        public RectangleTouchEvent(int rectanglePosition, TouchEventModel touchEvent){
            this.rectanglePosition = rectanglePosition;
            this.touchEvent = touchEvent;
        }

        public int getRectanglePosition() {
            return rectanglePosition;
        }

        public TouchEventModel getTouchEvent() {
            return touchEvent;
        }
    }
}
