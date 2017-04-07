package maderski.iwbinterviewhw.Helpers;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Jason on 4/6/17.
 */

public class CaptureTouchEventsHelper implements View.OnTouchListener {
    private static final String TAG = "TouchOverlay";

    private ListItemTouchListener mOnTouchListener;

    public interface ListItemTouchListener {
        void onListItemPressed(int clickedItemIndex, float pressure, double areaOfEllipse);
        void onListItemReleased(int clickedItemIndex);
        void onListItemCancel();
    }

    public CaptureTouchEventsHelper(View view){
        view.setOnTouchListener(this);
    }

    // Sets the OnTouchListener
    public void setOnTouchListener(ListItemTouchListener onTouchListener){
        mOnTouchListener = onTouchListener;
    }

    // Captures touch events
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();

        int actionIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(actionIndex);
        int onClickedPosition = 0;
        int pointerCount = motionEvent.getPointerCount();
        //Double areaOfEllipse = Math.PI * motionEvent.getTouchMajor() * motionEvent.getTouchMinor();

        switch(action) {
            case MotionEvent.ACTION_POINTER_DOWN:
                for(int i=0; i<pointerCount; i++){
                    pointerId = motionEvent.getPointerId(i);
                    float xCoord = motionEvent.getX(i);
                    float yCoord = motionEvent.getY(i);
                    try {
                        Double areaOfEllipse = Math.PI * motionEvent.getTouchMajor(pointerId) * motionEvent.getTouchMinor(pointerId);
                        Log.d(TAG, "pointer count: " + String.valueOf(pointerCount));
                        Log.d(TAG, "pointer id: " + String.valueOf(pointerId)
                                + " area: " + String.valueOf(areaOfEllipse)
                                + " X: " + String.valueOf(xCoord)
                                + " Y: " + String.valueOf(yCoord));
                    } catch(Exception e){
                        e.getMessage();
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_DOWN:
                if(pointerCount == 1){
                    float xCoord = motionEvent.getX();
                    float yCoord = motionEvent.getY();
                    Double areaOfEllipse = Math.PI * motionEvent.getTouchMajor() * motionEvent.getTouchMinor();
                    Log.d(TAG, "pointer id: " + String.valueOf(pointerId)
                            + " area: " + String.valueOf(areaOfEllipse)
                            + " X: " + String.valueOf(xCoord)
                            + " Y: " + String.valueOf(yCoord));
                }
//                Log.d(TAG, "DOWN pointer id: " + String.valueOf(actionIndex));
//                Log.d(TAG, "DOWN POINTER ID: " + String.valueOf(pointerId));
//                Log.d(TAG, "DOWN");
                //mOnTouchListener.onListItemPressed(onClickedPosition, motionEvent.getPressure(), areaOfEllipse);
                break;
            case MotionEvent.ACTION_UP:
//                Log.d(TAG, "UP POINTER ID: " + String.valueOf(pointerId));
//                Log.d(TAG, "UP");
//                Log.d(TAG, "UP pointer id: " + String.valueOf(actionIndex));
                //mOnTouchListener.onListItemReleased(onClickedPosition);
                break;
            case MotionEvent.ACTION_CANCEL:
                //mOnTouchListener.onListItemCancel();
                break;
        }
        return true;
    }
}
