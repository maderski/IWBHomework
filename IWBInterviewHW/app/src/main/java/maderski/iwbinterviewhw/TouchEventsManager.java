package maderski.iwbinterviewhw;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import maderski.iwbinterviewhw.Helpers.CaptureTouchEventsHelper;
import maderski.iwbinterviewhw.Helpers.TouchEventsHelper;
import maderski.iwbinterviewhw.Helpers.ViewRectHelper;
import maderski.iwbinterviewhw.Models.TouchEventModel;

/**
 * Created by Jason on 4/8/17.
 */

public class TouchEventsManager implements CaptureTouchEventsHelper.OnTouchListener {
    private static final String TAG = "TouchEventsManager";

    public interface PositionCallbacks{
        void choosenPosition(int position);
        void pressedPositions(int[] positions);
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
        for (Object o : touchEvents.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            int positionId = (int) pair.getKey();
            TouchEventModel tem = (TouchEventModel) pair.getValue();
            Log.d(TAG, "POSITION ID: " + String.valueOf(positionId) + " area: " + String.valueOf(tem.getArea()));
        }
    }
}
