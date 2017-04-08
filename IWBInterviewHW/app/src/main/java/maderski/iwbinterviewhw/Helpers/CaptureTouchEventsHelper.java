package maderski.iwbinterviewhw.Helpers;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Jason on 4/6/17.
 */

public class CaptureTouchEventsHelper implements View.OnTouchListener {
    private static final String TAG = "TouchOverlay";

    private OnTouchListener mOnTouchListener;

    public interface OnTouchListener {
        void singleTouchPressed(float x, float y, float majorAxis, float minorAxis, float pressure);
        void singleTouchReleased(int pointerId);
        void multiTouchPressed(int pointerId, float x, float y, float majorAxis, float minorAxis, float pressure);
        void multiTouchReleased(int pointerId);
        void touchCancel();
    }

    public CaptureTouchEventsHelper(View view){
        view.setOnTouchListener(this);
    }

    // Sets the OnTouchListener
    public void setOnTouchListener(OnTouchListener onTouchListener){
        mOnTouchListener = onTouchListener;
    }

    // Captures touch events
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();

        int actionIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(actionIndex);
        int pointerCount = motionEvent.getPointerCount();

        switch(action) {
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG, "MULTI PRESS");
                for(int i=0; i<pointerCount; i++){
                    pointerId = motionEvent.getPointerId(i);
                    float xCoord = motionEvent.getX(i);
                    float yCoord = motionEvent.getY(i);
                    float pressure = motionEvent.getPressure();

                    try {
                        float majorAxis = motionEvent.getTouchMajor(pointerId);
                        float minorAxis = motionEvent.getTouchMinor(pointerId);
                        Log.d(TAG, "pointer count: " + String.valueOf(pointerCount));
                        Log.d(TAG, "pointer id: " + String.valueOf(pointerId)
                                + " X: " + String.valueOf(xCoord)
                                + " Y: " + String.valueOf(yCoord));
                        mOnTouchListener.multiTouchPressed(pointerId, xCoord, yCoord, majorAxis, minorAxis, pressure);
                    } catch(Exception e){
                        e.getMessage();
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG, "MULTI RELEASE");
                for(int i=0; i<pointerCount; i++){
                    pointerId = motionEvent.getPointerId(i);
                    float xCoord = motionEvent.getX(i);
                    float yCoord = motionEvent.getY(i);

                    try {
                        Log.d(TAG, "pointer count: " + String.valueOf(pointerCount));
                        Log.d(TAG, "pointer id: " + String.valueOf(pointerId)
                                + " X: " + String.valueOf(xCoord)
                                + " Y: " + String.valueOf(yCoord));
                        mOnTouchListener.multiTouchReleased(pointerId);
                    } catch(Exception e){
                        e.getMessage();
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if(pointerCount == 1){
                    Log.d(TAG, "SINGLE PRESS");
                    float xCoord = motionEvent.getX();
                    float yCoord = motionEvent.getY();
                    float majorAxis = motionEvent.getTouchMajor();
                    float minorAxis = motionEvent.getTouchMinor();
                    float pressure = motionEvent.getPressure();
                    Log.d(TAG, "pointer id: " + String.valueOf(pointerId)
                            + " X: " + String.valueOf(xCoord)
                            + " Y: " + String.valueOf(yCoord));
                    mOnTouchListener.singleTouchPressed(xCoord, yCoord, majorAxis, minorAxis, pressure);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(pointerCount == 1) {
                    Log.d(TAG, "SINGLE RELEASE");
                    Log.d(TAG, "pointer id: " + String.valueOf(pointerId));
                    mOnTouchListener.singleTouchReleased(pointerId);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mOnTouchListener.touchCancel();
                break;
        }
        return true;
    }
}
