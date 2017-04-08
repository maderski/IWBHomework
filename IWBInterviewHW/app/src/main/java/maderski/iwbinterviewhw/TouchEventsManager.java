package maderski.iwbinterviewhw;

import android.util.Log;

import maderski.iwbinterviewhw.Helpers.CaptureTouchEventsHelper;
import maderski.iwbinterviewhw.Helpers.TouchEventsHelper;
import maderski.iwbinterviewhw.Helpers.ViewRectHelper;

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
    public void singleTouchPressed(float x, float y, float majorAxis, float minorAxis, float pressure) {

    }

    @Override
    public void singleTouchReleased(int pointerId) {

    }

    @Override
    public void multiTouchPressed(int pointerId, float x, float y, float majorAxis, float minorAxis, float pressure) {

    }

    @Override
    public void multiTouchReleased(int pointerId) {

    }

    @Override
    public void touchCancel() {

    }
}
