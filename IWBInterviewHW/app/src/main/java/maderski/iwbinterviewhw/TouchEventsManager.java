package maderski.iwbinterviewhw;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import maderski.iwbinterviewhw.Helpers.CaptureTouchEventsHelper;
import maderski.iwbinterviewhw.Helpers.TouchEventsHelper;
import maderski.iwbinterviewhw.Helpers.ViewRectHelper;
import maderski.iwbinterviewhw.Models.TouchEventModel;
import maderski.iwbinterviewhw.Models.ViewRectModel;

/**
 * Created by Jason on 4/8/17.
 */

public class TouchEventsManager implements CaptureTouchEventsHelper.OnTouchListener {
    private static final String TAG = "TouchEventsManager";

    public interface PositionCallbacks{
        void choosenPosition(int position);
        void pressedPositions(List<Integer> positions);
    }

    private TouchEventsHelper mTouchEventsHelper;
    private ViewRectHelper mRectangleHelper;
    private PositionCallbacks mPositionCallbacks;
    private HashMap<Integer, RectangleTouchEvent> mRectangleTouchEvents = new HashMap<>();

    public TouchEventsManager(TouchEventsHelper touchEventsHelper, ViewRectHelper rectangleHelper,
                              PositionCallbacks positionCallback ){
        mTouchEventsHelper = touchEventsHelper;
        mRectangleHelper = rectangleHelper;
        mPositionCallbacks = positionCallback;
    }

    @Override
    public void currentTouchEvents(HashMap<Integer, TouchEventModel> touchEvents) {
        for (Object object : touchEvents.entrySet()) {
            Map.Entry pair = (Map.Entry) object;
            int pointerPositionId = (int) pair.getKey();
            TouchEventModel touchEvent = (TouchEventModel) pair.getValue();

            mTouchEventsHelper.addTouchEvent(pointerPositionId, touchEvent);
            List<RectangleTouchEvent> rectangleTouchEvents = getTouchedRectangles(touchEvent);
            List<Integer> touchingRectangles = getPositionsList(rectangleTouchEvents);
            mPositionCallbacks.pressedPositions(touchingRectangles);

            for(RectangleTouchEvent rectangleTouchEvent : rectangleTouchEvents) {
                mRectangleTouchEvents.put(pointerPositionId, rectangleTouchEvent);
            }

        }
        startSearchforPosition();
    }

    private void startSearchforPosition(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                findChoosenPosition(mRectangleTouchEvents);
            }
        };
        handler.postDelayed(runnable, 500);
    }

    private List<Integer> getPositionsList(List<RectangleTouchEvent> list){
        List<Integer> positionList = new ArrayList<>();
        for(RectangleTouchEvent rectangleTouchEvent : list){
            positionList.add(rectangleTouchEvent.getRectanglePosition());
        }

        return positionList;
    }

    private void findChoosenPosition(HashMap<Integer, RectangleTouchEvent> rectangleTouchEventList){
        Log.d(TAG, "CHOOSEN...");
        for(Object object : rectangleTouchEventList.entrySet()){
            Map.Entry pair = (Map.Entry) object;
            RectangleTouchEvent rectangleTouchEvent = (RectangleTouchEvent) pair.getValue();
            int position = rectangleTouchEvent.getRectanglePosition();
            int pointerPositionId = (int) pair.getKey();
            TouchEventModel touchEvent = rectangleTouchEvent.getTouchEvent();
            Log.d(TAG, "CHOOSEN ID: " + String.valueOf(pointerPositionId) + " P: " + String.valueOf(position) + " A: " + String.valueOf(touchEvent.getArea()));
        }
        mRectangleTouchEvents.clear();
    }

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

//        Log.d(TAG, "IS TOUCHING: " + String.valueOf(isTouching) + " Position: " + String.valueOf(position));
        return isTouching;
    }

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
