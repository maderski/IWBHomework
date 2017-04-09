package maderski.iwbinterviewhw.Helpers;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.HashMap;
import java.util.Map;

import maderski.iwbinterviewhw.Models.ViewRectModel;

/**
 * Created by Jason on 4/7/17.
 */

public class ViewRectHelper implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "ViewCoordHelper";

    private RecyclerView mRecyclerView;
    private HashMap<Integer, ViewRectModel> mViewRectangles = new HashMap<>();

    public ViewRectHelper(RecyclerView recyclerView){
        mRecyclerView = recyclerView;
    }

    // Get the boundaries/rectangles for the views
    @Override
    public void onGlobalLayout() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();

        for(int i = 0; i < layoutManager.getChildCount(); i++) {
            View view = layoutManager.getChildAt(i);

            int position = layoutManager.getPosition(view);

            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);

            mViewRectangles.put(position, new ViewRectModel(rect.top, rect.right, rect.bottom, rect.left));
        }
        mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        for (Object object : mViewRectangles.entrySet()) {
            Map.Entry pair = (Map.Entry) object;
            int positionId = (int) pair.getKey();
            ViewRectModel viewRectangle = (ViewRectModel) pair.getValue();

            Log.d(TAG, "position: " + String.valueOf(positionId)
                    + "\ntop: " + String.valueOf(viewRectangle.getTop())
                    + " right: " + String.valueOf(viewRectangle.getRight())
                    + " bottom: " + String.valueOf(viewRectangle.getBottom())
                    + " left: " + String.valueOf(viewRectangle.getLeft()));
        }
    }

    // Get all view boundaries/rectangles
    public HashMap<Integer, ViewRectModel> getViewRectangles(){
        return mViewRectangles;
    }
}
