package maderski.iwbinterviewhw;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public TouchEventsManager(TouchEventsHelper touchEventsHelper, ViewRectHelper rectangleHelper){
        mTouchEventsHelper = touchEventsHelper;
        mRectangleHelper = rectangleHelper;
    }

    public void setPositionCallbacks(PositionCallbacks callback){
        mPositionCallbacks = callback;
    }

    @Override
    public void currentTouchEvents(HashMap<Integer, TouchEventModel> touchEvents) {
        for (Object object : touchEvents.entrySet()) {
            Map.Entry pair = (Map.Entry) object;
            int positionId = (int) pair.getKey();
            TouchEventModel touchEvent = (TouchEventModel) pair.getValue();
            //Log.d(TAG, "POSITION ID: " + String.valueOf(positionId) + " area: " + String.valueOf(touchEventModel.getArea()));
            List<Integer> touchingRectangles = getTouchedRectangle(touchEvent);
            for (Integer position : touchingRectangles){
                Log.d(TAG, "TOUCHING POSITION: " + String.valueOf(position));
            }
        }
    }

    private List<Integer> getTouchedRectangle(TouchEventModel touchEvent) {
        List<Integer> positions = new ArrayList<>();
        HashMap<Integer, ViewRectModel> Rectangles = mRectangleHelper.getViewRectangles();
        for(Object object : Rectangles.entrySet()){
            Map.Entry pair = (Map.Entry) object;
            ViewRectModel rectangle = (ViewRectModel) pair.getValue();
            boolean doesTouchRectangle = doesTouchRectangle(touchEvent, rectangle, (int) pair.getKey());
            if(doesTouchRectangle){
                int position = (int) pair.getKey();
                positions.add(position);
            }
        }
        return positions;
    }

    private boolean doesTouchRectangle(TouchEventModel touchEvent, ViewRectModel rectangle, int position){
        float x = touchEvent.getX();
        float y = touchEvent.getY();
        float semiMajorAxis = touchEvent.getMajorAxis()/2;
        float semiMinorAxis = touchEvent.getMinorAxis()/2;
        float ellipseVertexTop = y + semiMajorAxis;
        float ellipseVertexBottom = y - semiMajorAxis;
        float ellipseCovertexRight = x + semiMinorAxis;
        float ellipseCovertexLeft = x - semiMinorAxis;
        Log.d(TAG, "RECT TOP: " + String.valueOf(rectangle.getTop()) + " VERTEX TOP: " + String.valueOf(ellipseVertexTop));
        Log.d(TAG, "RECT BTM: " + String.valueOf(rectangle.getBottom()) + " VERTEX BTM: " + String.valueOf(ellipseVertexBottom));
        Log.d(TAG, "RECT LFT: " + String.valueOf(rectangle.getLeft()) + " VERTEX LFT: " + String.valueOf(ellipseCovertexLeft));
        Log.d(TAG, "RECT RGT: " + String.valueOf(rectangle.getRight()) + " VERTEX RGT: " + String.valueOf(ellipseCovertexRight));
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

        Log.d(TAG, "IS TOUCHING: " + String.valueOf(isTouching) + " Position: " + String.valueOf(position));
        return isTouching;
    }
}
