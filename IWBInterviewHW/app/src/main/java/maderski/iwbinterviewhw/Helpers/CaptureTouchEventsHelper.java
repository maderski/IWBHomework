package maderski.iwbinterviewhw.Helpers;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;

import maderski.iwbinterviewhw.Models.TouchEventModel;

/**
 * Created by Jason on 4/6/17.
 */

public class CaptureTouchEventsHelper implements View.OnTouchListener {
    private static final String TAG = "TouchOverlay";

    public interface OnTouchListener {
        void currentTouchEvents(HashMap<Integer, TouchEventModel> touchEvents);
    }

    private OnTouchListener mOnTouchListener;
    private int mPointerIdOnTouch;
    private HashMap<Integer, TouchEventModel> mCurrentTouchEvents = new HashMap<>();

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
        float xCoord, yCoord, pressure, majorAxis, minorAxis;
        double area;
        TouchEventModel touchEventModel;

        int action = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        int pointerCount = motionEvent.getPointerCount();
        mPointerIdOnTouch = motionEvent.getPointerId(actionIndex);

        switch(action) {
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG, "MULTI PRESS");

                xCoord = motionEvent.getX(mPointerIdOnTouch);
                yCoord = motionEvent.getY(mPointerIdOnTouch);
                pressure = motionEvent.getPressure(mPointerIdOnTouch);
                majorAxis = motionEvent.getTouchMajor(mPointerIdOnTouch);
                minorAxis = motionEvent.getTouchMinor(mPointerIdOnTouch);
                area = Math.PI * majorAxis * minorAxis;
                touchEventModel = new TouchEventModel(xCoord, yCoord, majorAxis, minorAxis, pressure, area);
                mCurrentTouchEvents.put(mPointerIdOnTouch, touchEventModel);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG, "MULTI RELEASE");
                mCurrentTouchEvents.remove(mPointerIdOnTouch);
                break;
            case MotionEvent.ACTION_DOWN:
                if(pointerCount == 1){
                    Log.d(TAG, "SINGLE PRESS");

                    xCoord = motionEvent.getX(mPointerIdOnTouch);
                    yCoord = motionEvent.getY(mPointerIdOnTouch);
                    pressure = motionEvent.getPressure(mPointerIdOnTouch);
                    majorAxis = motionEvent.getTouchMajor(mPointerIdOnTouch);
                    minorAxis = motionEvent.getTouchMinor(mPointerIdOnTouch);
                    area = Math.PI * majorAxis * minorAxis;
                    touchEventModel = new TouchEventModel(xCoord, yCoord, majorAxis, minorAxis, pressure, area);
                    mCurrentTouchEvents.put(mPointerIdOnTouch, touchEventModel);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(pointerCount == 1) {
                    Log.d(TAG, "SINGLE RELEASE");
                    mCurrentTouchEvents.remove(mPointerIdOnTouch);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "CANCELLED");
                break;
        }

        mOnTouchListener.currentTouchEvents(mCurrentTouchEvents);
        return true;
    }
}
